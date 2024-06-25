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

internal object ObjectsCategoryChunk2 {
  internal val EMOJIS: List<IosEmoji> = listOf(
    IosEmoji("\ud83d\udd27", listOf("wrench"), 30, 31),
    IosEmoji("\ud83e\ude9b", listOf("screwdriver"), 55, 21),
    IosEmoji("\ud83d\udd29", listOf("nut_and_bolt"), 30, 33),
    IosEmoji(
      unicode = "\u2699",
      shortcodes = listOf("gear"),
      x = 59,
      y = 1,
      variants = listOf(
        IosEmoji("\u2699\ufe0f", emptyList(), 59, 1),
      ),
    ),
    IosEmoji(
      unicode = "\ud83d\udddc",
      shortcodes = listOf("compression"),
      x = 32,
      y = 32,
      variants = listOf(
        IosEmoji("\ud83d\udddc\ufe0f", emptyList(), 32, 32),
      ),
    ),
    IosEmoji(
      unicode = "\u2696",
      shortcodes = listOf("scales"),
      x = 58,
      y = 61,
      variants = listOf(
        IosEmoji("\u2696\ufe0f", emptyList(), 58, 61),
      ),
    ),
    IosEmoji("\ud83e\uddaf", listOf("probing_cane"), 45, 38),
    IosEmoji("\ud83d\udd17", listOf("link"), 30, 15),
    IosEmoji(
      unicode = "\u26d3\u200d\ud83d\udca5",
      shortcodes = listOf("broken_chain"),
      x = 59,
      y = 19,
      variants = listOf(
        IosEmoji("\u26d3\ufe0f\u200d\ud83d\udca5", emptyList(), 59, 19),
      ),
    ),
    IosEmoji(
      unicode = "\u26d3",
      shortcodes = listOf("chains"),
      x = 59,
      y = 20,
      variants = listOf(
        IosEmoji("\u26d3\ufe0f", emptyList(), 59, 20),
      ),
    ),
    IosEmoji("\ud83e\ude9d", listOf("hook"), 55, 23),
    IosEmoji("\ud83e\uddf0", listOf("toolbox"), 54, 34),
    IosEmoji("\ud83e\uddf2", listOf("magnet"), 54, 36),
    IosEmoji("\ud83e\ude9c", listOf("ladder"), 55, 22),
    IosEmoji(
      unicode = "\u2697",
      shortcodes = listOf("alembic"),
      x = 59,
      y = 0,
      variants = listOf(
        IosEmoji("\u2697\ufe0f", emptyList(), 59, 0),
      ),
    ),
    IosEmoji("\ud83e\uddea", listOf("test_tube"), 54, 28),
    IosEmoji("\ud83e\uddeb", listOf("petri_dish"), 54, 29),
    IosEmoji("\ud83e\uddec", listOf("dna"), 54, 30),
    IosEmoji("\ud83d\udd2c", listOf("microscope"), 30, 36),
    IosEmoji("\ud83d\udd2d", listOf("telescope"), 30, 37),
    IosEmoji("\ud83d\udce1", listOf("satellite_antenna"), 29, 24),
    IosEmoji("\ud83d\udc89", listOf("syringe"), 27, 5),
    IosEmoji("\ud83e\ude78", listOf("drop_of_blood"), 54, 58),
    IosEmoji("\ud83d\udc8a", listOf("pill"), 27, 6),
    IosEmoji("\ud83e\ude79", listOf("adhesive_bandage"), 54, 59),
    IosEmoji("\ud83e\ude7c", listOf("crutch"), 55, 0),
    IosEmoji("\ud83e\ude7a", listOf("stethoscope"), 54, 60),
    IosEmoji("\ud83e\ude7b", listOf("x-ray"), 54, 61),
    IosEmoji("\ud83d\udeaa", listOf("door"), 36, 54),
    IosEmoji("\ud83d\uded7", listOf("elevator"), 38, 47),
    IosEmoji("\ud83e\ude9e", listOf("mirror"), 55, 24),
    IosEmoji("\ud83e\ude9f", listOf("window"), 55, 25),
    IosEmoji(
      unicode = "\ud83d\udecf",
      shortcodes = listOf("bed"),
      x = 38,
      y = 41,
      variants = listOf(
        IosEmoji("\ud83d\udecf\ufe0f", emptyList(), 38, 41),
      ),
    ),
    IosEmoji(
      unicode = "\ud83d\udecb",
      shortcodes = listOf("couch_and_lamp"),
      x = 38,
      y = 32,
      variants = listOf(
        IosEmoji("\ud83d\udecb\ufe0f", emptyList(), 38, 32),
      ),
    ),
    IosEmoji("\ud83e\ude91", listOf("chair"), 55, 11),
    IosEmoji("\ud83d\udebd", listOf("toilet"), 38, 18),
    IosEmoji("\ud83e\udea0", listOf("plunger"), 55, 26),
    IosEmoji("\ud83d\udebf", listOf("shower"), 38, 20),
    IosEmoji("\ud83d\udec1", listOf("bathtub"), 38, 27),
    IosEmoji("\ud83e\udea4", listOf("mouse_trap"), 55, 30),
    IosEmoji("\ud83e\ude92", listOf("razor"), 55, 12),
    IosEmoji("\ud83e\uddf4", listOf("lotion_bottle"), 54, 38),
    IosEmoji("\ud83e\uddf7", listOf("safety_pin"), 54, 41),
    IosEmoji("\ud83e\uddf9", listOf("broom"), 54, 43),
    IosEmoji("\ud83e\uddfa", listOf("basket"), 54, 44),
    IosEmoji("\ud83e\uddfb", listOf("roll_of_paper"), 54, 45),
    IosEmoji("\ud83e\udea3", listOf("bucket"), 55, 29),
    IosEmoji("\ud83e\uddfc", listOf("soap"), 54, 46),
    IosEmoji("\ud83e\udee7", listOf("bubbles"), 56, 37),
    IosEmoji("\ud83e\udea5", listOf("toothbrush"), 55, 31),
    IosEmoji("\ud83e\uddfd", listOf("sponge"), 54, 47),
    IosEmoji("\ud83e\uddef", listOf("fire_extinguisher"), 54, 33),
    IosEmoji("\ud83d\uded2", listOf("shopping_trolley"), 38, 44),
    IosEmoji("\ud83d\udeac", listOf("smoking"), 36, 56),
    IosEmoji(
      unicode = "\u26b0",
      shortcodes = listOf("coffin"),
      x = 59,
      y = 9,
      variants = listOf(
        IosEmoji("\u26b0\ufe0f", emptyList(), 59, 9),
      ),
    ),
    IosEmoji("\ud83e\udea6", listOf("headstone"), 55, 32),
    IosEmoji(
      unicode = "\u26b1",
      shortcodes = listOf("funeral_urn"),
      x = 59,
      y = 10,
      variants = listOf(
        IosEmoji("\u26b1\ufe0f", emptyList(), 59, 10),
      ),
    ),
    IosEmoji("\ud83e\uddff", listOf("nazar_amulet"), 54, 49),
    IosEmoji("\ud83e\udeac", listOf("hamsa"), 55, 38),
    IosEmoji("\ud83d\uddff", listOf("moyai"), 32, 45),
    IosEmoji("\ud83e\udea7", listOf("placard"), 55, 33),
    IosEmoji("\ud83e\udeaa", listOf("identification_card"), 55, 36),
  )
}
