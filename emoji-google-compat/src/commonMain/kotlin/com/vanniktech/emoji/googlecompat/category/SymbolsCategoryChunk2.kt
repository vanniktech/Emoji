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

package com.vanniktech.emoji.googlecompat.category

import com.vanniktech.emoji.googlecompat.GoogleCompatEmoji

internal object SymbolsCategoryChunk2 {
  internal val EMOJIS: List<GoogleCompatEmoji> = listOf(
    GoogleCompatEmoji("\ud83d\udfe8", listOf("large_yellow_square")),
    GoogleCompatEmoji("\ud83d\udfe9", listOf("large_green_square")),
    GoogleCompatEmoji("\ud83d\udfe6", listOf("large_blue_square")),
    GoogleCompatEmoji("\ud83d\udfea", listOf("large_purple_square")),
    GoogleCompatEmoji("\ud83d\udfeb", listOf("large_brown_square")),
    GoogleCompatEmoji("\u2b1b", listOf("black_large_square")),
    GoogleCompatEmoji("\u2b1c", listOf("white_large_square")),
    GoogleCompatEmoji(
      unicode = "\u25fc",
      shortcodes = listOf("black_medium_square"),
      variants = listOf(
        GoogleCompatEmoji("\u25fc\ufe0f", emptyList()),
      ),
    ),
    GoogleCompatEmoji(
      unicode = "\u25fb",
      shortcodes = listOf("white_medium_square"),
      variants = listOf(
        GoogleCompatEmoji("\u25fb\ufe0f", emptyList()),
      ),
    ),
    GoogleCompatEmoji("\u25fe", listOf("black_medium_small_square")),
    GoogleCompatEmoji("\u25fd", listOf("white_medium_small_square")),
    GoogleCompatEmoji(
      unicode = "\u25aa",
      shortcodes = listOf("black_small_square"),
      variants = listOf(
        GoogleCompatEmoji("\u25aa\ufe0f", emptyList()),
      ),
    ),
    GoogleCompatEmoji(
      unicode = "\u25ab",
      shortcodes = listOf("white_small_square"),
      variants = listOf(
        GoogleCompatEmoji("\u25ab\ufe0f", emptyList()),
      ),
    ),
    GoogleCompatEmoji("\ud83d\udd36", listOf("large_orange_diamond")),
    GoogleCompatEmoji("\ud83d\udd37", listOf("large_blue_diamond")),
    GoogleCompatEmoji("\ud83d\udd38", listOf("small_orange_diamond")),
    GoogleCompatEmoji("\ud83d\udd39", listOf("small_blue_diamond")),
    GoogleCompatEmoji("\ud83d\udd3a", listOf("small_red_triangle")),
    GoogleCompatEmoji("\ud83d\udd3b", listOf("small_red_triangle_down")),
    GoogleCompatEmoji("\ud83d\udca0", listOf("diamond_shape_with_a_dot_inside")),
    GoogleCompatEmoji("\ud83d\udd18", listOf("radio_button")),
    GoogleCompatEmoji("\ud83d\udd33", listOf("white_square_button")),
    GoogleCompatEmoji("\ud83d\udd32", listOf("black_square_button")),
  )
}
