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

import java.util.regex.Matcher
import java.util.regex.Pattern

private val SPACE_REMOVAL = Pattern.compile("[\\s]")

object EmojiUtils {
  /** Returns true when the string contains only emojis. Note that whitespace will be filtered out. */
  fun isOnlyEmojis(text: CharSequence?): Boolean {
    if (text != null && text.isNotEmpty()) {
      val inputWithoutSpaces = SPACE_REMOVAL.matcher(text).replaceAll(Matcher.quoteReplacement(""))
      return EmojiManager.getInstance()
        .emojiRepetitivePattern
        .matcher(inputWithoutSpaces)
        .matches()
    }
    return false
  }

  /** Returns the emojis that were found in the given text. */
  fun emojis(text: CharSequence?): List<EmojiRange> =
    EmojiManager.getInstance().findAllEmojis(text)

  /** Returns the number of all emojis that were found in the given text. */
  fun emojisCount(text: CharSequence?): Int = emojis(text).size

  /** Returns a class that contains all of the emoji information that was found in the given text. */
  fun emojiInformation(text: CharSequence?): EmojiInformation {
    val emojis = EmojiManager.getInstance().findAllEmojis(text)
    val isOnlyEmojis = isOnlyEmojis(text)
    return EmojiInformation(isOnlyEmojis, emojis)
  }
}
