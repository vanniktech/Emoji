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

package com.vanniktech.emoji.search

import com.vanniktech.emoji.EmojiManager

class SearchEmojiManager : SearchEmoji {
  override fun search(query: String): List<SearchEmojiResult> {
    val categories = EmojiManager.categories()

    return when {
      query.length > 1 -> categories.flatMap { it.emojis.toList() }
        .mapNotNull { emoji ->
          emoji.shortcodes.mapNotNull { shortcode ->
            val index = shortcode.indexOf(query, ignoreCase = true)

            if (index >= 0) {
              SearchEmojiResult(
                emoji = emoji,
                shortcode = shortcode,
                range = index until (index + query.length),
              )
            } else {
              null
            }
          }.firstOrNull()
        }
        .sortedWith(compareBy({ it.range.first }, { it.shortcode }))
      else -> emptyList()
    }
  }
}
