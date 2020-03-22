package com.vanniktech.emoji.twitter.category;

import com.vanniktech.emoji.twitter.TwitterEmoji;

import java.util.Arrays;

final class CategoryUtils {
  static TwitterEmoji[] concatAll(final TwitterEmoji[] first, final TwitterEmoji[]... rest) {
    int totalLength = first.length;
    for (final TwitterEmoji[] array : rest) {
      totalLength += array.length;
    }

    final TwitterEmoji[] result = Arrays.copyOf(first, totalLength);

    int offset = first.length;
    for (final TwitterEmoji[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }

    return result;
  }

  private CategoryUtils() {
    // No instances.
  }
}
