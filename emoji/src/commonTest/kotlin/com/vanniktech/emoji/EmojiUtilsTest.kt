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

class EmojiUtilsTest {
  @BeforeTest fun setUp() {
    val emoji1 = TestEmoji(intArrayOf(CODE_POINT_1), listOf("test"))
    val emoji2 = TestEmoji(intArrayOf(CODE_POINT_2), listOf("test"))
    EmojiManager.install(TestEmojiProvider(emoji1, emoji2))
  }

  @Test fun isOnlyEmojisEmpty() {
    assertEquals(expected = false, actual = "".isOnlyEmojis())
  }

  @Test fun isOnlyEmojisNull() {
    assertEquals(expected = false, actual = null.isOnlyEmojis())
  }

  @Test fun isOnlyEmojisText() {
    assertEquals(expected = false, actual = "hello world!".isOnlyEmojis())
  }

  @Test fun isOnlyEmojisSingleEmoji() {
    assertEquals(expected = true, actual = EMOJI_1.isOnlyEmojis())
  }

  @Test fun isOnlyEmojisMultipleEmojis() {
    assertEquals(expected = true, actual = (EMOJI_1 + EMOJI_2).isOnlyEmojis())
  }

  @Test fun isOnlyEmojisMultipleEmojisWithSpaces() {
    assertEquals(expected = true, actual = """ 	$EMOJI_1	    $EMOJI_2  """.isOnlyEmojis())
  }

  @Test fun isOnlyEmojisSingleEmojiAndText() {
    assertEquals(expected = false, actual = (EMOJI_1 + "hello").isOnlyEmojis())
  }

  @Test fun isOnlyEmojisSingleTextAndEmoji() {
    assertEquals(expected = false, actual = ("hello$EMOJI_1").isOnlyEmojis())
  }

  @Test fun isOnlyEmojisMultipleEmojisAndText() {
    assertEquals(expected = false, actual = (EMOJI_1 + "hello" + EMOJI_2).isOnlyEmojis())
  }

  @Test fun isOnlyEmojisMultipleTextAndEmojis() {
    assertEquals(expected = false, actual = ("hello" + EMOJI_1 + "world").isOnlyEmojis())
  }

  @Test fun emojisCountEmpty() {
    assertEquals(expected = 0, actual = "".emojisCount())
  }

  @Test fun emojisCountNull() {
    assertEquals(expected = 0, actual = null.emojisCount())
  }

  @Test fun emojisCountText() {
    assertEquals(expected = 0, actual = "hello world!".emojisCount())
  }

  @Test fun emojisCountSingleEmoji() {
    assertEquals(expected = 1, actual = EMOJI_1.emojisCount())
  }

  @Test fun emojisCountMultipleEmoji() {
    assertEquals(expected = 2, actual = (EMOJI_1 + EMOJI_2).emojisCount())
  }

  @Test fun emojisCountMultipleEmojisWithSpaces() {
    assertEquals(expected = 2, actual = """ $EMOJI_1    $EMOJI_2  """.emojisCount())
  }

  @Test fun emojisCountSingleEmojiAndText() {
    assertEquals(expected = 1, actual = (EMOJI_1 + "hello").emojisCount())
  }

  @Test fun emojisCountSingleTextAndEmoji() {
    assertEquals(expected = 1, actual = ("hello$EMOJI_1").emojisCount())
  }

  @Test fun emojisCountMultipleEmojisAndText() {
    assertEquals(expected = 2, actual = (EMOJI_1 + "hello" + EMOJI_2).emojisCount())
  }

  @Test fun emojisCountMultipleTextAndEmojis() {
    assertEquals(expected = 1, actual = ("hello" + EMOJI_1 + "world").emojisCount())
  }

  companion object {
    const val EMOJI_1 = "\u1234"
    val CODE_POINT_1 = EMOJI_1.codePointAt(0)
    const val EMOJI_2 = "\u4321"
    val CODE_POINT_2 = EMOJI_2.codePointAt(0)
  }
}
