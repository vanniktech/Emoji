package com.vanniktech.emoji;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.CallSuper;
import android.support.annotation.Px;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

public class EmojiButton extends AppCompatButton {
  private int emojiSize;

  public EmojiButton(final Context context) {
    this(context, null);
  }

  public EmojiButton(final Context context, final AttributeSet attrs) {
    super(context, attrs);

    if (!isInEditMode()) {
      EmojiManager.getInstance().verifyInstalled();
    }

    setText(getText());

    if (attrs == null) {
      emojiSize = getLineHeight();
    } else {
      final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.emoji);

      try {
        emojiSize = (int) a.getDimension(R.styleable.emoji_emojiSize, getLineHeight());
      } finally {
        a.recycle();
      }
    }
  }

  @Override @CallSuper public void setText(final CharSequence rawText, final BufferType type) {
    final CharSequence text = rawText == null ? "" : rawText;
    final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
    EmojiHandler.replaceWithImages(getContext(), spannableStringBuilder, emojiSize);
    super.setText(spannableStringBuilder, type);
  }

  public void setEmojiSize(@Px final int pixels) {
    emojiSize = pixels;
    setText(getText()); // Update it.
  }
}
