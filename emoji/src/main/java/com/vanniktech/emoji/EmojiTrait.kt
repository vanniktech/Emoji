package com.vanniktech.emoji

interface EmojiTrait {
  /** Call this method to reverse the installed trait. */
  fun uninstall()
}

internal object EmptyEmojiTrait : EmojiTrait {
  override fun uninstall() = Unit
}
