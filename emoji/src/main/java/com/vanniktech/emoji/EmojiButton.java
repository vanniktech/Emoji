package com.vanniktech.emoji;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

public class EmojiButton extends AppCompatButton {
  public EmojiButton(final Context context) {
    this(context, null);
  }

  public EmojiButton(final Context context, final AttributeSet attrs) {
    super(context, attrs);

    if (!isInEditMode()) {
      EmojiManager.getInstance().verifyInstalled();
    }

    setText(getText());
  }

  @Override @CallSuper public void setText(final CharSequence rawText, final BufferType type) {
    final CharSequence text = rawText == null ? "" : rawText;
    final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
    EmojiHandler.replaceWithImages(getContext(), spannableStringBuilder, getLineHeight());
    super.setText(spannableStringBuilder, type);
  }
}
