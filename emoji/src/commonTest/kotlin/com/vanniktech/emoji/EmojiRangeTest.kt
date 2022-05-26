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

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class EmojiRangeTest {
  private lateinit var emoji: Emoji
  private lateinit var emoji2: Emoji

  @BeforeTest fun setUp() {
    emoji = TestEmoji(intArrayOf(0x1234), listOf("test"), false)
    emoji2 = TestEmoji(intArrayOf(0x5678), listOf("test"), false)
  }

  @Test fun equality() {
    assertEquals(EmojiRange(emoji = emoji, range = 0..1), EmojiRange(emoji, 0..1))
    assertEquals(EmojiRange(emoji = emoji2, range = 0..1), EmojiRange(emoji2, 0..1))
    assertNotEquals(EmojiRange(emoji = emoji2, range = 0..1), EmojiRange(emoji2, 0..0))
    assertNotEquals(EmojiRange(emoji = emoji2, range = 1..10), EmojiRange(emoji2, 0..10))
    assertNotEquals(EmojiRange(emoji = emoji2, range = 0..1), EmojiRange(emoji, 0..1))
  }

  @Test fun hashy() {
    assertEquals(EmojiRange(emoji = emoji, range = 0..1).hashCode(), EmojiRange(emoji, 0..1).hashCode())
    assertEquals(EmojiRange(emoji = emoji2, range = 0..1).hashCode(), EmojiRange(emoji2, 0..1).hashCode())
    assertNotEquals(EmojiRange(emoji = emoji2, range = 0..1).hashCode(), EmojiRange(emoji2, 0..0).hashCode())
    assertNotEquals(EmojiRange(emoji = emoji2, range = 1..10).hashCode(), EmojiRange(emoji2, 0..10).hashCode())
    assertNotEquals(EmojiRange(emoji = emoji2, range = 0..1).hashCode(), EmojiRange(emoji, 0..1).hashCode())
  }
}
