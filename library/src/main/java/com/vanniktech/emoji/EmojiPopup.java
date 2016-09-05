package com.vanniktech.emoji;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
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

    private final EmojiEditText emojiEditText;
    final View rootView;
    final Context context;

    int keyBoardHeight;
    boolean isPendingOpen;
    boolean isKeyboardOpen;

    @Nullable OnEmojiPopupShownListener onEmojiPopupShownListener;
    @Nullable OnSoftKeyboardCloseListener onSoftKeyboardCloseListener;
    @Nullable OnSoftKeyboardOpenListener onSoftKeyboardOpenListener;
    @Nullable OnEmojiBackspaceClickListener onEmojiBackspaceClickListener;
    @Nullable OnEmojiClickedListener onEmojiClickedListener;
    @Nullable OnEmojiPopupDismissListener onEmojiPopupDismissListener;

    @NonNull final RecentEmoji recentEmoji;

    final PopupWindow popupWindow;
    private final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
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

    EmojiPopup(@NonNull final View rootView, @NonNull final EmojiEditText emojiEditText, @Nullable final RecentEmoji recent) {
        this.context = rootView.getContext();
        this.rootView = rootView;
        this.emojiEditText = emojiEditText;
        this.recentEmoji = recent != null ? recent : new RecentEmojiManager(context);

        popupWindow = new PopupWindow(context);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null)); // To avoid borders & overdraw

        final EmojiView emojiView = new EmojiView(context, new OnEmojiClickedListener() {
            @Override
            public void onEmojiClicked(final Emoji emoji) {
                emojiEditText.input(emoji);
                recentEmoji.addEmoji(emoji);

                if (onEmojiClickedListener != null) {
                    onEmojiClickedListener.onEmojiClicked(emoji);
                }
            }
        }, this.recentEmoji);

        emojiView.setOnEmojiBackspaceClickListener(new OnEmojiBackspaceClickListener() {
            @Override
            public void onEmojiBackspaceClicked(final View v) {
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
            @Override
            public void onDismiss() {
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
                this.showAtBottom();
            } else {
                // Open the text keyboard first and immediately after that show the emoji popup
                emojiEditText.setFocusableInTouchMode(true);
                emojiEditText.requestFocus();

                this.showAtBottomPending();

                final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(emojiEditText, InputMethodManager.SHOW_IMPLICIT);
            }

            if (onEmojiPopupShownListener != null) {
                onEmojiPopupShownListener.onEmojiPopupShown();
            }
        } else {
            dismiss();
        }
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }

    public void dismiss() {
        Utils.removeOnGlobalLayoutListener(rootView, onGlobalLayoutListener);
        popupWindow.dismiss();
        recentEmoji.persist();
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
        /**
         * @param rootView the rootView of your layout.xml which will be used for calculating the height of the keyboard
         * @return builder for building {@link EmojiPopup}
         */
        public static Builder fromRootView(final View rootView) {
            return new Builder(rootView);
        }

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

        public Builder setOnSoftKeyboardCloseListener(@Nullable final OnSoftKeyboardCloseListener listener) {
            this.onSoftKeyboardCloseListener = listener;
            return this;
        }

        public Builder setOnEmojiClickedListener(@Nullable final OnEmojiClickedListener listener) {
            this.onEmojiClickedListener = listener;
            return this;
        }

        public Builder setOnSoftKeyboardOpenListener(@Nullable final OnSoftKeyboardOpenListener listener) {
            this.onSoftKeyboardOpenListener = listener;
            return this;
        }

        public Builder setOnEmojiPopupShownListener(@Nullable final OnEmojiPopupShownListener listener) {
            this.onEmojiPopupShownListener = listener;
            return this;
        }

        public Builder setOnEmojiPopupDismissListener(@Nullable final OnEmojiPopupDismissListener listener) {
            this.onEmojiPopupDismissListener = listener;
            return this;
        }

        public Builder setOnEmojiBackspaceClickListener(@Nullable final OnEmojiBackspaceClickListener listener) {
            this.onEmojiBackspaceClickListener = listener;
            return this;
        }

        /**
         * allows you to pass your own implementation of recent emojis. If not provided the default one ({@link RecentEmojiManager} will be used
         *
         * @since 0.2.0
         */
        public Builder setRecentEmoji(@Nullable final RecentEmoji recent) {
            this.recentEmoji = recent;
            return this;
        }

        public EmojiPopup build(final EmojiEditText emojiEditText) {
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
