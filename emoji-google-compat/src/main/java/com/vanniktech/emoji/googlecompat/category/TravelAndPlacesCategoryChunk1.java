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

package com.vanniktech.emoji.googlecompat.category;

import com.vanniktech.emoji.googlecompat.GoogleCompatEmoji;

final class TravelAndPlacesCategoryChunk1 {
  @SuppressWarnings("PMD.ExcessiveMethodLength") static GoogleCompatEmoji[] get() {
    return new GoogleCompatEmoji[] {
      new GoogleCompatEmoji(new int[] { 0x1F329, 0xFE0F }, new String[]{"lightning", "lightning_cloud"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F32A, 0xFE0F }, new String[]{"tornado", "tornado_cloud"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F32B, 0xFE0F }, new String[]{"fog"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F32C, 0xFE0F }, new String[]{"wind_blowing_face"}, false),
      new GoogleCompatEmoji(0x1F300, new String[]{"cyclone"}, false),
      new GoogleCompatEmoji(0x1F308, new String[]{"rainbow"}, false),
      new GoogleCompatEmoji(0x1F302, new String[]{"closed_umbrella"}, false),
      new GoogleCompatEmoji(new int[] { 0x2602, 0xFE0F }, new String[]{"umbrella"}, false),
      new GoogleCompatEmoji(0x2614, new String[]{"umbrella_with_rain_drops"}, false),
      new GoogleCompatEmoji(new int[] { 0x26F1, 0xFE0F }, new String[]{"umbrella_on_ground"}, false),
      new GoogleCompatEmoji(0x26A1, new String[]{"zap"}, false),
      new GoogleCompatEmoji(new int[] { 0x2744, 0xFE0F }, new String[]{"snowflake"}, false),
      new GoogleCompatEmoji(new int[] { 0x2603, 0xFE0F }, new String[]{"snowman"}, false),
      new GoogleCompatEmoji(0x26C4, new String[]{"snowman_without_snow"}, false),
      new GoogleCompatEmoji(new int[] { 0x2604, 0xFE0F }, new String[]{"comet"}, false),
      new GoogleCompatEmoji(0x1F525, new String[]{"fire"}, false),
      new GoogleCompatEmoji(0x1F4A7, new String[]{"droplet"}, false),
      new GoogleCompatEmoji(0x1F30A, new String[]{"ocean"}, false)
    };
  }

  private TravelAndPlacesCategoryChunk1() {
    // No instances.
  }
}
