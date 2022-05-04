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

package com.vanniktech.emoji.google.category

import com.vanniktech.emoji.google.GoogleEmoji

internal object TravelAndPlacesCategoryChunk1 {
  fun get(): Array<GoogleEmoji> {
    return arrayOf(
      GoogleEmoji(intArrayOf(0x1F329, 0xFE0F), arrayOf("lightning", "lightning_cloud"), 5, 38, false),
      GoogleEmoji(intArrayOf(0x1F32A, 0xFE0F), arrayOf("tornado", "tornado_cloud"), 5, 39, false),
      GoogleEmoji(intArrayOf(0x1F32B, 0xFE0F), arrayOf("fog"), 5, 40, false),
      GoogleEmoji(intArrayOf(0x1F32C, 0xFE0F), arrayOf("wind_blowing_face"), 5, 41, false),
      GoogleEmoji(0x1F300, arrayOf("cyclone"), 4, 60, false),
      GoogleEmoji(0x1F308, arrayOf("rainbow"), 5, 7, false),
      GoogleEmoji(0x1F302, arrayOf("closed_umbrella"), 5, 1, false),
      GoogleEmoji(intArrayOf(0x2602, 0xFE0F), arrayOf("umbrella"), 56, 42, false),
      GoogleEmoji(0x2614, arrayOf("umbrella_with_rain_drops"), 56, 47, false),
      GoogleEmoji(intArrayOf(0x26F1, 0xFE0F), arrayOf("umbrella_on_ground"), 57, 57, false),
      GoogleEmoji(0x26A1, arrayOf("zap"), 57, 38, false),
      GoogleEmoji(intArrayOf(0x2744, 0xFE0F), arrayOf("snowflake"), 58, 60, false),
      GoogleEmoji(intArrayOf(0x2603, 0xFE0F), arrayOf("snowman"), 56, 43, false),
      GoogleEmoji(0x26C4, arrayOf("snowman_without_snow"), 57, 46, false),
      GoogleEmoji(intArrayOf(0x2604, 0xFE0F), arrayOf("comet"), 56, 44, false),
      GoogleEmoji(0x1F525, arrayOf("fire"), 30, 1, false),
      GoogleEmoji(0x1F4A7, arrayOf("droplet"), 27, 54, false),
      GoogleEmoji(0x1F30A, arrayOf("ocean"), 5, 9, false)
    )
  }
}
