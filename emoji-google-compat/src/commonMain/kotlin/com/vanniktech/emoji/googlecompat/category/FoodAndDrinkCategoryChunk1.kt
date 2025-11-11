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
    GoogleCompatEmoji("\ud83c\udf6c", listOf("candy")),
    GoogleCompatEmoji("\ud83c\udf6d", listOf("lollipop")),
    GoogleCompatEmoji("\ud83c\udf6e", listOf("custard")),
    GoogleCompatEmoji("\ud83c\udf6f", listOf("honey_pot")),
    GoogleCompatEmoji("\ud83c\udf7c", listOf("baby_bottle")),
    GoogleCompatEmoji("\ud83e\udd5b", listOf("glass_of_milk")),
    GoogleCompatEmoji("\u2615", listOf("coffee")),
    GoogleCompatEmoji("\ud83e\uded6", listOf("teapot")),
    GoogleCompatEmoji("\ud83c\udf75", listOf("tea")),
    GoogleCompatEmoji("\ud83c\udf76", listOf("sake")),
    GoogleCompatEmoji("\ud83c\udf7e", listOf("champagne")),
    GoogleCompatEmoji("\ud83c\udf77", listOf("wine_glass")),
    GoogleCompatEmoji("\ud83c\udf78", listOf("cocktail")),
    GoogleCompatEmoji("\ud83c\udf79", listOf("tropical_drink")),
    GoogleCompatEmoji("\ud83c\udf7a", listOf("beer")),
    GoogleCompatEmoji("\ud83c\udf7b", listOf("beers")),
    GoogleCompatEmoji("\ud83e\udd42", listOf("clinking_glasses")),
    GoogleCompatEmoji("\ud83e\udd43", listOf("tumbler_glass")),
    GoogleCompatEmoji("\ud83e\uded7", listOf("pouring_liquid")),
    GoogleCompatEmoji("\ud83e\udd64", listOf("cup_with_straw")),
    GoogleCompatEmoji("\ud83e\uddcb", listOf("bubble_tea")),
    GoogleCompatEmoji("\ud83e\uddc3", listOf("beverage_box")),
    GoogleCompatEmoji("\ud83e\uddc9", listOf("mate_drink")),
    GoogleCompatEmoji("\ud83e\uddca", listOf("ice_cube")),
    GoogleCompatEmoji("\ud83e\udd62", listOf("chopsticks")),
    GoogleCompatEmoji(
      unicode = "\ud83c\udf7d",
      shortcodes = listOf("knife_fork_plate"),
      variants = listOf(
        GoogleCompatEmoji("\ud83c\udf7d\ufe0f", emptyList()),
      ),
    ),
    GoogleCompatEmoji("\ud83c\udf74", listOf("fork_and_knife")),
    GoogleCompatEmoji("\ud83e\udd44", listOf("spoon")),
    GoogleCompatEmoji("\ud83d\udd2a", listOf("hocho", "knife")),
    GoogleCompatEmoji("\ud83e\uded9", listOf("jar")),
    GoogleCompatEmoji("\ud83c\udffa", listOf("amphora")),
  )
}
