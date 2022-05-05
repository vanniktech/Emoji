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

package com.vanniktech.emoji.internal

import android.content.Context
import android.util.AttributeSet
import com.vanniktech.emoji.EmojiTheming
import com.vanniktech.emoji.listeners.OnEmojiClickListener
import com.vanniktech.emoji.recent.RecentEmoji

internal class RecentEmojiGridView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : EmojiGridView(context, attrs) {
  private lateinit var emojiArrayAdapter: EmojiArrayAdapter
  private lateinit var recentEmojis: RecentEmoji

  fun init(
    onEmojiClickListener: OnEmojiClickListener?,
    onEmojiLongClickListener: OnEmojiLongClickListener?,
    theming: EmojiTheming,
    recentEmoji: RecentEmoji,
  ): RecentEmojiGridView {
    recentEmojis = recentEmoji
    emojiArrayAdapter = EmojiArrayAdapter(
      context,
      recentEmoji.getRecentEmojis().toTypedArray(),
      null,
      onEmojiClickListener,
      onEmojiLongClickListener,
      theming,
    )
    adapter = emojiArrayAdapter
    return this
  }

  fun invalidateEmojis() {
    emojiArrayAdapter.updateEmojis(recentEmojis.getRecentEmojis())
  }
}
