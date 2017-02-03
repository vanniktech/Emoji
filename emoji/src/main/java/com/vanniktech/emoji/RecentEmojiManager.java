package com.vanniktech.emoji;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.vanniktech.emoji.emoji.Emoji;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

final class RecentEmojiManager implements RecentEmoji {
    private static final String PREFERENCE_NAME = "emoji-recent-manager";
    private static final String TIME_DELIMITER = ";";
    private static final String EMOJI_DELIMITER = "~";
    private static final String RECENT_EMOJIS = "recent-emojis";
    private static final int EMOJI_GUESS_SIZE = 5;
    private static final int MAX_RECENTS = 40;

    @NonNull private final Context context;
    @NonNull private EmojiList emojiList = new EmojiList(0);

    RecentEmojiManager(@NonNull final Context context) {
        this.context = context.getApplicationContext();
    }

    @SuppressWarnings("PMD.AvoidDeeplyNestedIfStmts")
    @NonNull
    @Override
    public Collection<Emoji> getRecentEmojis() {
        if (emojiList.size() == 0) {
            final String savedRecentEmojis = getPreferences().getString(RECENT_EMOJIS, "");

            if (savedRecentEmojis.length() > 0) {
                final StringTokenizer stringTokenizer = new StringTokenizer(savedRecentEmojis, EMOJI_DELIMITER);
                emojiList = new EmojiList(stringTokenizer.countTokens());

                while (stringTokenizer.hasMoreTokens()) {
                    final String token = stringTokenizer.nextToken();

                    final String[] parts = token.split(TIME_DELIMITER);

                    if (parts.length == 2) {
                        final Emoji emoji = EmojiManager.getInstance().findEmoji(parts[0]);

                        if (emoji != null && emoji.getLength() == parts[0].length()) {
                            final long timestamp = Long.parseLong(parts[1]);

                            emojiList.add(emoji, timestamp);
                        }
                    }
                }
            } else {
                emojiList = new EmojiList(0);
            }
        }

        return emojiList.getEmojis();
    }

    @Override
    public void addEmoji(@NonNull final Emoji emoji) {
        emojiList.add(emoji);
    }

    @Override
    public void persist() {
        if (emojiList.size() > 0) {
            final StringBuilder stringBuilder = new StringBuilder(emojiList.size() * EMOJI_GUESS_SIZE);
            for (final Data data : emojiList) {
                stringBuilder.append(data.emoji.getUnicode()).append(TIME_DELIMITER).append(data.timestamp).append(EMOJI_DELIMITER);
            }

            stringBuilder.setLength(stringBuilder.length() - EMOJI_DELIMITER.length());

            getPreferences().edit().putString(RECENT_EMOJIS, stringBuilder.toString()).apply();
        }
    }

    private SharedPreferences getPreferences() {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    private static class EmojiList implements Iterable<Data> {
        private static final Comparator<Data> COMPARATOR = new Comparator<Data>() {
            @Override
            public int compare(final Data lhs, final Data rhs) {
                return Long.valueOf(rhs.timestamp).compareTo(lhs.timestamp);
            }
        };

        @NonNull
        private final List<Data> emojis;

        EmojiList(final int size) {
            emojis = new ArrayList<>(size);
        }

        void add(final Emoji emoji) {
            add(emoji, System.currentTimeMillis());
        }

        void add(final Emoji emoji, final long timestamp) {
            final Iterator<Data> iterator = emojis.iterator();

            while (iterator.hasNext()) {
                final Data data = iterator.next();

                if (data.emoji.equals(emoji)) {
                    iterator.remove();
                }
            }

            emojis.add(0, new Data(emoji, timestamp));

            if (emojis.size() > MAX_RECENTS) {
                emojis.remove(MAX_RECENTS);
            }
        }

        Collection<Emoji> getEmojis() {
            Collections.sort(emojis, COMPARATOR);

            final Collection<Emoji> sortedEmojis = new ArrayList<>(emojis.size());

            for (final Data data : emojis) {
                sortedEmojis.add(data.emoji);
            }

            return sortedEmojis;
        }

        int size() {
            return emojis.size();
        }

        @Override
        public Iterator<Data> iterator() {
            return emojis.iterator();
        }
    }

    private static class Data {
        final Emoji emoji;
        final long timestamp;

        Data(final Emoji emoji, final long timestamp) {
            this.emoji = emoji;
            this.timestamp = timestamp;
        }
    }
}
