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

import android.text.SpannableString
import com.vanniktech.emoji.internal.EmojiSpan
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class EmojiManagerAndroidTest {
  @Before fun setUp() {
    EmojiManager.install(TestEmojiProvider)
  }

  @After fun tearDown() {
    EmojiManager.destroy()
  }

  @Test fun simple() {
    assertEquals(
      expected = 1,
      actual = spansFor(SpannableString(emojiBalloon.unicode)).size,
    )
  }

  @Test fun inString() {
    assertEquals(
      expected = 1,
      actual = spansFor(SpannableString("test" + emojiBalloon.unicode + "abc")).size,
    )
  }

  @Test fun multiple() {
    assertEquals(
      expected = 2,
      actual = spansFor(SpannableString(emojiBalloon.unicode + emojiYoYo.unicode)).size,
    )
  }

  @Test fun multipleInString() {
    assertEquals(
      expected = 2,
      actual = spansFor(SpannableString("abc" + emojiBalloon.unicode + "cba" + emojiYoYo.unicode + "xyz")).size,
    )
  }

  @Test fun emoji() {
    assertEquals(
      expected = 1,
      actual = spansFor(SpannableString(emojiReminderRibbon.unicode)).size,
    )
  }

  @Test fun emojiVariantSelector16() {
    assertEquals(
      expected = 1,
      actual = spansFor(SpannableString(emojiReminderRibbon.variants.first().unicode)).size,
    )
  }

  @Test fun emojiCombined() {
    assertEquals(
      expected = 1,
      actual = spansFor(SpannableString(emojiBaldPerson.unicode)).size,
    )
  }

  @Test fun emojiCombinedVariant() {
    assertEquals(
      expected = 1,
      actual = spansFor(SpannableString(emojiBaldPerson.variants.first().unicode)).size,
    )
  }

  @Test fun empty() {
    assertEquals(
      expected = 0,
      actual = spansFor(SpannableString("")).size,
    )
  }

  @Test fun noneInString() {
    assertEquals(
      expected = 0,
      actual = spansFor(SpannableString("abcdefg")).size,
    )
  }

  @Suppress("DEPRECATION")
  private fun spansFor(text: SpannableString): Array<EmojiSpan> {
    EmojiManager.replaceWithImages(RuntimeEnvironment.application, text, 22f)
    return text.getSpans(0, text.length, EmojiSpan::class.java)
  }
}
