package com.vanniktech.emoji;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickListener;
import com.vanniktech.emoji.listeners.OnEmojiLongClickListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardOpenListener;

import static com.vanniktech.emoji.Utils.backspace;
import static com.vanniktech.emoji.Utils.checkNotNull;

public final class EmojiPopup {
  static final String TAG = "EmojiPopup";

  static final int MIN_KEYBOARD_HEIGHT = 100;

  final View rootView;
  final Activity context;

  @NonNull final RecentEmoji recentEmoji;
  @NonNull final VariantEmoji variantEmoji;
  @NonNull final EmojiVariantPopup variantPopup;

  final PopupWindow popupWindow;
  final EditText editText;

  boolean isPendingOpen;
  boolean isKeyboardOpen;

  @Nullable OnEmojiPopupShownListener onEmojiPopupShownListener;
  @Nullable OnSoftKeyboardCloseListener onSoftKeyboardCloseListener;
  @Nullable OnSoftKeyboardOpenListener onSoftKeyboardOpenListener;

  @Nullable OnEmojiBackspaceClickListener onEmojiBackspaceClickListener;
  @Nullable OnEmojiClickListener onEmojiClickListener;
  @Nullable OnEmojiPopupDismissListener onEmojiPopupDismissListener;

  int originalImeOptions = -1;

  final ResultReceiver resultReceiver = new ResultReceiver(null) {
      @Override protected void onReceiveResult (final int resultCode, final Bundle resultData) {
        if(resultCode == 0 || resultCode == 1) {
          context.runOnUiThread(new Runnable() {
            @Override public void run() {
              showAtBottom();
            }
          });
        }
    }
  };

  final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
    @Override @SuppressWarnings("PMD.CyclomaticComplexity") public void onGlobalLayout() {
      updateKeyboardState();
    }
  };

  private void updateKeyboardState() {

    final int keyboardHeight = Utils.getInputMethodHeight(context);

    if (keyboardHeight > Utils.dpToPx(context, MIN_KEYBOARD_HEIGHT) && isPendingOpen) {
      if (popupWindow.getHeight() != keyboardHeight) {
        popupWindow.setHeight(keyboardHeight);
      }

      final Rect rect = Utils.windowVisibleDisplayFrame(context);

      final int properWidth = Utils.getOrientation(context) == Configuration.ORIENTATION_PORTRAIT ? rect.right : Utils.getScreenWidth(context);
      if (popupWindow.getWidth() != properWidth) {
        popupWindow.setWidth(properWidth);
      }

      if (!isKeyboardOpen) {
        isKeyboardOpen = true;
        if (onSoftKeyboardOpenListener != null) {
          onSoftKeyboardOpenListener.onKeyboardOpen(keyboardHeight);
        }
      }

      showAtBottom();
    } else {
      isKeyboardOpen = false;

      if (onSoftKeyboardCloseListener != null) {
        onSoftKeyboardCloseListener.onKeyboardClose();
      }

      dismiss();
    }
  }

  EmojiPopup(@NonNull final View rootView, @NonNull final EditText editText,
      @Nullable final RecentEmoji recent, @Nullable final VariantEmoji variant,
      @ColorInt final int backgroundColor, @ColorInt final int iconColor, @ColorInt final int dividerColor,
      @StyleRes final int animationStyle, @Nullable final ViewPager.PageTransformer pageTransformer) {
    this.context = Utils.asActivity(rootView.getContext());
    this.rootView = rootView.getRootView();
    this.editText = editText;
    this.recentEmoji = recent != null ? recent : new RecentEmojiManager(context);
    this.variantEmoji = variant != null ? variant : new VariantEmojiManager(context);

    popupWindow = new PopupWindow(context);

    final OnEmojiLongClickListener longClickListener = new OnEmojiLongClickListener() {
      @Override public void onEmojiLongClick(@NonNull final EmojiImageView view, @NonNull final Emoji emoji) {
        variantPopup.show(view, emoji);
      }
    };

    final OnEmojiClickListener clickListener = new OnEmojiClickListener() {
      @Override public void onEmojiClick(@NonNull final EmojiImageView imageView, @NonNull final Emoji emoji) {
        Utils.input(editText, emoji);

        recentEmoji.addEmoji(emoji);
        variantEmoji.addVariant(emoji);
        imageView.updateEmoji(emoji);

        if (onEmojiClickListener != null) {
          onEmojiClickListener.onEmojiClick(imageView, emoji);
        }

        variantPopup.dismiss();
      }
    };

    variantPopup = new EmojiVariantPopup(this.rootView, clickListener);

    final EmojiView emojiView = new EmojiView(context, clickListener, longClickListener, recentEmoji, variantEmoji, backgroundColor, iconColor, dividerColor, pageTransformer);
    emojiView.setOnEmojiBackspaceClickListener(new OnEmojiBackspaceClickListener() {
      @Override public void onEmojiBackspaceClick(final View v) {
        backspace(editText);

        if (onEmojiBackspaceClickListener != null) {
          onEmojiBackspaceClickListener.onEmojiBackspaceClick(v);
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

    if (animationStyle != 0) {
      popupWindow.setAnimationStyle(animationStyle);
    }

    rootView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
  }

  public void toggle() {
    if (!popupWindow.isShowing()) {
      if (Utils.shouldOverrideRegularCondition(context, editText) && originalImeOptions == -1) {
        originalImeOptions = editText.getImeOptions();
      }
      editText.setFocusableInTouchMode(true);
      editText.requestFocus();
      showAtBottomPending();
    } else {
      dismiss();
    }
  }

  private void showAtBottomPending() {
    isPendingOpen = true;
    final InputMethodManager inputMethodManager =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

    if (Utils.shouldOverrideRegularCondition(context, editText)) {
      editText.setImeOptions(editText.getImeOptions() | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
      inputMethodManager.restartInput(editText);
    }

    inputMethodManager.showSoftInput(editText, InputMethodManager.RESULT_UNCHANGED_SHOWN, resultReceiver);
  }

  public boolean isShowing() {
    return popupWindow.isShowing();
  }

  public void dismiss() {
    popupWindow.dismiss();
    variantPopup.dismiss();
    recentEmoji.persist();
    variantEmoji.persist();

    if (originalImeOptions != -1) {
      final InputMethodManager inputMethodManager =
          (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
      editText.setImeOptions(originalImeOptions);
      inputMethodManager.restartInput(editText);
    }
  }

  void showAtBottom() {
    isPendingOpen = false;
    popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

    if (onEmojiPopupShownListener != null) {
      onEmojiPopupShownListener.onEmojiPopupShown();
    }
  }

  public static final class Builder {
    @NonNull private final View rootView;
    @StyleRes private int keyboardAnimationStyle;
    @ColorInt private int backgroundColor;
    @ColorInt private int iconColor;
    @ColorInt private int dividerColor;
    @Nullable private ViewPager.PageTransformer pageTransformer;
    @Nullable private OnEmojiPopupShownListener onEmojiPopupShownListener;
    @Nullable private OnSoftKeyboardCloseListener onSoftKeyboardCloseListener;
    @Nullable private OnSoftKeyboardOpenListener onSoftKeyboardOpenListener;
    @Nullable private OnEmojiBackspaceClickListener onEmojiBackspaceClickListener;
    @Nullable private OnEmojiClickListener onEmojiClickListener;
    @Nullable private OnEmojiPopupDismissListener onEmojiPopupDismissListener;
    @Nullable private RecentEmoji recentEmoji;
    @Nullable private VariantEmoji variantEmoji;

    private Builder(final View rootView) {
      this.rootView = checkNotNull(rootView, "The root View can't be null");
    }

    /**
     * @param rootView The root View of your layout.xml which will be used for calculating the height
     * of the keyboard.
     * @return builder For building the {@link EmojiPopup}.
     */
    @CheckResult public static Builder fromRootView(final View rootView) {
      return new Builder(rootView);
    }

    @CheckResult public Builder setOnSoftKeyboardCloseListener(@Nullable final OnSoftKeyboardCloseListener listener) {
      onSoftKeyboardCloseListener = listener;
      return this;
    }

    @CheckResult public Builder setOnEmojiClickListener(@Nullable final OnEmojiClickListener listener) {
      onEmojiClickListener = listener;
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
     * {@link RecentEmojiManager} will be used.
     *
     * @since 0.2.0
     */
    @CheckResult public Builder setRecentEmoji(@Nullable final RecentEmoji recent) {
      recentEmoji = recent;
      return this;
    }

    /**
     * Allows you to pass your own implementation of variant emojis. If not provided the default one
     * {@link VariantEmojiManager} will be used.
     *
     * @since 0.5.0
     */
    @CheckResult public Builder setVariantEmoji(@Nullable final VariantEmoji variant) {
      variantEmoji = variant;
      return this;
    }

    @CheckResult public Builder setBackgroundColor(@ColorInt final int color) {
      backgroundColor = color;
      return this;
    }

    @CheckResult public Builder setIconColor(@ColorInt final int color) {
      iconColor = color;
      return this;
    }

    @CheckResult public Builder setDividerColor(@ColorInt final int color) {
      dividerColor = color;
      return this;
    }

    @CheckResult public Builder setKeyboardAnimationStyle(@StyleRes final int animation) {
      keyboardAnimationStyle = animation;
      return this;
    }

    @CheckResult public Builder setPageTransformer(@Nullable final ViewPager.PageTransformer transformer) {
      pageTransformer = transformer;
      return this;
    }

    @CheckResult public EmojiPopup build(@NonNull final EditText editText) {
      EmojiManager.getInstance().verifyInstalled();
      checkNotNull(editText, "EditText can't be null");

      final EmojiPopup emojiPopup = new EmojiPopup(rootView, editText, recentEmoji, variantEmoji, backgroundColor,
          iconColor, dividerColor, keyboardAnimationStyle, pageTransformer);
      emojiPopup.onSoftKeyboardCloseListener = onSoftKeyboardCloseListener;
      emojiPopup.onEmojiClickListener = onEmojiClickListener;
      emojiPopup.onSoftKeyboardOpenListener = onSoftKeyboardOpenListener;
      emojiPopup.onEmojiPopupShownListener = onEmojiPopupShownListener;
      emojiPopup.onEmojiPopupDismissListener = onEmojiPopupDismissListener;
      emojiPopup.onEmojiBackspaceClickListener = onEmojiBackspaceClickListener;
      return emojiPopup;
    }
  }
}
