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
 */

package com.vanniktech.emoji.google.category

import com.vanniktech.emoji.google.GoogleEmoji

internal object SymbolsCategoryChunk2 {
  internal val EMOJIS: List<GoogleEmoji> = listOf(
    GoogleEmoji(String(intArrayOf(0x1F7E8), 0, 1), listOf("large_yellow_square"), 39, 18),
    GoogleEmoji(String(intArrayOf(0x1F7E9), 0, 1), listOf("large_green_square"), 39, 19),
    GoogleEmoji(String(intArrayOf(0x1F7E6), 0, 1), listOf("large_blue_square"), 39, 16),
    GoogleEmoji(String(intArrayOf(0x1F7EA), 0, 1), listOf("large_purple_square"), 39, 20),
    GoogleEmoji(String(intArrayOf(0x1F7EB), 0, 1), listOf("large_brown_square"), 39, 21),
    GoogleEmoji(String(intArrayOf(0x2B1B), 0, 1), listOf("black_large_square"), 60, 50),
    GoogleEmoji(String(intArrayOf(0x2B1C), 0, 1), listOf("white_large_square"), 60, 51),
    GoogleEmoji(
      unicode = String(intArrayOf(0x25FC), 0, 1),
      shortcodes = listOf("black_medium_square"),
      x = 58,
      y = 5,
      variants = listOf(
        GoogleEmoji(String(intArrayOf(0x25FC, 0xFE0F), 0, 2), emptyList(), 58, 5),
      ),
    ),
    GoogleEmoji(
      unicode = String(intArrayOf(0x25FB), 0, 1),
      shortcodes = listOf("white_medium_square"),
      x = 58,
      y = 4,
      variants = listOf(
        GoogleEmoji(String(intArrayOf(0x25FB, 0xFE0F), 0, 2), emptyList(), 58, 4),
      ),
    ),
    GoogleEmoji(String(intArrayOf(0x25FE), 0, 1), listOf("black_medium_small_square"), 58, 7),
    GoogleEmoji(String(intArrayOf(0x25FD), 0, 1), listOf("white_medium_small_square"), 58, 6),
    GoogleEmoji(
      unicode = String(intArrayOf(0x25AA), 0, 1),
      shortcodes = listOf("black_small_square"),
      x = 58,
      y = 0,
      variants = listOf(
        GoogleEmoji(String(intArrayOf(0x25AA, 0xFE0F), 0, 2), emptyList(), 58, 0),
      ),
    ),
    GoogleEmoji(
      unicode = String(intArrayOf(0x25AB), 0, 1),
      shortcodes = listOf("white_small_square"),
      x = 58,
      y = 1,
      variants = listOf(
        GoogleEmoji(String(intArrayOf(0x25AB, 0xFE0F), 0, 2), emptyList(), 58, 1),
      ),
    ),
    GoogleEmoji(String(intArrayOf(0x1F536), 0, 1), listOf("large_orange_diamond"), 30, 46),
    GoogleEmoji(String(intArrayOf(0x1F537), 0, 1), listOf("large_blue_diamond"), 30, 47),
    GoogleEmoji(String(intArrayOf(0x1F538), 0, 1), listOf("small_orange_diamond"), 30, 48),
    GoogleEmoji(String(intArrayOf(0x1F539), 0, 1), listOf("small_blue_diamond"), 30, 49),
    GoogleEmoji(String(intArrayOf(0x1F53A), 0, 1), listOf("small_red_triangle"), 30, 50),
    GoogleEmoji(String(intArrayOf(0x1F53B), 0, 1), listOf("small_red_triangle_down"), 30, 51),
    GoogleEmoji(String(intArrayOf(0x1F4A0), 0, 1), listOf("diamond_shape_with_a_dot_inside"), 28, 16),
    GoogleEmoji(String(intArrayOf(0x1F518), 0, 1), listOf("radio_button"), 30, 16),
    GoogleEmoji(String(intArrayOf(0x1F533), 0, 1), listOf("white_square_button"), 30, 43),
    GoogleEmoji(String(intArrayOf(0x1F532), 0, 1), listOf("black_square_button"), 30, 42),
  )
}
