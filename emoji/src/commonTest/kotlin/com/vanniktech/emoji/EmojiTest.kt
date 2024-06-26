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
    val emoji = emojiReminderRibbon.variants.first()
    assertEquals(expected = 3, actual = emoji.unicode.length)
    assertEquals(expected = "\ud83c\udf97\ufe0f", actual = emoji.unicode)
  }

  @Test fun baseWithoutVariant() {
    assertEquals(expected = emojiBalloon, actual = emojiBalloon.base)
  }

  @Test fun baseWithVariant() {
    assertEquals(expected = emojiReminderRibbon, actual = emojiReminderRibbon.base)
    assertEquals(expected = emojiReminderRibbon, actual = emojiReminderRibbon.variants.first().base)
  }

  @Test fun baseWithMultipleVariants() {
    emojiSuperhero.variants.forEach {
      assertEquals(expected = emojiSuperhero, actual = it.base)
    }
  }

  @Test fun baseWithRecursiveVariant() {
    val variantOfVariant = TestEmoji(
      unicode = "\u26f8",
      shortcodes = listOf("test"),
    )
    val variant = TestEmoji(
      unicode = "\uFE0F",
      shortcodes = listOf("test"),
      variants = listOf(variantOfVariant),
    )
    val emoji = TestEmoji(
      unicode = "\uDD79",
      shortcodes = listOf("test"),
      variants = listOf(variant),
    )
    assertEquals(emoji, variantOfVariant.base)
    assertEquals(emoji, variant.base)
  }

  @Test fun isVariant16() {
    assertEquals(
      expected = false,
      actual = emojiBalloon.isVariantSelector16(),
    )

    assertEquals(
      expected = true,
      actual = emojiReminderRibbon.isVariantSelector16(),
    )

    assertEquals(
      expected = false,
      actual = emojiReminderRibbon.variants.first().isVariantSelector16(),
    )
  }
}
