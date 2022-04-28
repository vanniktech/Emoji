package com.vanniktech.emoji.traits

interface EmojiTrait {
  /** Call this method to reverse the [EmojiTraitable] that you installed. */
  fun uninstall()
}
