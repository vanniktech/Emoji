package com.vanniktech.emoji.material;

import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;
import com.vanniktech.emoji.EmojiManager;

final class Utils {
  static float initTextView(final TextView textView, final AttributeSet attrs) {
    if (!textView.isInEditMode()) {
      EmojiManager.getInstance().verifyInstalled();
    }

    final Paint.FontMetrics fontMetrics = textView.getPaint().getFontMetrics();
    final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;

    final float emojiSize;

    if (attrs == null) {
      emojiSize = defaultEmojiSize;
    } else {
      final TypedArray a = textView.getContext().obtainStyledAttributes(attrs, com.vanniktech.emoji.R.styleable.EmojiTextView);

      try {
        emojiSize = a.getDimension(com.vanniktech.emoji.R.styleable.EmojiTextView_emojiSize, defaultEmojiSize);
      } finally {
        a.recycle();
      }
    }

    textView.setText(textView.getText());
    return emojiSize;
  }

  private Utils() {
    throw new AssertionError("No instances.");
  }
}
