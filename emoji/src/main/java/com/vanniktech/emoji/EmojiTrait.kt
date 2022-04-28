package com.vanniktech.emoji

import android.widget.EditText

interface EmojiTraitable {
  fun install(editText: EditText): EmojiTrait
}

interface EmojiTrait {
  /** Call this method to reverse the [EmojiTraitable] that you installed. */
  fun uninstall()
}

internal object EmptyEmojiTrait : EmojiTrait {
  override fun uninstall() = Unit
}
