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

package com.vanniktech.emoji.google.category;

import com.vanniktech.emoji.google.GoogleEmoji;

final class TravelAndPlacesCategoryChunk1 {
  @SuppressWarnings("PMD.ExcessiveMethodLength") static GoogleEmoji[] get() {
    return new GoogleEmoji[] {
      new GoogleEmoji(new int[] { 0x1F329, 0xFE0F }, new String[]{"lightning", "lightning_cloud"}, 5, 38, false),
      new GoogleEmoji(new int[] { 0x1F32A, 0xFE0F }, new String[]{"tornado", "tornado_cloud"}, 5, 39, false),
      new GoogleEmoji(new int[] { 0x1F32B, 0xFE0F }, new String[]{"fog"}, 5, 40, false),
      new GoogleEmoji(new int[] { 0x1F32C, 0xFE0F }, new String[]{"wind_blowing_face"}, 5, 41, false),
      new GoogleEmoji(0x1F300, new String[]{"cyclone"}, 4, 60, false),
      new GoogleEmoji(0x1F308, new String[]{"rainbow"}, 5, 7, false),
      new GoogleEmoji(0x1F302, new String[]{"closed_umbrella"}, 5, 1, false),
      new GoogleEmoji(new int[] { 0x2602, 0xFE0F }, new String[]{"umbrella"}, 56, 42, false),
      new GoogleEmoji(0x2614, new String[]{"umbrella_with_rain_drops"}, 56, 47, false),
      new GoogleEmoji(new int[] { 0x26F1, 0xFE0F }, new String[]{"umbrella_on_ground"}, 57, 57, false),
      new GoogleEmoji(0x26A1, new String[]{"zap"}, 57, 38, false),
      new GoogleEmoji(new int[] { 0x2744, 0xFE0F }, new String[]{"snowflake"}, 58, 60, false),
      new GoogleEmoji(new int[] { 0x2603, 0xFE0F }, new String[]{"snowman"}, 56, 43, false),
      new GoogleEmoji(0x26C4, new String[]{"snowman_without_snow"}, 57, 46, false),
      new GoogleEmoji(new int[] { 0x2604, 0xFE0F }, new String[]{"comet"}, 56, 44, false),
      new GoogleEmoji(0x1F525, new String[]{"fire"}, 30, 1, false),
      new GoogleEmoji(0x1F4A7, new String[]{"droplet"}, 27, 54, false),
      new GoogleEmoji(0x1F30A, new String[]{"ocean"}, 5, 9, false)
    };
  }

  private TravelAndPlacesCategoryChunk1() {
    // No instances.
  }
}
