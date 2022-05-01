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

package com.vanniktech.emoji.googlecompat.category;

import com.vanniktech.emoji.googlecompat.GoogleCompatEmoji;

final class SymbolsCategoryChunk1 {
  @SuppressWarnings("PMD.ExcessiveMethodLength") static GoogleCompatEmoji[] get() {
    return new GoogleCompatEmoji[] {
      new GoogleCompatEmoji(0x1F7E6, new String[]{"large_blue_square"}, false),
      new GoogleCompatEmoji(0x1F7EA, new String[]{"large_purple_square"}, false),
      new GoogleCompatEmoji(0x1F7EB, new String[]{"large_brown_square"}, false),
      new GoogleCompatEmoji(0x2B1B, new String[]{"black_large_square"}, false),
      new GoogleCompatEmoji(0x2B1C, new String[]{"white_large_square"}, false),
      new GoogleCompatEmoji(new int[] { 0x25FC, 0xFE0F }, new String[]{"black_medium_square"}, false),
      new GoogleCompatEmoji(new int[] { 0x25FB, 0xFE0F }, new String[]{"white_medium_square"}, false),
      new GoogleCompatEmoji(0x25FE, new String[]{"black_medium_small_square"}, false),
      new GoogleCompatEmoji(0x25FD, new String[]{"white_medium_small_square"}, false),
      new GoogleCompatEmoji(new int[] { 0x25AA, 0xFE0F }, new String[]{"black_small_square"}, false),
      new GoogleCompatEmoji(new int[] { 0x25AB, 0xFE0F }, new String[]{"white_small_square"}, false),
      new GoogleCompatEmoji(0x1F536, new String[]{"large_orange_diamond"}, false),
      new GoogleCompatEmoji(0x1F537, new String[]{"large_blue_diamond"}, false),
      new GoogleCompatEmoji(0x1F538, new String[]{"small_orange_diamond"}, false),
      new GoogleCompatEmoji(0x1F539, new String[]{"small_blue_diamond"}, false),
      new GoogleCompatEmoji(0x1F53A, new String[]{"small_red_triangle"}, false),
      new GoogleCompatEmoji(0x1F53B, new String[]{"small_red_triangle_down"}, false),
      new GoogleCompatEmoji(0x1F4A0, new String[]{"diamond_shape_with_a_dot_inside"}, false),
      new GoogleCompatEmoji(0x1F518, new String[]{"radio_button"}, false),
      new GoogleCompatEmoji(0x1F533, new String[]{"white_square_button"}, false),
      new GoogleCompatEmoji(0x1F532, new String[]{"black_square_button"}, false)
    };
  }

  private SymbolsCategoryChunk1() {
    // No instances.
  }
}
