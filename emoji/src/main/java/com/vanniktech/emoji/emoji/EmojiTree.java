package com.vanniktech.emoji.emoji;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.util.SparseArrayCompat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

/**
 * Data structure for holding the emojis and allow easy finding later.
 */
@RestrictTo(LIBRARY) public final class EmojiTree {
  private static final int SKIN_TONED_RESULT_CAPACITY = 6;
  private static final char SKIN_TONE_PART = '\uD83C';

  private EmojiNode root = new EmojiNode(null);

  public void add(@NonNull final Emoji emoji) {
    final String unicode = emoji.getUnicode();

    EmojiNode current = root;

    for (int i = 0; i < unicode.length() - 1; i++) {
      current = current.appendOrGet(unicode.charAt(i));
    }

    current.appendLast(unicode.charAt(unicode.length() - 1), emoji);
  }

  @Nullable public Emoji findEmoji(@NonNull final CharSequence candidate) {
    EmojiNode current = root;
    Emoji result = null;

    for (int i = 0; i < candidate.length(); i++) {
      current = current.getChild(candidate.charAt(i));

      if (current == null) {
        break;
      } else if (current.getEmoji() != null) {
        result = current.getEmoji();
      }
    }

    return result;
  }

  public List<Emoji> findSkinTonedEmojis(@NonNull final Emoji emoji){
    final String unicode = emoji.getUnicode();
    EmojiNode current = root;

    for (int i = 0; i < unicode.length(); i++){
      current = current.getChild(unicode.charAt(i));

      if (current == null) {
        throw new IllegalArgumentException("Invalid emoji");
      }
    }

    current = current.getChild(SKIN_TONE_PART);

    if (current != null) {
      final List<Emoji> result = new ArrayList<>(SKIN_TONED_RESULT_CAPACITY);

      for (int i = 0; i < current.children.size(); i++) {
        final Emoji candidate = current.children.valueAt(i).getEmoji();

        if (candidate != null && candidate.isSkinToned()) {
          result.add(candidate);
        }
      }

      return result;
    }

    return Collections.emptyList();
  }

  public boolean isEmpty() {
    return root.children.size() <= 0;
  }

  public void clear() {
    root = new EmojiNode(null);
  }

  static class EmojiNode {
    final SparseArrayCompat<EmojiNode> children = new SparseArrayCompat<>();
    private Emoji emoji;

    EmojiNode(@Nullable final Emoji emoji) {
      this.emoji = emoji;
    }

    @Nullable EmojiNode getChild(final char child) {
      return children.get(child);
    }

    @Nullable Emoji getEmoji() {
      return emoji;
    }

    void setEmoji(@NonNull final Emoji emoji) {
      this.emoji = emoji;
    }

    @NonNull EmojiNode appendOrGet(final char child) {
      EmojiNode existing = children.get(child);

      if (existing == null) {
        existing = new EmojiNode(null);

        children.put(child, existing);
      }

      return existing;
    }

    void appendLast(final char child, @NonNull final Emoji newEmoji) {
      EmojiNode existing = children.get(child);

      if (existing == null) {
        existing = new EmojiNode(newEmoji);

        children.put(child, existing);
      } else {
        existing.setEmoji(newEmoji);
      }
    }
  }
}
