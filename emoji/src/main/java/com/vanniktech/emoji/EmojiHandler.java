package com.vanniktech.emoji;

import android.content.Context;
import android.support.v4.util.Pair;
import android.text.Spannable;
import com.vanniktech.emoji.emoji.Emoji;
import java.util.ArrayList;
import java.util.List;

import static com.vanniktech.emoji.EmojiHandler.SpanRangeList.SPAN_NOT_FOUND;

final class EmojiHandler {
  private EmojiHandler() {
    throw new AssertionError("No instances.");
  }

  static void addEmojis(final Context context, final Spannable text, final int emojiSize) {
    final SpanRangeList existingSpanRanges = new SpanRangeList(text);
    final EmojiManager emojiManager = EmojiManager.getInstance();
    int index = 0;

    while (index < text.length()) {
      final int existingSpanEnd = existingSpanRanges.spanEnd(index);

      if (existingSpanEnd == SPAN_NOT_FOUND) {
        final int nextSpanStart = existingSpanRanges.nextSpanStart(index);
        final int searchRange = nextSpanStart == SPAN_NOT_FOUND ? text.length() : nextSpanStart;
        final Emoji found = emojiManager.findEmoji(text.subSequence(index, searchRange));

        if (found != null) {
          text.setSpan(new EmojiSpan(context, found.getResource(), emojiSize), index, index + found.getLength(),
                  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

          index += found.getLength();
        } else {
          index++;
        }
      } else {
        index += existingSpanEnd - index;
      }
    }
  }

  static final class SpanRangeList {
    static final int SPAN_NOT_FOUND = -1;

    private final List<Pair<Integer, Integer>> spanRanges = new ArrayList<>();

    SpanRangeList(final Spannable text) {
      for (final EmojiSpan span : text.getSpans(0, text.length(), EmojiSpan.class)) {
        spanRanges.add(new Pair<>(text.getSpanStart(span), text.getSpanEnd(span)));
      }
    }

    int spanEnd(final int index) {
      for (final Pair<Integer, Integer> spanRange : spanRanges) {
        if (spanRange.first == index) {
          return spanRange.second;
        }
      }

      return SPAN_NOT_FOUND;
    }

    int nextSpanStart(final int index) {
      for (final Pair<Integer, Integer> spanRange : spanRanges) {
        if (spanRange.first > index) {
          return spanRange.first;
        }
      }

      return SPAN_NOT_FOUND;
    }
  }
}
