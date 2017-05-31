package com.vanniktech.emoji;

import android.content.Context;
import android.text.Spannable;
import com.vanniktech.emoji.EmojiManager.EmojiRange;
import java.util.ArrayList;
import java.util.List;

final class EmojiHandler {
  static void replaceWithImages(final Context context, final Spannable text, final int emojiSize) {
    final EmojiManager emojiManager = EmojiManager.getInstance();
    final EmojiSpan[] existingSpans = text.getSpans(0, text.length(), EmojiSpan.class);
    final List<Integer> existingSpanPositions = new ArrayList<>(existingSpans.length);

    for (final EmojiSpan existingSpan : existingSpans) {
      existingSpanPositions.add(text.getSpanStart(existingSpan));
    }

    for (final EmojiRange location : emojiManager.findAllEmojis(text)) {
      if (!existingSpanPositions.contains(location.start)) {
        text.setSpan(new EmojiSpan(context, location.emoji.getResource(), emojiSize),
                location.start, location.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }
  }

  private EmojiHandler() {
    throw new AssertionError("No instances.");
  }
}
