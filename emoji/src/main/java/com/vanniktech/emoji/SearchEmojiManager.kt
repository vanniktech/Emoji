package com.vanniktech.emoji

import com.vanniktech.emoji.emoji.Emoji

class SearchEmojiManager : SearchEmoji {
  override fun search(query: String): List<Emoji> {
    val categories = EmojiManager.getInstance().categories

    return when {
      query.length > 1 -> categories.flatMap { it.emojis.toList() }
        .filter { emoji -> emoji.shortcodes.orEmpty().any { it.contains(query, ignoreCase = true) } }
      else -> emptyList()
    }
  }
}
