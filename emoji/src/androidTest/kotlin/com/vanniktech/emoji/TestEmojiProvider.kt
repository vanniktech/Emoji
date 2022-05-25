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

import android.content.Context

internal class TestEmojiProvider(
  vararg val emojis: Emoji,
) : EmojiProvider, EmojiDrawableProvider {
  override val categories: Array<EmojiCategory>
    get() = arrayOf(
      object : EmojiCategory, EmojiAndroidCategory {
        override val emojis: List<Emoji>
          get() {
            return this@TestEmojiProvider.emojis.toList()
          }

        override val icon: Int
          get() = R.drawable.emoji_recent
        override val categoryName: Int
          get() = R.string.emoji_category_recent
      }
    )

  override fun getDrawable(emoji: Emoji, context: Context) = error("Not available from tests")
  override fun destroy() = Unit
}

object EmptyCategories : EmojiProvider {
  override val categories: Array<EmojiCategory>
    get() = emptyArray()
}

object EmptyEmojiProvider : EmojiProvider {
  override val categories: Array<EmojiCategory>
    get() = arrayOf(
      object : EmojiCategory, EmojiAndroidCategory {
        override val emojis: List<Emoji>
          get() = emptyList()
        override val icon: Int
          get() = 0
        override val categoryName: Int
          get() = 0
      }
    )
}
