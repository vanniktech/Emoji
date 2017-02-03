package com.vanniktech.emoji;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.emoji.EmojiTree;

import java.util.ArrayList;
import java.util.List;

public final class EmojiManager {
    private static final EmojiManager INSTANCE = new EmojiManager();

    private List<EmojiCategory> categories = new ArrayList<>();
    private EmojiTree emojiTree = new EmojiTree();

    private EmojiManager() {
        // No instances apart from singleton.
    }

    static EmojiManager getInstance() {
        return INSTANCE;
    }

    public static void install(@NonNull final EmojiProvider provider) {
        if (!INSTANCE.categories.isEmpty() || !INSTANCE.emojiTree.isEmpty()) {
            throw new IllegalStateException("Please call the install method only once.");
        }

        for (final EmojiCategory entry : provider.getCategories()) {
            INSTANCE.categories.add(entry);

            for (final Emoji emoji : entry.getEmojis()) {
                INSTANCE.emojiTree.add(emoji);
            }
        }
    }

    static void destroy() {
        INSTANCE.categories = new ArrayList<>();
        INSTANCE.emojiTree = new EmojiTree();
    }

    List<EmojiCategory> getCategories() {
        verifyInstalled();

        return new ArrayList<>(categories);
    }

    @Nullable Emoji findEmoji(@NonNull final CharSequence candiate) {
        verifyInstalled();
        return emojiTree.findEmoji(candiate);
    }

    private void verifyInstalled() {
        if (categories.isEmpty()) {
            throw new IllegalStateException(
                    "Please install an EmojiProvider through the install method first.");
        }
    }
}
