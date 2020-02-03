package com.vanniktech.emoji.googlecompat.category;

import com.vanniktech.emoji.googlecompat.GoogleCompatEmoji;

import java.util.Arrays;

final class CategoryUtils {
  static GoogleCompatEmoji[] concatAll(final GoogleCompatEmoji[] first, final GoogleCompatEmoji[]... rest) {
    int totalLength = first.length;
    for (final GoogleCompatEmoji[] array : rest) {
      totalLength += array.length;
    }

    final GoogleCompatEmoji[] result = Arrays.copyOf(first, totalLength);

    int offset = first.length;
    for (final GoogleCompatEmoji[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }

    return result;
  }

  private CategoryUtils() {
    // No instances.
  }
}
