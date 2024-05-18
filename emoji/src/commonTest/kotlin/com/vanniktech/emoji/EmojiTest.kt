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

import kotlin.test.Test
import kotlin.test.assertEquals

class EmojiTest {
  @Test fun multipleCodePoints() {
    val emoji = TestEmoji(intArrayOf(0x1234, 0x5678), listOf("test"))
    assertEquals(2, emoji.unicode.length)
    assertEquals(String(intArrayOf(0x1234, 0x5678), 0, 2), emoji.unicode)
  }

  @Test fun baseWithoutVariant() {
    val emoji = TestEmoji(intArrayOf(0x1234), listOf("test"))
    assertEquals(emoji, emoji.base)
  }

  @Test fun baseWithVariant() {
    val variant = TestEmoji(intArrayOf(0x4321), listOf("test"))
    val emoji = TestEmoji(intArrayOf(0x1234), listOf("test"), listOf(variant))
    assertEquals(emoji, variant.base)
  }

  @Test fun baseWithMultipleVariants() {
    val variant = TestEmoji(intArrayOf(0x4321), listOf("test"))
    val variant2 = TestEmoji(intArrayOf(0x5678), listOf("test"))
    val emoji = TestEmoji(codePoints = intArrayOf(0x1234), listOf("test"), listOf(variant, variant2))
    assertEquals(emoji, variant.base)
    assertEquals(emoji, variant2.base)
  }

  @Test fun baseWithRecursiveVariant() {
    val variantOfVariant = TestEmoji(intArrayOf(0x4321), listOf("test"))
    val variant = TestEmoji(intArrayOf(0x5678), listOf("test"), listOf(variantOfVariant))
    val emoji = TestEmoji(intArrayOf(0x1234), listOf("test"), listOf(variant))
    assertEquals(emoji, variantOfVariant.base)
    assertEquals(emoji, variant.base)
  }
}
