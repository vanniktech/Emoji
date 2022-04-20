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

package com.vanniktech.emoji;

public interface EmojiForceable {
  /** Returns true when {@link #enableKeyboardInput()} was called and otherwise false. */
  boolean isKeyboardInputDisabled();

  /** Enables the keyboard input. If it has been disabled before using {@link #disableKeyboardInput(EmojiPopup)} the OnFocusChangeListener will be preserved. */
  void enableKeyboardInput();

  /** Disables the keyboard input using a focus change listener and delegating to the previous focus change listener. */
  void disableKeyboardInput(EmojiPopup emojiPopup);

  /** Forces this EditText to contain only one Emoji. */
  void forceSingleEmoji();
}
