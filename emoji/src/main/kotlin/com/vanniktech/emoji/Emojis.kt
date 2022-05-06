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

package com.vanniktech.emoji

private val SPACE_REMOVAL = Regex("[\\s]")

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
    EmojiManager.verifyInstalled()
    val inputWithoutSpaces = replace(SPACE_REMOVAL, "")
    return EmojiManager.emojiRepetitivePattern!!
      .matches(inputWithoutSpaces)
  }
  return false
}

/** Returns the emojis that were found in the given text. */
fun CharSequence?.emojis(): List<EmojiRange> = EmojiManager.findAllEmojis(this)

/** Returns the number of all emojis that were found in the given text. */
fun CharSequence?.emojisCount() = emojis().size

/** Returns a class that contains all of the emoji information that was found in the given text. */
fun CharSequence?.emojiInformation(): EmojiInformation = EmojiInformation(isOnlyEmojis(), emojis())
