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

@Suppress("UNUSED")
object EmojiUtils {
  /** Returns true when the string contains only emojis. Note that whitespace will be filtered out. */
  @Deprecated(message = "Moved to another package", replaceWith = ReplaceWith("text.isOnlyEmojis()", imports = arrayOf("com.vanniktech.emoji.isOnlyEmojis")))
  fun isOnlyEmojis(text: CharSequence?) = text.isOnlyEmojis()

  /** Returns the emojis that were found in the given text. */
  @Deprecated(message = "Moved to another package", replaceWith = ReplaceWith("text.emojis()", imports = arrayOf("com.vanniktech.emoji.emojis")))
  fun emojis(text: CharSequence?) = text.emojis()

  /** Returns the number of all emojis that were found in the given text. */
  @Deprecated(message = "Moved to another package", replaceWith = ReplaceWith("text.emojisCount()", imports = arrayOf("com.vanniktech.emoji.emojisCount")))
  fun emojisCount(text: CharSequence?) = text.emojisCount()

  /** Returns a class that contains all of the emoji information that was found in the given text. */
  @Deprecated(message = "Moved to another package", replaceWith = ReplaceWith("text.emojiInformation()", imports = arrayOf("com.vanniktech.emoji.emojiInformation")))
  fun emojiInformation(text: CharSequence?) = text.emojiInformation()
}

/** Returns true when the string contains only emojis. Note that whitespace will be filtered out. */
fun CharSequence?.isOnlyEmojis(): Boolean {
  if (this != null && this.isNotEmpty()) {
    val inputWithoutSpaces = SPACE_REMOVAL.matcher(this).replaceAll(Matcher.quoteReplacement(""))
    return EmojiManager.getInstance()
      .emojiRepetitivePattern
      .matcher(inputWithoutSpaces)
      .matches()
  }
  return false
}

/** Returns the emojis that were found in the given text. */
fun CharSequence?.emojis(): List<EmojiRange> = EmojiManager.getInstance().findAllEmojis(this)

/** Returns the number of all emojis that were found in the given text. */
fun CharSequence?.emojisCount() = emojis().size

/** Returns a class that contains all of the emoji information that was found in the given text. */
fun CharSequence?.emojiInformation(): EmojiInformation {
  val emojis = EmojiManager.getInstance().findAllEmojis(this)
  return EmojiInformation(isOnlyEmojis(), emojis)
}
