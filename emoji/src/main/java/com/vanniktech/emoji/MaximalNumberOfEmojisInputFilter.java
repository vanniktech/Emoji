package com.vanniktech.emoji;

import android.text.InputFilter;
import android.text.Spanned;

/** Input Filter that accepts only a certain number of Emojis. */
public final class MaximalNumberOfEmojisInputFilter implements InputFilter {
  private final int maxCount;

  public MaximalNumberOfEmojisInputFilter(final int maxCount) {
    this.maxCount = maxCount;
  }

  @Override public CharSequence filter(final CharSequence source, final int start, final int end, final Spanned dest, final int dstart, final int dend) {
    final EmojiInformation emojiInformation = EmojiUtils.emojiInformation(dest.subSequence(0, dest.length()));

    if (emojiInformation.emojis.size() >= maxCount) {
      return ""; // Reject.
    }

    return null;
  }
}
