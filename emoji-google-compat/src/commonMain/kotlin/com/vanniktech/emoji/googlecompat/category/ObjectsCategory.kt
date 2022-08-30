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

import com.vanniktech.emoji.EmojiCategory
import com.vanniktech.emoji.googlecompat.GoogleCompatEmoji

internal class ObjectsCategory : EmojiCategory {
  override val categoryNames: Map<String, String>
    get() = mapOf(
      "en" to "Objects",
      "de" to "Objekte",
    )

  override val emojis = ALL_EMOJIS

  private companion object {
    val ALL_EMOJIS: List<GoogleCompatEmoji> = ObjectsCategoryChunk0.EMOJIS + ObjectsCategoryChunk1.EMOJIS + ObjectsCategoryChunk2.EMOJIS
  }
}
