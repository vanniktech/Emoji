package com.vanniktech.emoji;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import com.vanniktech.emoji.emoji.Emoji;

public class EmojiEditText extends AppCompatEditText {
  public EmojiEditText(final Context context) {
    this(context, null);
  }

  public EmojiEditText(final Context context, final AttributeSet attrs) {
    super(context, attrs);

    init();
  }

  private void init() {
    if (!isInEditMode()) {
      EmojiManager.getInstance().verifyInstalled();
    }

    setText(getText());
  }

  @Override
  protected void onTextChanged(final CharSequence text, final int start, final int lengthBefore, final int lengthAfter) {
    EmojiHandler.addEmojis(getContext(), getText(), getLineHeight());
  }

  public void backspace() {
    final KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
    dispatchKeyEvent(event);
  }

  public void input(final Emoji emoji) {
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
}
