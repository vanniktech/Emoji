package com.vanniktech.emoji;

import android.content.Context;
import android.text.Spannable;

import com.vanniktech.emoji.EmojiManager.EmojiRange;

import java.util.ArrayList;
import java.util.List;

final class EmojiHandler {
  static void replaceWithImages(final Context context, final Spannable text, final int emojiSize) {
    final EmojiManager emojiManager = EmojiManager.getInstance();
    final List<Integer> existingSpans = new ArrayList<>();

    for (final EmojiSpan span : text.getSpans(0, text.length(), EmojiSpan.class)) {
      existingSpans.add(text.getSpanStart(span));
    }

    for (final EmojiRange location : emojiManager.findAllEmojis(text)) {
      if (!existingSpans.contains(location.getStart())) {
        text.setSpan(new EmojiSpan(context, location.getEmoji().getResource(), emojiSize),
                location.getStart(), location.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }
  }

  private EmojiHandler() {
    throw new AssertionError("No instances.");
  }
}
