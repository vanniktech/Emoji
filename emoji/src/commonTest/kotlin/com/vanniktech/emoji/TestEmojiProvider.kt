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

package com.vanniktech.emoji

internal object TestEmojiProvider : EmojiProvider {
  override val categories: Array<EmojiCategory>
    get() = arrayOf(
      object : EmojiCategory {
        override val emojis = listOf(
          emojiBalloon,
          emojiYoYo,
          emojiReminderRibbon,
          emojiBaldPerson,
          emojiSuperhero,
        )

        override val categoryNames: Map<String, String>
          get() = mapOf(
            "en" to "Test",
          )
      },
    )

  override fun release() = Unit
}

object EmptyCategories : EmojiProvider {
  override val categories: Array<EmojiCategory>
    get() = emptyArray()

  override fun release() = Unit
}

object EmptyEmojiProvider : EmojiProvider {
  override val categories: Array<EmojiCategory>
    get() = arrayOf(
      object : EmojiCategory {
        override val emojis: List<Emoji>
          get() = emptyList()
        override val categoryNames: Map<String, String>
          get() = mapOf(
            "en" to "Test",
          )
      },
    )

  override fun release() = Unit
}
