package com.vanniktech.emoji;

import android.content.Context;
import android.text.Spannable;

import com.vanniktech.emoji.emoji.Emoji;

final class EmojiHandler {
    private EmojiHandler() {
        throw new AssertionError("No instances.");
    }

    static void addEmojis(final Context context, final Spannable text, final int emojiSize) {
        for (final EmojiSpan oldSpan : text.getSpans(0, text.length(), EmojiSpan.class)) {
            text.removeSpan(oldSpan);
        }

        int i = 0;

        while (i < text.length()) {
            final Emoji found = EmojiManager.getInstance().findEmoji(text.subSequence(i, text.length()));

            if (found != null) {
                text.setSpan(new EmojiSpan(context, found.getResource(), emojiSize), i,
                        i + found.getLength(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

                i += found.getLength();
            } else {
                i++;
            }
        }
    }
}
