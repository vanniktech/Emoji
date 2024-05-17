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
import android.graphics.drawable.Drawable
import android.text.Spannable
import androidx.emoji2.text.EmojiCompat
import com.vanniktech.emoji.Emoji
import com.vanniktech.emoji.EmojiAndroidProvider
import com.vanniktech.emoji.EmojiCategory
import com.vanniktech.emoji.EmojiProvider
import com.vanniktech.emoji.EmojiReplacer
<%= imports %>

class <%= name %>Provider(
  @Suppress("unused") private val emojiCompat: EmojiCompat,
) : EmojiProvider, EmojiAndroidProvider, EmojiReplacer {
  override val categories: Array<EmojiCategory>
    get() = arrayOf(<% categories.forEach(function(category) { %>
      <%= category.name %>(),<% }); %>
    )

  override fun getIcon(emojiCategory: EmojiCategory): Int = when (emojiCategory) {<% categories.forEach(function(category) { %>
    is <%= category.name %> -> R.drawable.emoji_<%= package %>_category_<%= category.icon %><% }); %>
    else -> error("Unknown $emojiCategory")
  }

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

  override fun getDrawable(emoji: Emoji, context: Context): Drawable = AndroidxEmoji2Drawable(emoji.unicode)
  override fun release() = Unit
}
