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
package com.vanniktech.emoji

import com.vanniktech.emoji.TestEmojiProvider.Companion.from
import com.vanniktech.emoji.emoji.Emoji
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner::class) class EmojiUtilsTest {
  @Before fun setUp() {
    val emoji1 = Emoji(CODE_POINT_1, arrayOf("test"), R.drawable.emoji_recent, false)
    val emoji2 = Emoji(CODE_POINT_2, arrayOf("test"), R.drawable.emoji_backspace, false)
    EmojiManager.install(from(emoji1, emoji2))
  }

  @Test fun isOnlyEmojisEmpty() {
    assertEquals(false, EmojiUtils.isOnlyEmojis(""))
  }

  @Test fun isOnlyEmojisNull() {
    assertEquals(false, EmojiUtils.isOnlyEmojis(null))
  }

  @Test fun isOnlyEmojisText() {
    assertEquals(false, EmojiUtils.isOnlyEmojis("hello world!"))
  }

  @Test fun isOnlyEmojisSingleEmoji() {
    assertEquals(true, EmojiUtils.isOnlyEmojis(EMOJI_1))
  }

  @Test fun isOnlyEmojisMultipleEmojis() {
    assertEquals(true, EmojiUtils.isOnlyEmojis(EMOJI_1 + EMOJI_2))
  }

  @Test fun isOnlyEmojisMultipleEmojisWithSpaces() {
    assertEquals(true, EmojiUtils.isOnlyEmojis(""" 	$EMOJI_1	    $EMOJI_2  """))
  }

  @Test fun isOnlyEmojisSingleEmojiAndText() {
    assertEquals(false, EmojiUtils.isOnlyEmojis(EMOJI_1 + "hello"))
  }

  @Test fun isOnlyEmojisSingleTextAndEmoji() {
    assertEquals(false, EmojiUtils.isOnlyEmojis("hello" + EMOJI_1))
  }

  @Test fun isOnlyEmojisMultipleEmojisAndText() {
    assertEquals(false, EmojiUtils.isOnlyEmojis(EMOJI_1 + "hello" + EMOJI_2))
  }

  @Test fun isOnlyEmojisMultipleTextAndEmojis() {
    assertEquals(false, EmojiUtils.isOnlyEmojis("hello" + EMOJI_1 + "world"))
  }

  @Test fun emojisCountEmpty() {
    assertEquals(0, EmojiUtils.emojisCount(""))
  }

  @Test fun emojisCountNull() {
    assertEquals(0, EmojiUtils.emojisCount(null))
  }

  @Test fun emojisCountText() {
    assertEquals(0, EmojiUtils.emojisCount("hello world!"))
  }

  @Test fun emojisCountSingleEmoji() {
    assertEquals(1, EmojiUtils.emojisCount(EMOJI_1))
  }

  @Test fun emojisCountMultipleEmoji() {
    assertEquals(2, EmojiUtils.emojisCount(EMOJI_1 + EMOJI_2))
  }

  @Test fun emojisCountMultipleEmojisWithSpaces() {
    assertEquals(2, EmojiUtils.emojisCount(""" $EMOJI_1    $EMOJI_2  """))
  }

  @Test fun emojisCountSingleEmojiAndText() {
    assertEquals(1, EmojiUtils.emojisCount(EMOJI_1 + "hello"))
  }

  @Test fun emojisCountSingleTextAndEmoji() {
    assertEquals(1, EmojiUtils.emojisCount("hello" + EMOJI_1))
  }

  @Test fun emojisCountMultipleEmojisAndText() {
    assertEquals(2, EmojiUtils.emojisCount(EMOJI_1 + "hello" + EMOJI_2))
  }

  @Test fun emojisCountMultipleTextAndEmojis() {
    assertEquals(1, EmojiUtils.emojisCount("hello" + EMOJI_1 + "world"))
  }

  companion object {
    const val EMOJI_1 = "\u1234"
    val CODE_POINT_1 = EMOJI_1.codePointAt(0)
    const val EMOJI_2 = "\u4321"
    val CODE_POINT_2 = EMOJI_2.codePointAt(0)
  }
}
