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

internal object ObjectsCategoryChunk2 {
  internal val EMOJIS: List<GoogleCompatEmoji> = listOf(
    GoogleCompatEmoji(String(intArrayOf(0x1FA92), 0, 1), listOf("razor"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9F4), 0, 1), listOf("lotion_bottle"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9F7), 0, 1), listOf("safety_pin"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9F9), 0, 1), listOf("broom"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9FA), 0, 1), listOf("basket"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9FB), 0, 1), listOf("roll_of_paper"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1FAA3), 0, 1), listOf("bucket"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9FC), 0, 1), listOf("soap"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1FAE7), 0, 1), listOf("bubbles"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1FAA5), 0, 1), listOf("toothbrush"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9FD), 0, 1), listOf("sponge"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F9EF), 0, 1), listOf("fire_extinguisher"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F6D2), 0, 1), listOf("shopping_trolley"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F6AC), 0, 1), listOf("smoking"), false),
    GoogleCompatEmoji(
      String(intArrayOf(0x26B0), 0, 1), listOf("coffin"), false,
      variants = listOf(
        GoogleCompatEmoji(String(intArrayOf(0x26B0, 0xFE0F), 0, 2), emptyList<String>(), false),
      ),
    ),
    GoogleCompatEmoji(String(intArrayOf(0x1FAA6), 0, 1), listOf("headstone"), false),
    GoogleCompatEmoji(
      String(intArrayOf(0x26B1), 0, 1), listOf("funeral_urn"), false,
      variants = listOf(
        GoogleCompatEmoji(String(intArrayOf(0x26B1, 0xFE0F), 0, 2), emptyList<String>(), false),
      ),
    ),
    GoogleCompatEmoji(String(intArrayOf(0x1F9FF), 0, 1), listOf("nazar_amulet"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1FAAC), 0, 1), listOf("hamsa"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1F5FF), 0, 1), listOf("moyai"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1FAA7), 0, 1), listOf("placard"), false),
    GoogleCompatEmoji(String(intArrayOf(0x1FAAA), 0, 1), listOf("identification_card"), false),
  )
}
