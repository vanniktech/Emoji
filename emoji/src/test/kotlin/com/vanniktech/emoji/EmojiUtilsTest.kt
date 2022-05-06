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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner::class) class EmojiUtilsTest {
  @Before fun setUp() {
    val emoji1 = TestEmoji(intArrayOf(CODE_POINT_1), listOf("test"), false)
    val emoji2 = TestEmoji(intArrayOf(CODE_POINT_2), listOf("test"), false)
    EmojiManager.install(TestEmojiProvider(emoji1, emoji2))
  }

  @Test fun isOnlyEmojisEmpty() {
    assertEquals(false, "".isOnlyEmojis())
  }

  @Test fun isOnlyEmojisNull() {
    assertEquals(false, null.isOnlyEmojis())
  }

  @Test fun isOnlyEmojisText() {
    assertEquals(false, "hello world!".isOnlyEmojis())
  }

  @Test fun isOnlyEmojisSingleEmoji() {
    assertEquals(true, EMOJI_1.isOnlyEmojis())
  }

  @Test fun isOnlyEmojisMultipleEmojis() {
    assertEquals(true, (EMOJI_1 + EMOJI_2).isOnlyEmojis())
  }

  @Test fun isOnlyEmojisMultipleEmojisWithSpaces() {
    assertEquals(true, """ 	$EMOJI_1	    $EMOJI_2  """.isOnlyEmojis())
  }

  @Test fun isOnlyEmojisSingleEmojiAndText() {
    assertEquals(false, (EMOJI_1 + "hello").isOnlyEmojis())
  }

  @Test fun isOnlyEmojisSingleTextAndEmoji() {
    assertEquals(false, ("hello$EMOJI_1").isOnlyEmojis())
  }

  @Test fun isOnlyEmojisMultipleEmojisAndText() {
    assertEquals(false, (EMOJI_1 + "hello" + EMOJI_2).isOnlyEmojis())
  }

  @Test fun isOnlyEmojisMultipleTextAndEmojis() {
    assertEquals(false, ("hello" + EMOJI_1 + "world").isOnlyEmojis())
  }

  @Test fun emojisCountEmpty() {
    assertEquals(0, "".emojisCount())
  }

  @Test fun emojisCountNull() {
    assertEquals(0, null.emojisCount())
  }

  @Test fun emojisCountText() {
    assertEquals(0, "hello world!".emojisCount())
  }

  @Test fun emojisCountSingleEmoji() {
    assertEquals(1, EMOJI_1.emojisCount())
  }

  @Test fun emojisCountMultipleEmoji() {
    assertEquals(2, (EMOJI_1 + EMOJI_2).emojisCount())
  }

  @Test fun emojisCountMultipleEmojisWithSpaces() {
    assertEquals(2, """ $EMOJI_1    $EMOJI_2  """.emojisCount())
  }

  @Test fun emojisCountSingleEmojiAndText() {
    assertEquals(1, (EMOJI_1 + "hello").emojisCount())
  }

  @Test fun emojisCountSingleTextAndEmoji() {
    assertEquals(1, ("hello$EMOJI_1").emojisCount())
  }

  @Test fun emojisCountMultipleEmojisAndText() {
    assertEquals(2, (EMOJI_1 + "hello" + EMOJI_2).emojisCount())
  }

  @Test fun emojisCountMultipleTextAndEmojis() {
    assertEquals(1, ("hello" + EMOJI_1 + "world").emojisCount())
  }

  companion object {
    const val EMOJI_1 = "\u1234"
    val CODE_POINT_1 = EMOJI_1.codePointAt(0)
    const val EMOJI_2 = "\u4321"
    val CODE_POINT_2 = EMOJI_2.codePointAt(0)
  }
}
