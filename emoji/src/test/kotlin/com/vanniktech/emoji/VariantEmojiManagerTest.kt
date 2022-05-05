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

import android.app.Application
import com.vanniktech.emoji.TestEmojiProvider.Companion.from
import com.vanniktech.emoji.emoji.Emoji
import com.vanniktech.emoji.variant.VariantEmojiManager
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner::class) class VariantEmojiManagerTest {
  private lateinit var variantEmojiManager: VariantEmojiManager
  private lateinit var variant1: Emoji
  private lateinit var variant2: Emoji
  private lateinit var variant3: Emoji
  private lateinit var base: Emoji
  private lateinit var emoji: Emoji
  private lateinit var application: Application

  @Suppress("DEPRECATION")
  @Before fun setUp() {
    application = RuntimeEnvironment.application
    variantEmojiManager = VariantEmojiManager(application)
    emoji = TestEmoji(intArrayOf(0x1f437), arrayOf("test"), false)
    variant1 = TestEmoji(intArrayOf(0x1f55b), arrayOf("test"), false)
    variant2 = TestEmoji(intArrayOf(0x1f55c), arrayOf("test"), false)
    variant3 = TestEmoji(intArrayOf(0x1f55d), arrayOf("test"), false)
    base = TestEmoji(intArrayOf(0x1f55a), arrayOf("test"), false, variant1, variant2, variant3)
  }

  @Test fun variantDefault() {
    assertEquals(emoji, variantEmojiManager.getVariant(emoji))
  }

  @Test fun variantUsingOnlyVariants() {
    variantEmojiManager.addVariant(variant2)
    assertEquals(variant2, variantEmojiManager.getVariant(base))
    assertEquals(variant2, variantEmojiManager.getVariant(variant1))
    assertEquals(variant2, variantEmojiManager.getVariant(variant2))
    assertEquals(variant2, variantEmojiManager.getVariant(variant3))
  }

  @Test fun variantUsingOnlyVariantsBeforeBase() {
    variantEmojiManager.addVariant(variant1)
    variantEmojiManager.addVariant(base)
    assertEquals(base, variantEmojiManager.getVariant(variant1))
  }

  @Test fun variantUsingSame() {
    variantEmojiManager.addVariant(variant1)
    variantEmojiManager.addVariant(variant1)
    assertEquals(variant1, variantEmojiManager.getVariant(variant1))
  }

  @Test fun persist() {
    variantEmojiManager.addVariant(variant1)
    variantEmojiManager.addVariant(variant2)
    variantEmojiManager.persist()
    EmojiManager.install(from(variant1, variant2))
    val sharedPrefsManager = VariantEmojiManager(application)
    assertEquals(variant2, sharedPrefsManager.getVariant(base))
  }

  @Test fun persistEmpty() {
    variantEmojiManager.persist()
    EmojiManager.install(from(variant1, variant2))
    val sharedPrefsManager = VariantEmojiManager(application)
    assertEquals(base, sharedPrefsManager.getVariant(base))
  }
}
