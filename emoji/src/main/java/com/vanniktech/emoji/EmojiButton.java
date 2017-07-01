package com.vanniktech.emoji;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.CallSuper;
import android.support.annotation.Px;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

public class EmojiButton extends AppCompatButton {
  private float emojiSize;

  public EmojiButton(final Context context) {
    this(context, null);
  }

  public EmojiButton(final Context context, final AttributeSet attrs) {
    super(context, attrs);

    if (!isInEditMode()) {
      EmojiManager.getInstance().verifyInstalled();
    }

    final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
    final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;

    if (attrs == null) {
      emojiSize = defaultEmojiSize;
    } else {
      final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmojiButton);

      try {
        emojiSize = a.getDimension(R.styleable.EmojiButton_emojiSize, defaultEmojiSize);
      } finally {
        a.recycle();
      }
    }

    setText(getText());
  }

  @Override @CallSuper public void setText(final CharSequence rawText, final BufferType type) {
    final CharSequence text = rawText == null ? "" : rawText;
    final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
    EmojiManager.replaceWithImages(getContext(), spannableStringBuilder, emojiSize);
    super.setText(spannableStringBuilder, type);
  }

  public void setEmojiSize(@Px final int pixels) {
    emojiSize = pixels;
    setText(getText()); // Update it.
  }
}
