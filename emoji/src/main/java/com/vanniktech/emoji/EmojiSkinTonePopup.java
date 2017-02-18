package com.vanniktech.emoji;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;

import java.util.List;

class EmojiSkinTonePopup {
  private static final int MARGIN = 4;

  private PopupWindow popupWindow;

  @Nullable private final OnEmojiClickedListener listener;

  EmojiSkinTonePopup(@Nullable final OnEmojiClickedListener listener) {
    this.listener = listener;
  }

  void show(@NonNull final View clickedImage, @NonNull final Emoji emoji) {
    dismiss();

    final View content = View.inflate(clickedImage.getContext(), R.layout.emoji_skin_popup, null);
    final LinearLayout imageContainer = (LinearLayout) content.findViewById(R.id.container);
    popupWindow = new PopupWindow(content,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
    );

    final List<Emoji> skinTonedEmojis = EmojiManager.getInstance().findSkinTonedEmojis(emoji);
    skinTonedEmojis.add(0, emoji);

    for (final Emoji tonedEmoji : skinTonedEmojis) {
      final ImageView emojiImage = (ImageView) LayoutInflater.from(clickedImage.getContext())
              .inflate(R.layout.emoji_item, imageContainer, false);
      final ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) emojiImage.getLayoutParams();
      final int margin = (int) Utils.dpToPx(clickedImage.getContext(), MARGIN);

      // Use the same size for Emojis as the picker
      layoutParams.width = clickedImage.getWidth();
      layoutParams.setMargins(margin, margin, margin, margin);
      emojiImage.setImageResource(tonedEmoji.getResource());

      emojiImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
          if (listener != null) {
            listener.onEmojiClicked(tonedEmoji);
          }
        }
      });

      imageContainer.addView(emojiImage);
    }

    content.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

    popupWindow.setOutsideTouchable(true);
    popupWindow.setBackgroundDrawable(new BitmapDrawable(clickedImage.getContext().getResources(), (Bitmap) null));

    final int[] location = new int[2];
    clickedImage.getLocationOnScreen(location);

    final int x = location[0] - popupWindow.getContentView().getMeasuredWidth() / 2 + clickedImage.getWidth() / 2;
    final int y = location[1] - popupWindow.getContentView().getMeasuredHeight();

    popupWindow.showAtLocation(((Activity) clickedImage.getContext()).getWindow().getDecorView(),
            Gravity.NO_GRAVITY, x, y);
  }

  void dismiss() {
    if (popupWindow != null) {
      popupWindow.dismiss();
      popupWindow = null;
    }
  }
}
