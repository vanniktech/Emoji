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

    //noinspection ForLoopReplaceableByForEach
    for (int i = 0; i < existingSpans.length; i++) {
      existingSpanPositions.add(text.getSpanStart(existingSpans[i]));
    }

    final List<EmojiRange> findAllEmojis = emojiManager.findAllEmojis(text);

    //noinspection ForLoopReplaceableByForEach
    for (int i = 0; i < findAllEmojis.size(); i++) {
      final EmojiRange location = findAllEmojis.get(i);

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
