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
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EmojiManagerTest {
  private lateinit var provider: EmojiProvider

  @BeforeTest fun setUp() {
    val emoji1 = TestEmoji(intArrayOf(0x1234), listOf("test"), false)
    val emoji2 = TestEmoji(intArrayOf(0x4321), listOf("test"), false)
    val emoji3 = TestEmoji(intArrayOf(0x5678), listOf("test"), false)
    val emoji4 = TestEmoji(intArrayOf(0x1234, 0x4321, 0x9999), listOf("test"), false)
    provider = TestEmojiProvider(emoji1, emoji2, emoji3, emoji4)
  }

  @AfterTest fun tearDown() {
    EmojiManager.destroy()
  }

  @Test fun installNormalCategory() {
    EmojiManager.install(provider)
    assertEquals(true, EmojiManager.categories().isNotEmpty())
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

  @Test fun installNormalEmoji() {
    EmojiManager.install(provider)
    assertEquals(
      TestEmoji(intArrayOf(0x1234), listOf("test"), false),
      EmojiManager.findEmoji(String(intArrayOf(0x1234), 0, 1)),
    )
  }

  @Test fun installMultiple() {
    EmojiManager.install(provider)
    EmojiManager.install(provider)

    // No duplicate categories.
    assertEquals(1, EmojiManager.categories().size)
  }

  @Test fun destroy() {
    EmojiManager.destroy()
    assertFailsWith<IllegalStateException>("Please install an EmojiProvider through the EmojiManager.install() method first.") {
      EmojiManager.findEmoji("test")
    }
  }

  @Test fun findEmojiNormal() {
    EmojiManager.install(provider)
    assertEquals(
      TestEmoji(intArrayOf(0x5678), listOf("test"), false),
      EmojiManager.findEmoji(String(intArrayOf(0x5678), 0, 1)),
    )
  }

  @Test fun findEmojiEmpty() {
    EmojiManager.install(provider)
    assertEquals(null, EmojiManager.findEmoji(""))
  }

  @Test fun findAllEmojisNormal() {
    EmojiManager.install(provider)
    val text = (
      "te" + String(intArrayOf(0x5678), 0, 1) +
        "st" + String(intArrayOf(0x1234), 0, 1)
      )
    val firstExpectedRange = EmojiRange(TestEmoji(intArrayOf(0x5678), listOf("test"), false), 2..3)
    val secondExpectedRange = EmojiRange(TestEmoji(intArrayOf(0x1234), listOf("test"), false), 5..6)
    assertEquals(
      listOf(
        firstExpectedRange,
        secondExpectedRange,
      ),
      EmojiManager.findAllEmojis(text),
    )
  }

  @Test fun findAllEmojisEmpty() {
    EmojiManager.install(provider)
    assertEquals(true, EmojiManager.findAllEmojis("").isEmpty())
  }

  @Test fun findAllEmojisNull() {
    EmojiManager.install(provider)
    assertEquals(true, EmojiManager.findAllEmojis(null).isEmpty())
  }
}
