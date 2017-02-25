package com.vanniktech.emoji;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardOpenListener;

import static com.vanniktech.emoji.Utils.checkNotNull;

public final class EmojiPopup {
  private static final int MIN_KEYBOARD_HEIGHT = 100;

  final View rootView;
  final Activity context;

  @NonNull final RecentEmoji recentEmoji;
  @NonNull final EmojiVariantPopup variantPopup;

  final PopupWindow popupWindow;
  final EmojiEditText emojiEditText;

  boolean isPendingOpen;
  boolean isKeyboardOpen;

  @Nullable OnEmojiPopupShownListener onEmojiPopupShownListener;
  @Nullable OnSoftKeyboardCloseListener onSoftKeyboardCloseListener;
  @Nullable OnSoftKeyboardOpenListener onSoftKeyboardOpenListener;

  @Nullable OnEmojiBackspaceClickListener onEmojiBackspaceClickListener;
  @Nullable OnEmojiClickedListener onEmojiClickedListener;
  @Nullable OnEmojiPopupDismissListener onEmojiPopupDismissListener;

  private final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
    @Override public void onGlobalLayout() {
      final Rect rect = Utils.windowVisibleDisplayFrame(context);
      final int heightDifference = Utils.screenHeight(context) - rect.bottom;

      if (heightDifference > Utils.dpToPx(context, MIN_KEYBOARD_HEIGHT)) {
        popupWindow.setHeight(heightDifference);
        popupWindow.setWidth(rect.right);

        if (!isKeyboardOpen && onSoftKeyboardOpenListener != null) {
          onSoftKeyboardOpenListener.onKeyboardOpen(heightDifference);
        }

        isKeyboardOpen = true;

        if (isPendingOpen) {
          showAtBottom();
          isPendingOpen = false;
        }
      } else {
        if (isKeyboardOpen) {
          isKeyboardOpen = false;

          if (onSoftKeyboardCloseListener != null) {
            onSoftKeyboardCloseListener.onKeyboardClose();
          }
        }
      }
    }
  };

  EmojiPopup(@NonNull final View rootView, @NonNull final EmojiEditText emojiEditText, @Nullable final RecentEmoji recent) {
    this.context = (Activity) rootView.getContext();
    this.rootView = rootView.getRootView();
    this.emojiEditText = emojiEditText;
    this.recentEmoji = recent != null ? recent : new RecentEmojiManager(context);

    popupWindow = new PopupWindow(context);

    final OnEmojiLongClickedListener longClickListener = new OnEmojiLongClickedListener() {
      @Override
      public void onEmojiLongClicked(final View view, final Emoji emoji) {
        variantPopup.show(view, emoji);
      }
    };

    final OnEmojiClickedListener clickListener = new OnEmojiClickedListener() {
      @Override
      public void onEmojiClicked(final Emoji emoji) {
        emojiEditText.input(emoji);
        recentEmoji.addEmoji(emoji);

        if (onEmojiClickedListener != null) {
          onEmojiClickedListener.onEmojiClicked(emoji);
        }

        variantPopup.dismiss();
      }
    };

    variantPopup = new EmojiVariantPopup(this.rootView, clickListener);

    final EmojiView emojiView = new EmojiView(context, clickListener, longClickListener, recentEmoji);

    emojiView.setOnEmojiBackspaceClickListener(new OnEmojiBackspaceClickListener() {
      @Override public void onEmojiBackspaceClicked(final View v) {
        emojiEditText.backspace();

        if (onEmojiBackspaceClickListener != null) {
          onEmojiBackspaceClickListener.onEmojiBackspaceClicked(v);
        }
      }
    });

    popupWindow.setContentView(emojiView);
    popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
    popupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null)); // To avoid borders and overdraw.
    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override public void onDismiss() {
        if (onEmojiPopupDismissListener != null) {
          onEmojiPopupDismissListener.onEmojiPopupDismiss();
        }
      }
    });
  }

  public void toggle() {
    if (!popupWindow.isShowing()) {
      context.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

      if (isKeyboardOpen) {
        // If the keyboard is visible, simply show the emoji popup.
        showAtBottom();
      } else {
        // Open the text keyboard first and immediately after that show the emoji popup.
        emojiEditText.setFocusableInTouchMode(true);
        emojiEditText.requestFocus();

        showAtBottomPending();

        final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(emojiEditText, InputMethodManager.SHOW_IMPLICIT);
      }
    } else {
      dismiss();
    }

    // Manually dispatch the event. In some cases this does not work out of the box reliably.
    context.getWindow().getDecorView().getViewTreeObserver().dispatchOnGlobalLayout();
  }

  public boolean isShowing() {
    return popupWindow.isShowing();
  }

  public void dismiss() {
    Utils.removeOnGlobalLayoutListener(context.getWindow().getDecorView(), onGlobalLayoutListener);
    popupWindow.dismiss();
    variantPopup.dismiss();
    recentEmoji.persist();
  }

  void showAtBottom() {
    popupWindow.showAtLocation(rootView, Gravity.NO_GRAVITY, 0, Utils.screenHeight(context));

    if (onEmojiPopupShownListener != null) {
      onEmojiPopupShownListener.onEmojiPopupShown();
    }
  }

  private void showAtBottomPending() {
    if (isKeyboardOpen) {
      showAtBottom();
    } else {
      isPendingOpen = true;
    }
  }

  public static final class Builder {
    @NonNull private final View rootView;
    @Nullable private OnEmojiPopupShownListener onEmojiPopupShownListener;
    @Nullable private OnSoftKeyboardCloseListener onSoftKeyboardCloseListener;
    @Nullable private OnSoftKeyboardOpenListener onSoftKeyboardOpenListener;
    @Nullable private OnEmojiBackspaceClickListener onEmojiBackspaceClickListener;
    @Nullable private OnEmojiClickedListener onEmojiClickedListener;
    @Nullable private OnEmojiPopupDismissListener onEmojiPopupDismissListener;
    @Nullable private RecentEmoji recentEmoji;

    private Builder(final View rootView) {
      this.rootView = checkNotNull(rootView, "The root View can't be null");
    }

    /**
     * @param rootView The root View of your layout.xml which will be used for calculating the height
     *                 of the keyboard.
     * @return builder For building the {@link EmojiPopup}.
     */
    @CheckResult public static Builder fromRootView(final View rootView) {
      return new Builder(rootView);
    }

    @CheckResult public Builder setOnSoftKeyboardCloseListener(@Nullable final OnSoftKeyboardCloseListener listener) {
      onSoftKeyboardCloseListener = listener;
      return this;
    }

    @CheckResult public Builder setOnEmojiClickedListener(@Nullable final OnEmojiClickedListener listener) {
      onEmojiClickedListener = listener;
      return this;
    }

    @CheckResult public Builder setOnSoftKeyboardOpenListener(@Nullable final OnSoftKeyboardOpenListener listener) {
      onSoftKeyboardOpenListener = listener;
      return this;
    }

    @CheckResult public Builder setOnEmojiPopupShownListener(@Nullable final OnEmojiPopupShownListener listener) {
      onEmojiPopupShownListener = listener;
      return this;
    }

    @CheckResult public Builder setOnEmojiPopupDismissListener(@Nullable final OnEmojiPopupDismissListener listener) {
      onEmojiPopupDismissListener = listener;
      return this;
    }

    @CheckResult public Builder setOnEmojiBackspaceClickListener(@Nullable final OnEmojiBackspaceClickListener listener) {
      onEmojiBackspaceClickListener = listener;
      return this;
    }

    /**
     * Allows you to pass your own implementation of recent emojis. If not provided the default one
     * {@link RecentEmojiManager} will be used
     *
     * @since 0.2.0
     */
    @CheckResult public Builder setRecentEmoji(@Nullable final RecentEmoji recent) {
      recentEmoji = recent;
      return this;
    }

    @CheckResult public EmojiPopup build(@NonNull final EmojiEditText emojiEditText) {
      EmojiManager.getInstance().verifyInstalled();
      checkNotNull(emojiEditText, "EmojiEditText can't be null");

      final EmojiPopup emojiPopup = new EmojiPopup(rootView, emojiEditText, recentEmoji);
      emojiPopup.onSoftKeyboardCloseListener = onSoftKeyboardCloseListener;
      emojiPopup.onEmojiClickedListener = onEmojiClickedListener;
      emojiPopup.onSoftKeyboardOpenListener = onSoftKeyboardOpenListener;
      emojiPopup.onEmojiPopupShownListener = onEmojiPopupShownListener;
      emojiPopup.onEmojiPopupDismissListener = onEmojiPopupDismissListener;
      emojiPopup.onEmojiBackspaceClickListener = onEmojiBackspaceClickListener;
      return emojiPopup;
    }
  }
}
