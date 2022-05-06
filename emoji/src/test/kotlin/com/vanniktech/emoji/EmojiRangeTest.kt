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

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class EmojiRangeTest {
  private lateinit var emoji: Emoji
  private lateinit var emoji2: Emoji

  @Before fun setUp() {
    emoji = TestEmoji(intArrayOf(0x1234), listOf("test"), false)
    emoji2 = TestEmoji(intArrayOf(0x5678), listOf("test"), false)
  }

  @Test fun equality() {
    assertEquals(EmojiRange(0, 1, emoji), EmojiRange(0, 1, emoji))
    assertEquals(EmojiRange(0, 1, emoji2), EmojiRange(0, 1, emoji2))
    assertNotEquals(EmojiRange(0, 1, emoji2), EmojiRange(0, 0, emoji2))
    assertNotEquals(EmojiRange(10, 1, emoji2), EmojiRange(10, 0, emoji2))
    assertNotEquals(EmojiRange(0, 1, emoji2), EmojiRange(0, 1, emoji))
  }

  @Test fun hashy() {
    assertEquals(EmojiRange(0, 1, emoji).hashCode(), EmojiRange(0, 1, emoji).hashCode())
    assertEquals(EmojiRange(0, 1, emoji2).hashCode(), EmojiRange(0, 1, emoji2).hashCode())
    assertNotEquals(EmojiRange(0, 1, emoji2).hashCode(), EmojiRange(0, 0, emoji2).hashCode())
    assertNotEquals(EmojiRange(10, 1, emoji2).hashCode(), EmojiRange(10, 0, emoji2).hashCode())
    assertNotEquals(EmojiRange(0, 1, emoji2).hashCode(), EmojiRange(0, 1, emoji).hashCode())
  }
}
