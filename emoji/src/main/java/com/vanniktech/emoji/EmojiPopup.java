package com.vanniktech.emoji;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;
import com.vanniktech.emoji.listeners.OnEmojiLongClickedListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardOpenListener;

import java.util.List;

import static com.vanniktech.emoji.Utils.checkNotNull;

@SuppressWarnings("PMD.ExcessiveImports")
public final class EmojiPopup {
  private static final int MIN_KEYBOARD_HEIGHT = 100;

  final View rootView;
  final Context context;

  @NonNull final RecentEmoji recentEmoji;

  final PopupWindow popupWindow;
  private PopupWindow skinTonePopupWindow;
  private final EmojiEditText emojiEditText;

  int keyBoardHeight;
  boolean isPendingOpen;
  boolean isKeyboardOpen;

  @Nullable OnEmojiPopupShownListener onEmojiPopupShownListener;
  @Nullable OnSoftKeyboardCloseListener onSoftKeyboardCloseListener;
  @Nullable OnSoftKeyboardOpenListener onSoftKeyboardOpenListener;

  private final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
    @Override public void onGlobalLayout() {
      final Rect rect = new Rect();
      rootView.getWindowVisibleDisplayFrame(rect);

      int heightDifference = getUsableScreenHeight() - (rect.bottom - rect.top);

      final Resources resources = context.getResources();
      final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");

      if (resourceId > 0) {
        heightDifference -= resources.getDimensionPixelSize(resourceId);
      }

      if (heightDifference > MIN_KEYBOARD_HEIGHT) {
        keyBoardHeight = heightDifference;
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(keyBoardHeight);

        if (!isKeyboardOpen && onSoftKeyboardOpenListener != null) {
          onSoftKeyboardOpenListener.onKeyboardOpen(keyBoardHeight);
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

  private final OnEmojiClickedListener internalOnEmojiClickedListener = new OnEmojiClickedListener() {
    @Override
    public void onEmojiClicked(final Emoji emoji) {
      emojiEditText.input(emoji);
      recentEmoji.addEmoji(emoji);

      if (onEmojiClickedListener != null) {
        onEmojiClickedListener.onEmojiClicked(emoji);
      }
    }
  };

  @Nullable OnEmojiBackspaceClickListener onEmojiBackspaceClickListener;
  @Nullable OnEmojiClickedListener onEmojiClickedListener;
  @Nullable OnEmojiPopupDismissListener onEmojiPopupDismissListener;

  EmojiPopup(@NonNull final View rootView, @NonNull final EmojiEditText emojiEditText, @Nullable final RecentEmoji recent) {
    this.context = rootView.getContext();
    this.rootView = rootView;
    this.emojiEditText = emojiEditText;
    this.recentEmoji = recent != null ? recent : new RecentEmojiManager(context);

    popupWindow = new PopupWindow(context);
    popupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null)); // To avoid borders & overdraw

    final OnEmojiLongClickedListener longClickListener = new OnEmojiLongClickedListener() {
      @Override
      public void onEmojiLongClicked(final View view, final Emoji emoji) {
        skinTonePopupWindow = buildSkinTonePopupWindow(view, emoji);

        final int[] location = new int[2];
        view.getLocationOnScreen(location);

        final int x = location[0] - skinTonePopupWindow.getContentView().getMeasuredWidth() / 2 + view.getWidth() / 2;
        final int y = location[1] - skinTonePopupWindow.getContentView().getMeasuredHeight();

        skinTonePopupWindow.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.NO_GRAVITY, x, y);
      }
    };

    final EmojiView emojiView = new EmojiView(context, internalOnEmojiClickedListener, longClickListener, recentEmoji);

    emojiView.setOnEmojiBackspaceClickListener(new OnEmojiBackspaceClickListener() {
      @Override public void onEmojiBackspaceClicked(final View v) {
        emojiEditText.backspace();

        if (onEmojiBackspaceClickListener != null) {
          onEmojiBackspaceClickListener.onEmojiBackspaceClicked(v);
        }
      }
    });

    popupWindow.setContentView(emojiView);
    popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
    popupWindow.setHeight((int) context.getResources().getDimension(R.dimen.emoji_keyboard_height));
    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override public void onDismiss() {
        if (onEmojiPopupDismissListener != null) {
          onEmojiPopupDismissListener.onEmojiPopupDismiss();
        }
      }
    });
  }

  void showAtBottom() {
    popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
  }

  private void showAtBottomPending() {
    if (isKeyboardOpen) {
      showAtBottom();
    } else {
      isPendingOpen = true;
    }
  }

  public void toggle() {
    if (!popupWindow.isShowing()) {
      rootView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

      if (isKeyboardOpen) {
        // If keyboard is visible, simply show the emoji popup
        showAtBottom();
      } else {
        // Open the text keyboard first and immediately after that show the emoji popup
        emojiEditText.setFocusableInTouchMode(true);
        emojiEditText.requestFocus();

        showAtBottomPending();

        final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(emojiEditText, InputMethodManager.SHOW_IMPLICIT);
      }

      if (onEmojiPopupShownListener != null) {
        onEmojiPopupShownListener.onEmojiPopupShown();
      }
    } else {
      dismiss();
    }

    // Manually dispatch the event. In some cases this does not work out of the box reliably.
    rootView.getViewTreeObserver().dispatchOnGlobalLayout();
  }

  public boolean isShowing() {
    return popupWindow.isShowing();
  }

  public void dismiss() {
    Utils.removeOnGlobalLayoutListener(rootView, onGlobalLayoutListener);
    popupWindow.dismiss();
    recentEmoji.persist();

    if (skinTonePopupWindow != null) {
      skinTonePopupWindow.dismiss();
    }
  }

  PopupWindow buildSkinTonePopupWindow(final View view, final Emoji emoji){
    final View content = View.inflate(context, R.layout.emoji_skin_popup, null);
    final LinearLayout imageContainer = (LinearLayout) content.findViewById(R.id.container);
    final PopupWindow result = new PopupWindow(content,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
    );

    final List<Emoji> skinTonedEmojis = EmojiManager.getInstance().findSkinTonedEmojis(emoji);
    skinTonedEmojis.add(0, emoji);

    for (final Emoji tonedEmoji : skinTonedEmojis) {
      final ImageView emojiImage = (ImageView) LayoutInflater.from(context)
              .inflate(R.layout.emoji_item, imageContainer, false);
      final ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) emojiImage.getLayoutParams();
      final int margin = (int) Utils.dpToPx(context, 4);

      // Use the same size for Emojis as the picker
      layoutParams.width = view.getWidth();
      layoutParams.setMargins(margin, margin, margin, margin);
      emojiImage.setImageResource(tonedEmoji.getResource());

      emojiImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
          internalOnEmojiClickedListener.onEmojiClicked(tonedEmoji);
        }
      });

      imageContainer.addView(emojiImage);
    }

    content.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

    result.setOutsideTouchable(true);
    result.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));

    return result;
  }

  int getUsableScreenHeight() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      final DisplayMetrics metrics = new DisplayMetrics();

      final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
      windowManager.getDefaultDisplay().getMetrics(metrics);

      return metrics.heightPixels;
    } else {
      return rootView.getRootView().getHeight();
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
      this.rootView = checkNotNull(rootView, "The rootView can't be null");
    }

    /**
     * @param rootView the rootView of your layout.xml which will be used for calculating the height
     * of the keyboard
     * @return builder for building {@link EmojiPopup}
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
     * allows you to pass your own implementation of recent emojis. If not provided the default one
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
