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

import com.vanniktech.emoji.recent.RecentEmoji
import com.vanniktech.emoji.recent.RecentEmojiManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner::class) class RecentEmojiManagerTest {
  private lateinit var recentEmojiManager: RecentEmoji

  @Before fun setUp() {
    @Suppress("DEPRECATION")
    recentEmojiManager = RecentEmojiManager(RuntimeEnvironment.application)
  }

  @Test fun recentEmojis() {
    assertEquals(emptyList<Emoji>(), recentEmojiManager.getRecentEmojis())
  }

  @Test fun addEmoji() {
    recentEmojiManager.addEmoji(TestEmoji(intArrayOf(0x1f437), listOf("test"), false))
    recentEmojiManager.addEmoji(TestEmoji(intArrayOf(0x1f43d), listOf("test"), false))
    assertEquals(
      listOf(
        TestEmoji(intArrayOf(0x1f43d), listOf("test"), false),
        TestEmoji(intArrayOf(0x1f437), listOf("test"), false),
      ),
      recentEmojiManager.getRecentEmojis(),
    )
  }

  @Test fun persist() {
    val firstEmoji = TestEmoji(intArrayOf(0x1f437), listOf("test"), false)
    recentEmojiManager.addEmoji(firstEmoji)
    val secondEmoji = TestEmoji(intArrayOf(0x1f43d), listOf("test"), false)
    recentEmojiManager.addEmoji(secondEmoji)
    recentEmojiManager.persist()
    assertEquals(listOf(secondEmoji, firstEmoji), recentEmojiManager.getRecentEmojis())
  }

  @Test fun duplicateEmojis() {
    val emoji = TestEmoji(intArrayOf(0x1f437), listOf("test"), false)
    recentEmojiManager.addEmoji(emoji)
    recentEmojiManager.addEmoji(emoji)
    recentEmojiManager.persist()
    assertEquals(listOf(emoji), recentEmojiManager.getRecentEmojis())
  }

  @Test fun inOrder() {
    recentEmojiManager.addEmoji(TestEmoji(intArrayOf(0x1f55a), listOf("test"), false))
    recentEmojiManager.addEmoji(TestEmoji(intArrayOf(0x1f561), listOf("test"), false))
    recentEmojiManager.addEmoji(TestEmoji(intArrayOf(0x1f4e2), listOf("test"), false))
    recentEmojiManager.addEmoji(TestEmoji(intArrayOf(0x1f562), listOf("test"), false))
    recentEmojiManager.addEmoji(TestEmoji(intArrayOf(0xe535), listOf("test"), false))
    recentEmojiManager.addEmoji(TestEmoji(intArrayOf(0x1f563), listOf("test"), false))
    recentEmojiManager.persist()
    val recentEmojis = recentEmojiManager.getRecentEmojis()
    assertEquals(
      listOf(
        TestEmoji(intArrayOf(0x1f563), listOf("test"), false),
        TestEmoji(intArrayOf(0xe535), listOf("test"), false),
        TestEmoji(intArrayOf(0x1f562), listOf("test"), false),
        TestEmoji(intArrayOf(0x1f4e2), listOf("test"), false),
        TestEmoji(intArrayOf(0x1f561), listOf("test"), false),
        TestEmoji(intArrayOf(0x1f55a), listOf("test"), false),
      ),
      recentEmojis,
    )
  }

  @Test fun newShouldReplaceOld() {
    val first = TestEmoji(intArrayOf(0x2764), listOf("test"), false)
    val second = TestEmoji(intArrayOf(0x1f577), listOf("test"), false)
    recentEmojiManager.addEmoji(first)
    assertEquals(listOf(first), recentEmojiManager.getRecentEmojis())
    recentEmojiManager.addEmoji(second)
    assertEquals(listOf(second, first), recentEmojiManager.getRecentEmojis())
    recentEmojiManager.addEmoji(first)
    assertEquals(listOf(first, second), recentEmojiManager.getRecentEmojis())
  }

  @Test fun addSkinTone() {
    val variant1 = TestEmoji(intArrayOf(0x1f55b), listOf("test"), false)
    val variant2 = TestEmoji(intArrayOf(0x1f55c), listOf("test"), false)
    val variant3 = TestEmoji(intArrayOf(0x1f55d), listOf("test"), false)
    val base = TestEmoji(intArrayOf(0x1f55a), listOf("test"), false, listOf(variant1, variant2, variant3))
    recentEmojiManager.addEmoji(base)
    recentEmojiManager.addEmoji(variant1)
    assertEquals(listOf(variant1), recentEmojiManager.getRecentEmojis())
    recentEmojiManager.addEmoji(variant2)
    assertEquals(listOf(variant2), recentEmojiManager.getRecentEmojis())
    recentEmojiManager.addEmoji(variant3)
    assertEquals(listOf(variant3), recentEmojiManager.getRecentEmojis())
  }

  @Test fun maxRecents() {
    for (i in 0..499) {
      recentEmojiManager.addEmoji(TestEmoji(intArrayOf(i), listOf("test"), false))
    }
    assertEquals(40, recentEmojiManager.getRecentEmojis().size)
  }
}
