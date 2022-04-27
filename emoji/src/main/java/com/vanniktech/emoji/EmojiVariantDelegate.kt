package com.vanniktech.emoji

import com.vanniktech.emoji.emoji.Emoji

internal interface EmojiVariantDelegate {
  fun onEmojiClick(emojiImageView: EmojiImageView, emoji: Emoji)
}
