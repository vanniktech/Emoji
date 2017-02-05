package com.vanniktech.emoji;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.emoji.EmojiTree;

import static com.vanniktech.emoji.Utils.checkNotNull;

public final class EmojiManager {
  private static final EmojiManager INSTANCE = new EmojiManager();

  private EmojiCategory[] categories;
  private final EmojiTree emojiTree = new EmojiTree();

  private EmojiManager() {
    // No instances apart from singleton.
  }

  static EmojiManager getInstance() {
    return INSTANCE;
  }

  public static void install(@NonNull final EmojiProvider provider) {
    INSTANCE.categories = checkNotNull(provider.getCategories(), "categories == null");
    INSTANCE.emojiTree.clear();

    //noinspection ForLoopReplaceableByForEach
    for (int i = 0; i < INSTANCE.categories.length; i++) {
      final Emoji[] emojis = checkNotNull(INSTANCE.categories[i].getEmojis(), "emojies == null");

      //noinspection ForLoopReplaceableByForEach
      for (int j = 0; j < emojis.length; j++) {
        INSTANCE.emojiTree.add(emojis[j]);
      }
    }
  }

  EmojiCategory[] getCategories() {
    verifyInstalled();
    return categories; // NOPMD
  }

  @Nullable Emoji findEmoji(@NonNull final CharSequence candiate) {
    verifyInstalled();
    return emojiTree.findEmoji(candiate);
  }

  private void verifyInstalled() {
    if (categories == null) {
      throw new IllegalStateException("Please install an EmojiProvider through the install method first.");
    }
  }
}
