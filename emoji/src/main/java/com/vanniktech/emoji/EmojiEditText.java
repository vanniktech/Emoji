package com.vanniktech.emoji;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.CallSuper;
import android.support.annotation.Px;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import com.vanniktech.emoji.emoji.Emoji;

public class EmojiEditText extends AppCompatEditText {
  private int emojiSize;

  public EmojiEditText(final Context context) {
    this(context, null);
  }

  public EmojiEditText(final Context context, final AttributeSet attrs) {
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

  @Override @CallSuper protected void onTextChanged(final CharSequence text, final int start, final int lengthBefore, final int lengthAfter) {
    EmojiHandler.replaceWithImages(getContext(), getText(), emojiSize);
  }

  @CallSuper public void backspace() {
    final KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
    dispatchKeyEvent(event);
  }

  @CallSuper public void input(final Emoji emoji) {
    if (emoji != null) {
      final int start = getSelectionStart();
      final int end = getSelectionEnd();

      if (start < 0) {
        append(emoji.getUnicode());
      } else {
        getText().replace(Math.min(start, end), Math.max(start, end), emoji.getUnicode(), 0, emoji.getUnicode().length());
      }
    }
  }

  public void setEmojiSize(@Px final int pixels) {
    emojiSize = pixels;
    setText(getText()); // Update it.
  }
}
