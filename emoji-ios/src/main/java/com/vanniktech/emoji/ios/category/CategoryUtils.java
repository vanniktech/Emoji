package com.vanniktech.emoji.ios.category;

import com.vanniktech.emoji.ios.IosEmoji;

import java.util.Arrays;

final class CategoryUtils {
  static IosEmoji[] concatAll(final IosEmoji[] first, final IosEmoji[]... rest) {
    int totalLength = first.length;
    for (final IosEmoji[] array : rest) {
      totalLength += array.length;
    }

    final IosEmoji[] result = Arrays.copyOf(first, totalLength);

    int offset = first.length;
    for (final IosEmoji[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }

    return result;
  }

  private CategoryUtils() {
    // No instances.
  }
}
