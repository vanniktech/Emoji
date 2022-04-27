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
 *
 */
package com.vanniktech.emoji

interface EmojiForceable {
  /** Returns true when [.enableKeyboardInput] was called and otherwise false.  */
  val isKeyboardInputDisabled: Boolean

  /** Enables the keyboard input. If it has been disabled before using [disableKeyboardInput] the OnFocusChangeListener will be preserved.  */
  fun enableKeyboardInput()

  /** Disables the keyboard input using a focus change listener and delegating to the previous focus change listener.  */
  fun disableKeyboardInput(emojiPopup: EmojiPopup?)

  /** Forces this EditText to contain only one Emoji.  */
  fun forceSingleEmoji()
}
