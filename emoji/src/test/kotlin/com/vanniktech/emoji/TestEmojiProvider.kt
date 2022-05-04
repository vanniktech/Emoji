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

import com.vanniktech.emoji.emoji.Emoji
import com.vanniktech.emoji.emoji.EmojiCategory

internal class TestEmojiProvider
private constructor(
  val emojis: Array<Emoji>
) : EmojiProvider {
  override val categories: Array<EmojiCategory>
    get() = arrayOf(
      object : EmojiCategory {
        override val emojis: Array<Emoji>
          get() {
            return this@TestEmojiProvider.emojis
          }

        override val icon: Int
          get() = R.drawable.emoji_recent
        override val categoryName: Int
          get() = R.string.emoji_category_recent
      }
    )

  companion object {
    @JvmStatic
    fun from(vararg emojis: Emoji): EmojiProvider {
      return TestEmojiProvider(emojis.toList().toTypedArray())
    }

    @JvmStatic
    fun emptyCategories(): EmojiProvider {
      return object : EmojiProvider {
        override val categories: Array<EmojiCategory>
          get() = emptyArray()
      }
    }

    @JvmStatic
    fun emptyEmojis(): EmojiProvider {
      return object : EmojiProvider {
        override val categories: Array<EmojiCategory>
          get() = arrayOf(
            object : EmojiCategory {
              override val emojis: Array<Emoji>
                get() = emptyArray()
              override val icon: Int
                get() = 0
              override val categoryName: Int
                get() = 0
            }
          )
      }
    }
  }
}
