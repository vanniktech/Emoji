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

package com.vanniktech.emoji.androidxemoji2.category

import com.vanniktech.emoji.androidxemoji2.AndroidxEmoji2

internal object SymbolsCategoryChunk2 {
  internal val EMOJIS: List<AndroidxEmoji2> = listOf(
    AndroidxEmoji2("\ud83d\udfe8", listOf("large_yellow_square")),
    AndroidxEmoji2("\ud83d\udfe9", listOf("large_green_square")),
    AndroidxEmoji2("\ud83d\udfe6", listOf("large_blue_square")),
    AndroidxEmoji2("\ud83d\udfea", listOf("large_purple_square")),
    AndroidxEmoji2("\ud83d\udfeb", listOf("large_brown_square")),
    AndroidxEmoji2("\u2b1b", listOf("black_large_square")),
    AndroidxEmoji2("\u2b1c", listOf("white_large_square")),
    AndroidxEmoji2(
      unicode = "\u25fc",
      shortcodes = listOf("black_medium_square"),
      variants = listOf(
        AndroidxEmoji2("\u25fc\ufe0f", emptyList()),
      ),
    ),
    AndroidxEmoji2(
      unicode = "\u25fb",
      shortcodes = listOf("white_medium_square"),
      variants = listOf(
        AndroidxEmoji2("\u25fb\ufe0f", emptyList()),
      ),
    ),
    AndroidxEmoji2("\u25fe", listOf("black_medium_small_square")),
    AndroidxEmoji2("\u25fd", listOf("white_medium_small_square")),
    AndroidxEmoji2(
      unicode = "\u25aa",
      shortcodes = listOf("black_small_square"),
      variants = listOf(
        AndroidxEmoji2("\u25aa\ufe0f", emptyList()),
      ),
    ),
    AndroidxEmoji2(
      unicode = "\u25ab",
      shortcodes = listOf("white_small_square"),
      variants = listOf(
        AndroidxEmoji2("\u25ab\ufe0f", emptyList()),
      ),
    ),
    AndroidxEmoji2("\ud83d\udd36", listOf("large_orange_diamond")),
    AndroidxEmoji2("\ud83d\udd37", listOf("large_blue_diamond")),
    AndroidxEmoji2("\ud83d\udd38", listOf("small_orange_diamond")),
    AndroidxEmoji2("\ud83d\udd39", listOf("small_blue_diamond")),
    AndroidxEmoji2("\ud83d\udd3a", listOf("small_red_triangle")),
    AndroidxEmoji2("\ud83d\udd3b", listOf("small_red_triangle_down")),
    AndroidxEmoji2("\ud83d\udca0", listOf("diamond_shape_with_a_dot_inside")),
    AndroidxEmoji2("\ud83d\udd18", listOf("radio_button")),
    AndroidxEmoji2("\ud83d\udd33", listOf("white_square_button")),
    AndroidxEmoji2("\ud83d\udd32", listOf("black_square_button")),
  )
}
