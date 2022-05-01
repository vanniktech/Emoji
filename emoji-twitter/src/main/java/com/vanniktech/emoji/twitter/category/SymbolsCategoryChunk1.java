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

package com.vanniktech.emoji.twitter.category;

import com.vanniktech.emoji.twitter.TwitterEmoji;

final class SymbolsCategoryChunk1 {
  @SuppressWarnings("PMD.ExcessiveMethodLength") static TwitterEmoji[] get() {
    return new TwitterEmoji[] {
      new TwitterEmoji(0x1F7E6, new String[]{"large_blue_square"}, 38, 37, false),
      new TwitterEmoji(0x1F7EA, new String[]{"large_purple_square"}, 38, 41, false),
      new TwitterEmoji(0x1F7EB, new String[]{"large_brown_square"}, 38, 42, false),
      new TwitterEmoji(0x2B1B, new String[]{"black_large_square"}, 59, 22, false),
      new TwitterEmoji(0x2B1C, new String[]{"white_large_square"}, 59, 23, false),
      new TwitterEmoji(new int[] { 0x25FC, 0xFE0F }, new String[]{"black_medium_square"}, 56, 37, false),
      new TwitterEmoji(new int[] { 0x25FB, 0xFE0F }, new String[]{"white_medium_square"}, 56, 36, false),
      new TwitterEmoji(0x25FE, new String[]{"black_medium_small_square"}, 56, 39, false),
      new TwitterEmoji(0x25FD, new String[]{"white_medium_small_square"}, 56, 38, false),
      new TwitterEmoji(new int[] { 0x25AA, 0xFE0F }, new String[]{"black_small_square"}, 56, 32, false),
      new TwitterEmoji(new int[] { 0x25AB, 0xFE0F }, new String[]{"white_small_square"}, 56, 33, false),
      new TwitterEmoji(0x1F536, new String[]{"large_orange_diamond"}, 30, 18, false),
      new TwitterEmoji(0x1F537, new String[]{"large_blue_diamond"}, 30, 19, false),
      new TwitterEmoji(0x1F538, new String[]{"small_orange_diamond"}, 30, 20, false),
      new TwitterEmoji(0x1F539, new String[]{"small_blue_diamond"}, 30, 21, false),
      new TwitterEmoji(0x1F53A, new String[]{"small_red_triangle"}, 30, 22, false),
      new TwitterEmoji(0x1F53B, new String[]{"small_red_triangle_down"}, 30, 23, false),
      new TwitterEmoji(0x1F4A0, new String[]{"diamond_shape_with_a_dot_inside"}, 27, 47, false),
      new TwitterEmoji(0x1F518, new String[]{"radio_button"}, 29, 49, false),
      new TwitterEmoji(0x1F533, new String[]{"white_square_button"}, 30, 15, false),
      new TwitterEmoji(0x1F532, new String[]{"black_square_button"}, 30, 14, false)
    };
  }

  private SymbolsCategoryChunk1() {
    // No instances.
  }
}
