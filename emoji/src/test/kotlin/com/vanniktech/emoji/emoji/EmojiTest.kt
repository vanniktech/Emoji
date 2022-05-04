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
package com.vanniktech.emoji.emoji

import com.vanniktech.emoji.R
import org.junit.Assert.assertEquals
import org.junit.Test

class EmojiTest {
  @Test fun multipleCodePoints() {
    val emoji = Emoji(intArrayOf(0x1234, 0x5678), arrayOf("test"), R.drawable.emoji_backspace, false)
    assertEquals(2, emoji.unicode.length)
    assertEquals(String(intArrayOf(0x1234, 0x5678), 0, 2), emoji.unicode)
  }

  @Test fun baseWithoutVariant() {
    val emoji = Emoji(0x1234, arrayOf("test"), R.drawable.emoji_backspace, false)
    assertEquals(emoji, emoji.base)
  }

  @Test fun baseWithVariant() {
    val variant = Emoji(0x4321, arrayOf("test"), R.drawable.emoji_recent, false)
    val emoji = Emoji(0x1234, arrayOf("test"), R.drawable.emoji_backspace, false, variant)
    assertEquals(emoji, variant.base)
  }

  @Test fun baseWithMultipleVariants() {
    val variant = Emoji(0x4321, arrayOf("test"), R.drawable.emoji_recent, false)
    val variant2 = Emoji(0x5678, arrayOf("test"), R.drawable.emoji_recent, false)
    val emoji = Emoji(0x1234, arrayOf("test"), R.drawable.emoji_backspace, false, variant, variant2)
    assertEquals(emoji, variant.base)
    assertEquals(emoji, variant2.base)
  }

  @Test fun baseWithRecursiveVariant() {
    val variantOfVariant = Emoji(0x4321, arrayOf("test"), R.drawable.emoji_recent, false)
    val variant = Emoji(0x5678, arrayOf("test"), R.drawable.emoji_recent, false, variantOfVariant)
    val emoji = Emoji(0x1234, arrayOf("test"), R.drawable.emoji_backspace, false, variant)
    assertEquals(emoji, variantOfVariant.base)
    assertEquals(emoji, variant.base)
  }

  @Test fun hasVariants() {
    assertEquals(false, Emoji(intArrayOf(0x1234, 0x5678), arrayOf("test"), R.drawable.emoji_backspace, false).hasVariants())
    assertEquals(true, Emoji(0x1f3cb, arrayOf("test"), R.drawable.emoji_backspace, false, Emoji(intArrayOf(0x1f3cb, 0x1f3fb), arrayOf("test"), R.drawable.emoji_backspace, false)).hasVariants())
  }

  @Test fun resource() {
    @Suppress("DEPRECATION")
    assertEquals(R.drawable.emoji_backspace, Emoji(intArrayOf(0x1234, 0x5678), arrayOf("test"), R.drawable.emoji_backspace, false).resource)
  }

  @Test fun length() {
    assertEquals(1, Emoji(0x1234, arrayOf("test"), R.drawable.emoji_backspace, false).length)
    assertEquals(2, Emoji(intArrayOf(0x1234, 0x5678), arrayOf("test"), R.drawable.emoji_backspace, false).length)
  }
}
