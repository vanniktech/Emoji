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

@file:JvmName("EmojiEditTexts")

package com.vanniktech.emoji

import android.view.KeyEvent
import android.widget.EditText
import com.vanniktech.emoji.traits.DisableKeyboardInputTrait
import com.vanniktech.emoji.traits.EmojiTrait
import com.vanniktech.emoji.traits.ForceSingleEmojiTrait
import com.vanniktech.emoji.traits.SearchInPlaceTrait
import kotlin.math.max
import kotlin.math.min

/** Dispatches a KeyEvent which mimics the press of the Backspace key */
fun EditText.backspace() {
  val event = KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL)
  dispatchKeyEvent(event)
}

/** Inserts the given [emoji] into [this] while preserving the current selection. */
fun EditText.input(emoji: Emoji, addWhitespace: Boolean = false) {
  val start = selectionStart
  val end = selectionEnd
  val new = emoji.unicode + when {
    addWhitespace -> " "
    else -> ""
  }

  if (start < 0) {
    append(new)
  } else {
    text.replace(min(start, end), max(start, end), new, 0, new.length)
  }
}

/** Disables the keyboard. Only [emojiPopup] will be shown. */
fun EditText.installDisableKeyboardInput(emojiPopup: EmojiPopup): EmojiTrait = DisableKeyboardInputTrait(emojiPopup).install(this)

/** Forces this EditText to contain only one Emoji. */
fun EditText.installForceSingleEmoji(): EmojiTrait = ForceSingleEmojiTrait().install(this)

/** When typing :query it will display a Popup similar to how Telegram and Slack does it to search for an Emoji. */
fun EditText.installSearchInPlace(emojiPopup: EmojiPopup): EmojiTrait = SearchInPlaceTrait(emojiPopup).install(this)
