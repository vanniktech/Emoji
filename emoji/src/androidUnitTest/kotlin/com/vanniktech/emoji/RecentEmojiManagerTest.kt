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

import android.content.Context
import com.vanniktech.emoji.recent.RecentEmojiManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner::class) class RecentEmojiManagerTest {
  private lateinit var context: Context

  @Before fun setUp() {
    @Suppress("DEPRECATION")
    context = RuntimeEnvironment.application
  }

  @Test fun recentEmojis() {
    val recentEmojiManager = RecentEmojiManager(context)
    assertEquals(emptyList<Emoji>(), recentEmojiManager.getRecentEmojis())
  }

  @Test fun addEmoji() {
    val recentEmojiManager = RecentEmojiManager(context)
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
    val recentEmojiManager = RecentEmojiManager(context)
    val firstEmoji = TestEmoji(intArrayOf(0x1f437), listOf("test"), false)
    recentEmojiManager.addEmoji(firstEmoji)
    val secondEmoji = TestEmoji(intArrayOf(0x1f43d), listOf("test"), false)
    recentEmojiManager.addEmoji(secondEmoji)
    recentEmojiManager.persist()
    assertEquals(listOf(secondEmoji, firstEmoji), recentEmojiManager.getRecentEmojis())
  }

  @Test fun duplicateEmojis() {
    val recentEmojiManager = RecentEmojiManager(context)
    val emoji = TestEmoji(intArrayOf(0x1f437), listOf("test"), false)
    recentEmojiManager.addEmoji(emoji)
    recentEmojiManager.addEmoji(emoji)
    recentEmojiManager.persist()
    assertEquals(listOf(emoji), recentEmojiManager.getRecentEmojis())
  }

  @Test fun newShouldReplaceOld() {
    val recentEmojiManager = RecentEmojiManager(context)
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
    val recentEmojiManager = RecentEmojiManager(context)
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

  @Test fun maxRecentsInOrder() {
    val recentEmojiManager = RecentEmojiManager(context, maxRecents = 2)
    val first = TestEmoji(intArrayOf(0x2764), listOf("first"), false)
    val second = TestEmoji(intArrayOf(0x1f577), listOf("second"), false)
    val third = TestEmoji(intArrayOf(0x1f576), listOf("third"), false)
    recentEmojiManager.addEmoji(first)
    recentEmojiManager.addEmoji(second)
    recentEmojiManager.addEmoji(third)

    // In Memory.
    assertEquals(
      listOf(third, second),
      recentEmojiManager.getRecentEmojis(),
    )

    // Persist.
    recentEmojiManager.persist()

    try {
      // Reading from preferences.
      EmojiManager.install(TestEmojiProvider(first, second, third))
      val recentEmojiManager2 = RecentEmojiManager(context, maxRecents = 2)
      assertEquals(
        listOf(third, second),
        recentEmojiManager2.getRecentEmojis(),
      )
    } finally {
      EmojiManager.destroy()
    }
  }
}
