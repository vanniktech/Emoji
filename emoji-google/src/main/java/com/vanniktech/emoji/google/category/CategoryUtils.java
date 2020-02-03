package com.vanniktech.emoji.google.category;

import com.vanniktech.emoji.google.GoogleEmoji;

import java.util.Arrays;

final class CategoryUtils {
  static GoogleEmoji[] concatAll(final GoogleEmoji[] first, final GoogleEmoji[]... rest) {
    int totalLength = first.length;
    for (final GoogleEmoji[] array : rest) {
      totalLength += array.length;
    }

    final GoogleEmoji[] result = Arrays.copyOf(first, totalLength);

    int offset = first.length;
    for (final GoogleEmoji[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }

    return result;
  }

  private CategoryUtils() {
    // No instances.
  }
}
