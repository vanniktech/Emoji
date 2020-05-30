package com.vanniktech.emoji.material;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import androidx.annotation.CallSuper;
import androidx.annotation.DimenRes;
import androidx.annotation.Px;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.EmojiDisplayable;

@SuppressWarnings("CPD-START") public class EmojiMaterialRadioButton extends MaterialRadioButton implements EmojiDisplayable {
  private float emojiSize;

  public EmojiMaterialRadioButton(final Context context) {
    this(context, null);
  }

  public EmojiMaterialRadioButton(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    emojiSize = Utils.initTextView(this, attrs);
  }

  @Override @CallSuper public void setText(final CharSequence rawText, final BufferType type) {
    final CharSequence text = rawText == null ? "" : rawText;
    final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
    final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
    final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;
    EmojiManager.getInstance().replaceWithImages(getContext(), spannableStringBuilder, emojiSize, defaultEmojiSize);
    super.setText(spannableStringBuilder, type);
  }

  @Override public float getEmojiSize() {
    return emojiSize;
  }

  @Override public final void setEmojiSize(@Px final int pixels) {
    setEmojiSize(pixels, true);
  }

  @Override public final void setEmojiSize(@Px final int pixels, final boolean shouldInvalidate) {
    emojiSize = pixels;

    if (shouldInvalidate) {
      setText(getText());
    }
  }

  @Override public final void setEmojiSizeRes(@DimenRes final int res) {
    setEmojiSizeRes(res, true);
  }

  @Override public final void setEmojiSizeRes(@DimenRes final int res, final boolean shouldInvalidate) {
    setEmojiSize(getResources().getDimensionPixelSize(res), shouldInvalidate);
  }
}
