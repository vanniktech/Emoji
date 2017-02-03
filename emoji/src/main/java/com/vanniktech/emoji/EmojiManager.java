package com.vanniktech.emoji;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.emoji.EmojiTree;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class EmojiManager {
  private static final EmojiManager INSTANCE = new EmojiManager();

  private Map<String, EmojiCategory> categories = new LinkedHashMap<>();
  private EmojiTree emojiTree = new EmojiTree();

  private EmojiManager() {
    // No instances apart from singleton.
  }

  public static EmojiManager getInstance() {
    return INSTANCE;
  }

  public static void install(@NonNull final EmojiProvider provider) {
    if (!INSTANCE.categories.isEmpty() || !INSTANCE.emojiTree.isEmpty()) {
      throw new IllegalStateException("Please call the install method only once.");
    }

    for (final Map.Entry<String, EmojiCategory> entry : provider.getCategories().entrySet()) {
      INSTANCE.categories.put(entry.getKey(), entry.getValue());

      for (final Emoji emoji : entry.getValue().getEmojis()) {
        INSTANCE.emojiTree.add(emoji);
      }
    }
  }

  public static void destroy() {
    INSTANCE.categories = new LinkedHashMap<>();
    INSTANCE.emojiTree = new EmojiTree();
  }

  public List<Pair<String, EmojiCategory>> getCategories() {
    verifyInstalled();

    final List<Pair<String, EmojiCategory>> result = new ArrayList<>(categories.size());

    for (final Map.Entry<String, EmojiCategory> entry : categories.entrySet()) {
      result.add(new Pair<>(entry.getKey(), entry.getValue()));
    }

    return result;
  }

  private void verifyInstalled() {
    if (categories.isEmpty()) {
      throw new IllegalStateException(
          "Please install an EmojiProvider through the install method first.");
    }
  }

  @Nullable public Emoji findEmoji(@NonNull final CharSequence candiate) {
    verifyInstalled();
    return emojiTree.findEmoji(candiate);
  }
}
