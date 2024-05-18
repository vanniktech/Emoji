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
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner::class) class RecentEmojiManagerTest {
  private lateinit var context: Context

  @Before fun setUp() {
    @Suppress("DEPRECATION")
    context = RuntimeEnvironment.application
  }

  @After fun tearDown() {
    EmojiManager.destroy()
  }

  @Test fun recentEmojis() {
    val recentEmojiManager = RecentEmojiManager(context)
    assertEquals(expected = emptyList(), actual = recentEmojiManager.getRecentEmojis())
  }

  @Test fun addEmoji() {
    val recentEmojiManager = RecentEmojiManager(context)
    recentEmojiManager.addEmoji(emojiBalloon)
    recentEmojiManager.addEmoji(emojiYoYo)
    assertEquals(
      expected = listOf(
        emojiYoYo,
        emojiBalloon,
      ),
      actual = recentEmojiManager.getRecentEmojis(),
    )
  }

  @Test fun persist() {
    val recentEmojiManager = RecentEmojiManager(context)
    recentEmojiManager.addEmoji(emojiBalloon)
    recentEmojiManager.addEmoji(emojiYoYo)
    recentEmojiManager.persist()
    assertEquals(expected = listOf(emojiYoYo, emojiBalloon), actual = recentEmojiManager.getRecentEmojis())
  }

  @Test fun duplicateEmojis() {
    val recentEmojiManager = RecentEmojiManager(context)
    recentEmojiManager.addEmoji(emojiBalloon)
    recentEmojiManager.addEmoji(emojiBalloon)
    recentEmojiManager.persist()
    assertEquals(expected = listOf(emojiBalloon), actual = recentEmojiManager.getRecentEmojis())
  }

  @Test fun newShouldReplaceOld() {
    val recentEmojiManager = RecentEmojiManager(context)
    recentEmojiManager.addEmoji(emojiBalloon)
    assertEquals(expected = listOf(emojiBalloon), actual = recentEmojiManager.getRecentEmojis())
    recentEmojiManager.addEmoji(emojiYoYo)
    assertEquals(expected = listOf(emojiYoYo, emojiBalloon), actual = recentEmojiManager.getRecentEmojis())
    recentEmojiManager.addEmoji(emojiBalloon)
    assertEquals(expected = listOf(emojiBalloon, emojiYoYo), actual = recentEmojiManager.getRecentEmojis())
  }

  @Test fun addSkinTone() {
    val recentEmojiManager = RecentEmojiManager(context)
    recentEmojiManager.addEmoji(emojiSuperhero)

    emojiSuperhero.variants.forEach { variant ->
      recentEmojiManager.addEmoji(variant)
      assertEquals(expected = listOf(variant), actual = recentEmojiManager.getRecentEmojis())
    }
  }

  @Test fun maxRecentsInOrder() {
    val recentEmojiManager = RecentEmojiManager(context, maxRecents = 2)
    recentEmojiManager.addEmoji(emojiBalloon)
    recentEmojiManager.addEmoji(emojiYoYo)
    recentEmojiManager.addEmoji(emojiSuperhero)

    // In Memory.
    assertEquals(
      expected = listOf(emojiSuperhero, emojiYoYo),
      actual = recentEmojiManager.getRecentEmojis(),
    )

    // Persist.
    recentEmojiManager.persist()

    try {
      // Reading from preferences.
      EmojiManager.install(TestEmojiProvider)
      val recentEmojiManager2 = RecentEmojiManager(context, maxRecents = 2)
      assertEquals(
        listOf(emojiSuperhero, emojiYoYo),
        recentEmojiManager2.getRecentEmojis(),
      )
    } finally {
      EmojiManager.destroy()
    }
  }
}
