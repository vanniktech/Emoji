package com.vanniktech.emoji;

import android.content.Context;
import android.text.Spannable;
import com.vanniktech.emoji.emoji.Emoji;

final class EmojiHandler {
  static void addEmojis(final Context context, final Spannable text, final int emojiSize) {
    final EmojiSpan[] spans = text.getSpans(0, text.length(), EmojiSpan.class);

    for (final EmojiSpan oldSpan : spans) {
      text.removeSpan(oldSpan);
    }

    int i = 0;

    final EmojiManager instance = EmojiManager.getInstance();

    while (i < text.length()) {
      final Emoji found = instance.findEmoji(text.subSequence(i, text.length()));

      if (found != null) {
        text.setSpan(new EmojiSpan(context, found.getResource(), emojiSize), i, i + found.getLength(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        i += found.getLength();
      } else {
        i++;
      }
    }
  }

  private EmojiHandler() {
    throw new AssertionError("No instances.");
  }
}
