package com.vanniktech.emoji;

import android.content.Context;
import android.text.Spannable;
import android.util.SparseIntArray;

import com.vanniktech.emoji.emoji.Emoji;

final class EmojiHandler {
  private EmojiHandler() {
    throw new AssertionError("No instances.");
  }

  static void addEmojis(final Context context, final Spannable text, final int emojiSize) {
    final EmojiSpan[] existingSpans = text.getSpans(0, text.length(), EmojiSpan.class);
    final SparseIntArray existingSpanRanges = new SparseIntArray();

    for (final EmojiSpan span : existingSpans) {
      final int spanStart = text.getSpanStart(span);
      final int spanEnd = text.getSpanEnd(span);

      existingSpanRanges.append(spanStart, spanEnd);
    }

    int i = 0;
    final EmojiManager emojiManager = EmojiManager.getInstance();

    while (i < text.length()) {
      final int existingSpanEnd = existingSpanRanges.get(i, -1);

      if (existingSpanEnd == -1) {
        final Emoji found = emojiManager.findEmoji(text.subSequence(i, text.length()));

        if (found != null) {
          text.setSpan(new EmojiSpan(context, found.getResource(), emojiSize), i, i + found.getLength(),
                  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

          i += found.getLength();
        } else {
          i++;
        }
      } else {
        i += existingSpanEnd - i;
      }
    }
  }
}
