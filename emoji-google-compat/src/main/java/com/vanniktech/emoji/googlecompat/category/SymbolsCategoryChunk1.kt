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

package com.vanniktech.emoji.googlecompat.category

import com.vanniktech.emoji.googlecompat.GoogleCompatEmoji

internal object SymbolsCategoryChunk1 {
  fun get(): Array<GoogleCompatEmoji> {
    return arrayOf(
      GoogleCompatEmoji(0x1F7E6, arrayOf("large_blue_square"), false),
      GoogleCompatEmoji(0x1F7EA, arrayOf("large_purple_square"), false),
      GoogleCompatEmoji(0x1F7EB, arrayOf("large_brown_square"), false),
      GoogleCompatEmoji(0x2B1B, arrayOf("black_large_square"), false),
      GoogleCompatEmoji(0x2B1C, arrayOf("white_large_square"), false),
      GoogleCompatEmoji(intArrayOf(0x25FC, 0xFE0F), arrayOf("black_medium_square"), false),
      GoogleCompatEmoji(intArrayOf(0x25FB, 0xFE0F), arrayOf("white_medium_square"), false),
      GoogleCompatEmoji(0x25FE, arrayOf("black_medium_small_square"), false),
      GoogleCompatEmoji(0x25FD, arrayOf("white_medium_small_square"), false),
      GoogleCompatEmoji(intArrayOf(0x25AA, 0xFE0F), arrayOf("black_small_square"), false),
      GoogleCompatEmoji(intArrayOf(0x25AB, 0xFE0F), arrayOf("white_small_square"), false),
      GoogleCompatEmoji(0x1F536, arrayOf("large_orange_diamond"), false),
      GoogleCompatEmoji(0x1F537, arrayOf("large_blue_diamond"), false),
      GoogleCompatEmoji(0x1F538, arrayOf("small_orange_diamond"), false),
      GoogleCompatEmoji(0x1F539, arrayOf("small_blue_diamond"), false),
      GoogleCompatEmoji(0x1F53A, arrayOf("small_red_triangle"), false),
      GoogleCompatEmoji(0x1F53B, arrayOf("small_red_triangle_down"), false),
      GoogleCompatEmoji(0x1F4A0, arrayOf("diamond_shape_with_a_dot_inside"), false),
      GoogleCompatEmoji(0x1F518, arrayOf("radio_button"), false),
      GoogleCompatEmoji(0x1F533, arrayOf("white_square_button"), false),
      GoogleCompatEmoji(0x1F532, arrayOf("black_square_button"), false)
    )
  }
}
