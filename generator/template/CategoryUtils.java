package com.vanniktech.emoji.<%= package %>.category;

import com.vanniktech.emoji.<%= package %>.<%= name %>;

import java.util.Arrays;

final class CategoryUtils {
  static <%= name %>[] concatAll(final <%= name %>[] first, final <%= name %>[]... rest) {
    int totalLength = first.length;
    for (final <%= name %>[] array : rest) {
      totalLength += array.length;
    }

    final <%= name %>[] result = Arrays.copyOf(first, totalLength);

    int offset = first.length;
    for (final <%= name %>[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }

    return result;
  }

  private CategoryUtils() {
    // No instances.
  }
}
