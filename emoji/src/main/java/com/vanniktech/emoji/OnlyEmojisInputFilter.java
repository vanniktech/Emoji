package com.vanniktech.emoji;

import android.text.InputFilter;
import android.text.Spanned;

/** InputFilter that only accepts emojis. **/
public final class OnlyEmojisInputFilter implements InputFilter {
  @Override public CharSequence filter(final CharSequence source, final int start, final int end, final Spanned dest, final int dstart, final int dend) {
    if (!EmojiUtils.isOnlyEmojis(source.subSequence(start, end))) {
      return ""; // Reject.
    }

    return null;
  }
}
