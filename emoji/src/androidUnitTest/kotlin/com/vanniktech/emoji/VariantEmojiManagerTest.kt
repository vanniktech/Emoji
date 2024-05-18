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
import com.vanniktech.emoji.variant.VariantEmojiManager
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner::class) class VariantEmojiManagerTest {
  private lateinit var variantEmojiManager: VariantEmojiManager
  private lateinit var application: Application

  @Suppress("DEPRECATION")
  @Before fun setUp() {
    application = RuntimeEnvironment.application
    variantEmojiManager = VariantEmojiManager(application)
  }

  @After fun tearDown() {
    EmojiManager.destroy()
  }

  @Test fun noVariants() {
    assertEquals(expected = emojiBalloon, actual = variantEmojiManager.getVariant(emojiBalloon))
    assertEquals(expected = emptyList(), actual = variantEmojiManager.getVariants(emojiBalloon))
  }

  @Test fun variantSelector16() {
    assertEquals(expected = emojiReminderRibbon, actual = variantEmojiManager.getVariant(emojiReminderRibbon))

    // But we don't want it since it is the same!
    assertEquals(expected = 1, actual = emojiReminderRibbon.variants.size)
    assertEquals(expected = emptyList(), actual = variantEmojiManager.getVariants(emojiReminderRibbon))
  }

  @Test fun variantUsingOnlyVariants() {
    val variant = emojiSuperhero.variants.random()
    variantEmojiManager.addVariant(variant)
    assertEquals(expected = variant, actual = variantEmojiManager.getVariant(emojiSuperhero))
    assertEquals(expected = emojiSuperhero.variants, actual = variantEmojiManager.getVariants(emojiSuperhero))

    emojiSuperhero.variants.forEach {
      assertEquals(expected = variant, actual = variantEmojiManager.getVariant(it))
      assertEquals(expected = emptyList(), actual = variantEmojiManager.getVariants(it))
    }
  }

  @Test fun variantUsingOnlyVariantsBeforeBase() {
    variantEmojiManager.addVariant(emojiSuperhero.variants.random())
    variantEmojiManager.addVariant(emojiSuperhero)
    assertEquals(expected = emojiSuperhero, actual = variantEmojiManager.getVariant(emojiSuperhero))
    assertEquals(expected = emojiSuperhero.variants, actual = variantEmojiManager.getVariants(emojiSuperhero))
  }

  @Test fun persist() {
    emojiSuperhero.variants.forEach {
      variantEmojiManager.addVariant(it)
    }

    variantEmojiManager.persist()

    EmojiManager.install(TestEmojiProvider)
    val manager = VariantEmojiManager(application)
    assertEquals(expected = emojiSuperhero.variants.last(), actual = manager.getVariant(emojiSuperhero))
    assertEquals(expected = emojiSuperhero.variants, actual = manager.getVariants(emojiSuperhero))
    assertEquals(expected = emojiSuperhero.variants.last(), actual = variantEmojiManager.getVariant(emojiSuperhero))
    assertEquals(expected = emojiSuperhero.variants, actual = variantEmojiManager.getVariants(emojiSuperhero))
  }

  @Test fun persistEmpty() {
    variantEmojiManager.persist()

    EmojiManager.install(TestEmojiProvider)
    val manager = VariantEmojiManager(application)
    assertEquals(expected = emojiSuperhero, actual = manager.getVariant(emojiSuperhero))
    assertEquals(expected = emojiSuperhero.variants, actual = manager.getVariants(emojiSuperhero))
    assertEquals(expected = emojiSuperhero, actual = variantEmojiManager.getVariant(emojiSuperhero))
    assertEquals(expected = emojiSuperhero.variants, actual = variantEmojiManager.getVariants(emojiSuperhero))
  }
}
