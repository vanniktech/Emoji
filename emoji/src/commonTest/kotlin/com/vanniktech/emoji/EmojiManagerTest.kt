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

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EmojiManagerTest {
  @AfterTest fun tearDown() {
    EmojiManager.destroy()
  }

  @Test fun installNormalCategory() {
    EmojiManager.install(TestEmojiProvider)
    assertEquals(expected = true, actual = EmojiManager.categories().isNotEmpty())
  }

  @Test fun noProviderInstalled() {
    assertFailsWith<IllegalStateException>("Please install an EmojiProvider through the EmojiManager.install() method first.") {
      EmojiManager.findEmoji("test")
    }
  }

  @Test fun installEmptyProvider() {
    assertFailsWith<IllegalArgumentException>("Your EmojiProvider must at least have one category with at least one emoji.") {
      EmojiManager.install(EmptyCategories)
    }
  }

  @Test fun installEmptyCategory() {
    assertFailsWith<IllegalArgumentException>("Your EmojiProvider must at least have one category with at least one emoji.") {
      EmojiManager.install(EmptyEmojiProvider)
    }
  }

  @Test fun installMultiple() {
    EmojiManager.install(TestEmojiProvider)
    EmojiManager.install(TestEmojiProvider)

    // No duplicate categories.
    assertEquals(expected = 1, actual = EmojiManager.categories().size)
    assertEquals(expected = TestEmojiProvider, actual = EmojiManager.emojiProvider())
  }

  @Test fun destroy() {
    EmojiManager.destroy()
    assertFailsWith<IllegalStateException>("Please install an EmojiProvider through the EmojiManager.install() method first.") {
      EmojiManager.findEmoji("test")
    }
  }

  @Test fun findEmojiNormal() {
    EmojiManager.install(TestEmojiProvider)
    assertEquals(expected = emojiBalloon, actual = EmojiManager.findEmoji(emojiBalloon.unicode))
  }

  @Test fun findEmojiEmpty() {
    EmojiManager.install(TestEmojiProvider)
    assertEquals(expected = null, actual = EmojiManager.findEmoji(""))
  }

  @Test fun findAllEmojisNormal() {
    EmojiManager.install(TestEmojiProvider)
    val text = "te${emojiBalloon.unicode}st${emojiYoYo.unicode}"
    val firstExpectedRange = EmojiRange(emojiBalloon, 2..3)
    val secondExpectedRange = EmojiRange(emojiYoYo, 6..7)
    assertEquals(
      expected = listOf(
        firstExpectedRange,
        secondExpectedRange,
      ),
      actual = EmojiManager.findAllEmojis(text),
    )
    assertEquals(
      expected = """🎈""",
      actual = text.substring(firstExpectedRange.range),
    )
    assertEquals(
      expected = """🪀""",
      actual = text.substring(secondExpectedRange.range),
    )
  }

  @Test fun findAllEmojisEmpty() {
    EmojiManager.install(TestEmojiProvider)
    assertEquals(expected = true, actual = EmojiManager.findAllEmojis("").isEmpty())
  }

  @Test fun findAllEmojisNull() {
    EmojiManager.install(TestEmojiProvider)
    assertEquals(expected = true, actual = EmojiManager.findAllEmojis(null).isEmpty())
  }
}
