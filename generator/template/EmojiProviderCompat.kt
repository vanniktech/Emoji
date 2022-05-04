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

package com.vanniktech.emoji.<%= package %>

import android.content.Context
import android.text.Spannable
import androidx.emoji.text.EmojiCompat
import com.vanniktech.emoji.EmojiProvider
import com.vanniktech.emoji.EmojiReplacer
import com.vanniktech.emoji.emoji.EmojiCategory
<%= imports %>

class <%= name %>Provider(
  @Suppress("unused") private val emojiCompat: EmojiCompat,
) : EmojiProvider, EmojiReplacer {
  override val categories: Array<EmojiCategory>
    get() = arrayOf(
      <%= categories %>
    )

  override fun replaceWithImages(
    context: Context,
    text: Spannable,
    emojiSize: Float,
    fallback: EmojiReplacer?,
  ) {
    val emojiCompat = EmojiCompat.get()
    if (emojiCompat.loadState != EmojiCompat.LOAD_STATE_SUCCEEDED || emojiCompat.process(text, 0, text.length) !== text) {
      fallback?.replaceWithImages(context, text, emojiSize, null)
    }
  }
}
