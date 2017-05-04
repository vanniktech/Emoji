package com.vanniktech.emoji;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;

public class EmojiTextView extends AppCompatTextView {
  public EmojiTextView(final Context context) {
    this(context, null);
  }

  public EmojiTextView(final Context context, final AttributeSet attrs) {
    super(context, attrs);

    init();
  }

  private void init() {
    if (!isInEditMode()) {
      EmojiManager.getInstance().verifyInstalled();
    }

    setText(getText());
  }

  @Override public void setText(final CharSequence rawText, final BufferType type) {
    final CharSequence text = rawText == null ? "" : rawText;
    final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
    EmojiHandler.addEmojis(getContext(), spannableStringBuilder, getLineHeight());
    super.setText(spannableStringBuilder, type);
  }
}
