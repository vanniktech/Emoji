package com.vanniktech.emoji;

import android.text.InputFilter;
import android.text.Spanned;
import androidx.annotation.Nullable;

public final class OnlyEmojisInputFilter implements InputFilter {
  @Nullable private final Integer maxCount;

  public OnlyEmojisInputFilter() {
    maxCount = null;
  }

  public OnlyEmojisInputFilter(final int maxCount) {
    this.maxCount = maxCount;
  }

  @Override public CharSequence filter(final CharSequence source, final int start, final int end, final Spanned dest, final int dstart, final int dend) {
    if (!EmojiUtils.isOnlyEmojis(source.subSequence(start, end))) {
      return ""; // Reject.
    }

    if (maxCount != null) {
      final EmojiInformation emojiInformation = EmojiUtils.emojiInformation(dest.subSequence(0, dest.length()));

      if (emojiInformation.emojis.size() >= maxCount) {
        return ""; // Reject.
      }
    }

    return null;
  }
}
