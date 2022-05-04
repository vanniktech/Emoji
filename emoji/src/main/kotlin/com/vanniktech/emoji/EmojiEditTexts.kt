@file:JvmName("EmojiEditTexts")

package com.vanniktech.emoji

import android.view.KeyEvent
import android.widget.EditText
import com.vanniktech.emoji.emoji.Emoji
import kotlin.math.max
import kotlin.math.min

/** Dispatches a KeyEvent which mimics the press of the Backspace key */
fun EditText.backspace() {
  val event = KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL)
  dispatchKeyEvent(event)
}

/** Inserts the given [emoji] into [this] while preserving the current selection. */
fun EditText.input(emoji: Emoji) {
  val start = selectionStart
  val end = selectionEnd
  if (start < 0) {
    append(emoji.unicode)
  } else {
    text.replace(min(start, end), max(start, end), emoji.unicode, 0, emoji.unicode.length)
  }
}
