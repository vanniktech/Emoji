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

internal object FoodAndDrinkCategoryChunk1 {
  internal val EMOJIS: List<GoogleEmoji> = listOf(
    GoogleEmoji("\ud83c\udf6c", listOf("candy"), 6, 41),
    GoogleEmoji("\ud83c\udf6d", listOf("lollipop"), 6, 42),
    GoogleEmoji("\ud83c\udf6e", listOf("custard"), 6, 43),
    GoogleEmoji("\ud83c\udf6f", listOf("honey_pot"), 6, 44),
    GoogleEmoji("\ud83c\udf7c", listOf("baby_bottle"), 6, 57),
    GoogleEmoji("\ud83e\udd5b", listOf("glass_of_milk"), 44, 12),
    GoogleEmoji("\u2615", listOf("coffee"), 58, 24),
    GoogleEmoji("\ud83e\uded6", listOf("teapot"), 56, 29),
    GoogleEmoji("\ud83c\udf75", listOf("tea"), 6, 50),
    GoogleEmoji("\ud83c\udf76", listOf("sake"), 6, 51),
    GoogleEmoji("\ud83c\udf7e", listOf("champagne"), 6, 59),
    GoogleEmoji("\ud83c\udf77", listOf("wine_glass"), 6, 52),
    GoogleEmoji("\ud83c\udf78", listOf("cocktail"), 6, 53),
    GoogleEmoji("\ud83c\udf79", listOf("tropical_drink"), 6, 54),
    GoogleEmoji("\ud83c\udf7a", listOf("beer"), 6, 55),
    GoogleEmoji("\ud83c\udf7b", listOf("beers"), 6, 56),
    GoogleEmoji("\ud83e\udd42", listOf("clinking_glasses"), 43, 50),
    GoogleEmoji("\ud83e\udd43", listOf("tumbler_glass"), 43, 51),
    GoogleEmoji("\ud83e\uded7", listOf("pouring_liquid"), 56, 30),
    GoogleEmoji("\ud83e\udd64", listOf("cup_with_straw"), 44, 21),
    GoogleEmoji("\ud83e\uddcb", listOf("bubble_tea"), 46, 50),
    GoogleEmoji("\ud83e\uddc3", listOf("beverage_box"), 46, 42),
    GoogleEmoji("\ud83e\uddc9", listOf("mate_drink"), 46, 48),
    GoogleEmoji("\ud83e\uddca", listOf("ice_cube"), 46, 49),
    GoogleEmoji("\ud83e\udd62", listOf("chopsticks"), 44, 19),
    GoogleEmoji(
      unicode = "\ud83c\udf7d",
      shortcodes = listOf("knife_fork_plate"),
      x = 6,
      y = 58,
      variants = listOf(
        GoogleEmoji("\ud83c\udf7d\ufe0f", emptyList(), 6, 58),
      ),
    ),
    GoogleEmoji("\ud83c\udf74", listOf("fork_and_knife"), 6, 49),
    GoogleEmoji("\ud83e\udd44", listOf("spoon"), 43, 52),
    GoogleEmoji("\ud83d\udd2a", listOf("hocho", "knife"), 30, 35),
    GoogleEmoji("\ud83e\uded9", listOf("jar"), 56, 32),
    GoogleEmoji("\ud83c\udffa", listOf("amphora"), 10, 51),
  )
}
