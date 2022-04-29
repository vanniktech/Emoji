package com.vanniktech.emoji

class SearchEmojiManager : SearchEmoji {
  override fun search(query: String): List<SearchEmojiResult> {
    val categories = EmojiManager.getInstance().categories

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
      else -> emptyList()
    }
  }
}
