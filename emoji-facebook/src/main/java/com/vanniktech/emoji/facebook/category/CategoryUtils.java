package com.vanniktech.emoji.facebook.category;

import com.vanniktech.emoji.facebook.FacebookEmoji;

import java.util.Arrays;

final class CategoryUtils {
  static FacebookEmoji[] concatAll(final FacebookEmoji[] first, final FacebookEmoji[]... rest) {
    int totalLength = first.length;
    for (final FacebookEmoji[] array : rest) {
      totalLength += array.length;
    }

    final FacebookEmoji[] result = Arrays.copyOf(first, totalLength);

    int offset = first.length;
    for (final FacebookEmoji[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }

    return result;
  }

  private CategoryUtils() {
    // No instances.
  }
}
