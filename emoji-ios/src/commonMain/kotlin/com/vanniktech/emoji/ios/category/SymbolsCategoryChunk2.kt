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

package com.vanniktech.emoji.ios.category

import com.vanniktech.emoji.ios.IosEmoji

internal object SymbolsCategoryChunk2 {
  internal val EMOJIS: List<IosEmoji> = listOf(
    IosEmoji("\ud83d\udfe6", listOf("large_blue_square"), 39, 17),
    IosEmoji("\ud83d\udfea", listOf("large_purple_square"), 39, 21),
    IosEmoji("\ud83d\udfeb", listOf("large_brown_square"), 39, 22),
    IosEmoji("\u2b1b", listOf("black_large_square"), 60, 58),
    IosEmoji("\u2b1c", listOf("white_large_square"), 60, 59),
    IosEmoji(
      unicode = "\u25fc",
      shortcodes = listOf("black_medium_square"),
      x = 58,
      y = 13,
      variants = listOf(
        IosEmoji("\u25fc\ufe0f", emptyList(), 58, 13),
      ),
    ),
    IosEmoji(
      unicode = "\u25fb",
      shortcodes = listOf("white_medium_square"),
      x = 58,
      y = 12,
      variants = listOf(
        IosEmoji("\u25fb\ufe0f", emptyList(), 58, 12),
      ),
    ),
    IosEmoji("\u25fe", listOf("black_medium_small_square"), 58, 15),
    IosEmoji("\u25fd", listOf("white_medium_small_square"), 58, 14),
    IosEmoji(
      unicode = "\u25aa",
      shortcodes = listOf("black_small_square"),
      x = 58,
      y = 8,
      variants = listOf(
        IosEmoji("\u25aa\ufe0f", emptyList(), 58, 8),
      ),
    ),
    IosEmoji(
      unicode = "\u25ab",
      shortcodes = listOf("white_small_square"),
      x = 58,
      y = 9,
      variants = listOf(
        IosEmoji("\u25ab\ufe0f", emptyList(), 58, 9),
      ),
    ),
    IosEmoji("\ud83d\udd36", listOf("large_orange_diamond"), 30, 47),
    IosEmoji("\ud83d\udd37", listOf("large_blue_diamond"), 30, 48),
    IosEmoji("\ud83d\udd38", listOf("small_orange_diamond"), 30, 49),
    IosEmoji("\ud83d\udd39", listOf("small_blue_diamond"), 30, 50),
    IosEmoji("\ud83d\udd3a", listOf("small_red_triangle"), 30, 51),
    IosEmoji("\ud83d\udd3b", listOf("small_red_triangle_down"), 30, 52),
    IosEmoji("\ud83d\udca0", listOf("diamond_shape_with_a_dot_inside"), 28, 17),
    IosEmoji("\ud83d\udd18", listOf("radio_button"), 30, 17),
    IosEmoji("\ud83d\udd33", listOf("white_square_button"), 30, 44),
    IosEmoji("\ud83d\udd32", listOf("black_square_button"), 30, 43),
  )
}
