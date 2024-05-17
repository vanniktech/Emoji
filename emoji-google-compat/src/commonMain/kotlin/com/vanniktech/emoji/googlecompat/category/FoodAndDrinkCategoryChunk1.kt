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

internal object FoodAndDrinkCategoryChunk1 {
  internal val EMOJIS: List<GoogleCompatEmoji> = listOf(
    GoogleCompatEmoji(String(intArrayOf(0x1F942), 0, 1), listOf("clinking_glasses"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F943), 0, 1), listOf("tumbler_glass"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1FAD7), 0, 1), listOf("pouring_liquid"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F964), 0, 1), listOf("cup_with_straw"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9CB), 0, 1), listOf("bubble_tea"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9C3), 0, 1), listOf("beverage_box"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9C9), 0, 1), listOf("mate_drink"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9CA), 0, 1), listOf("ice_cube"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F962), 0, 1), listOf("chopsticks"), false),
    GoogleCompatEmoji(
      String(intArrayOf(0x1F37D), 0, 1), listOf("knife_fork_plate"), false,
      variants = listOf(
        GoogleCompatEmoji(String(intArrayOf(0x1F37D, 0xFE0F), 0, 2), emptyList<String>(), false),
      ),
    ),
    GoogleCompatEmoji(String(intArrayOf(0x1F374), 0, 1), listOf("fork_and_knife"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F944), 0, 1), listOf("spoon"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F52A), 0, 1), listOf("hocho", "knife"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1FAD9), 0, 1), listOf("jar"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F3FA), 0, 1), listOf("amphora"), false),
  )
}
