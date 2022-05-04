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

import com.vanniktech.emoji.emoji.Emoji
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class EmojiInformationTest {
  private lateinit var empty: EmojiInformation
  private lateinit var empty2: EmojiInformation
  private lateinit var one: EmojiInformation
  private lateinit var one2: EmojiInformation

  @Before fun setUp() {
    val emptyList: List<EmojiRange> = emptyList()
    empty = EmojiInformation(false, emptyList)
    empty2 = EmojiInformation(false, emptyList)
    val emoji = Emoji(intArrayOf(0x1234), arrayOf("test"), R.drawable.emoji_recent, false)
    one = EmojiInformation(false, listOf(EmojiRange(0, 1, emoji)))
    one2 = EmojiInformation(false, listOf(EmojiRange(0, 1, emoji)))
  }

  @Test fun equality() {
    assertEquals(empty2, empty)
    assertEquals(one2, one)
    assertNotEquals(empty, one)
    assertNotEquals(one, empty)
  }

  @Test fun hashy() {
    assertEquals(empty2.hashCode(), empty.hashCode())
    assertEquals(one2.hashCode(), one.hashCode())
    assertNotEquals(empty.hashCode(), one.hashCode())
    assertNotEquals(one.hashCode(), empty.hashCode())
  }
}
