/*
 * Copyright (C) 2016 - Niklas Baudy, Ruben Gees, Mario Đanić and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
