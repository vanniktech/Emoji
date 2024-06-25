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
    GoogleEmoji("\ud83d\udfe8", listOf("large_yellow_square"), 39, 18),
    GoogleEmoji("\ud83d\udfe9", listOf("large_green_square"), 39, 19),
    GoogleEmoji("\ud83d\udfe6", listOf("large_blue_square"), 39, 16),
    GoogleEmoji("\ud83d\udfea", listOf("large_purple_square"), 39, 20),
    GoogleEmoji("\ud83d\udfeb", listOf("large_brown_square"), 39, 21),
    GoogleEmoji("\u2b1b", listOf("black_large_square"), 60, 50),
    GoogleEmoji("\u2b1c", listOf("white_large_square"), 60, 51),
    GoogleEmoji(
      unicode = "\u25fc",
      shortcodes = listOf("black_medium_square"),
      x = 58,
      y = 5,
      variants = listOf(
        GoogleEmoji("\u25fc\ufe0f", emptyList(), 58, 5),
      ),
    ),
    GoogleEmoji(
      unicode = "\u25fb",
      shortcodes = listOf("white_medium_square"),
      x = 58,
      y = 4,
      variants = listOf(
        GoogleEmoji("\u25fb\ufe0f", emptyList(), 58, 4),
      ),
    ),
    GoogleEmoji("\u25fe", listOf("black_medium_small_square"), 58, 7),
    GoogleEmoji("\u25fd", listOf("white_medium_small_square"), 58, 6),
    GoogleEmoji(
      unicode = "\u25aa",
      shortcodes = listOf("black_small_square"),
      x = 58,
      y = 0,
      variants = listOf(
        GoogleEmoji("\u25aa\ufe0f", emptyList(), 58, 0),
      ),
    ),
    GoogleEmoji(
      unicode = "\u25ab",
      shortcodes = listOf("white_small_square"),
      x = 58,
      y = 1,
      variants = listOf(
        GoogleEmoji("\u25ab\ufe0f", emptyList(), 58, 1),
      ),
    ),
    GoogleEmoji("\ud83d\udd36", listOf("large_orange_diamond"), 30, 46),
    GoogleEmoji("\ud83d\udd37", listOf("large_blue_diamond"), 30, 47),
    GoogleEmoji("\ud83d\udd38", listOf("small_orange_diamond"), 30, 48),
    GoogleEmoji("\ud83d\udd39", listOf("small_blue_diamond"), 30, 49),
    GoogleEmoji("\ud83d\udd3a", listOf("small_red_triangle"), 30, 50),
    GoogleEmoji("\ud83d\udd3b", listOf("small_red_triangle_down"), 30, 51),
    GoogleEmoji("\ud83d\udca0", listOf("diamond_shape_with_a_dot_inside"), 28, 16),
    GoogleEmoji("\ud83d\udd18", listOf("radio_button"), 30, 16),
    GoogleEmoji("\ud83d\udd33", listOf("white_square_button"), 30, 43),
    GoogleEmoji("\ud83d\udd32", listOf("black_square_button"), 30, 42),
  )
}
