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
import kotlin.test.assertNotEquals

class EmojiInformationTest {
  private lateinit var empty: EmojiInformation
  private lateinit var empty2: EmojiInformation
  private lateinit var one: EmojiInformation
  private lateinit var one2: EmojiInformation

  @BeforeTest fun setUp() {
    empty = EmojiInformation(false, emptyList())
    empty2 = EmojiInformation(false, emptyList())

    one = EmojiInformation(false, listOf(EmojiRange(emojiBalloon, 0..1)))
    one2 = EmojiInformation(false, listOf(EmojiRange(emojiBalloon, 0..1)))
  }

  @Test fun equality() {
    assertEquals(expected = empty2, actual = empty)
    assertEquals(expected = one2, actual = one)
    assertNotEquals(illegal = empty, actual = one)
    assertNotEquals(illegal = one, actual = empty)
  }

  @Test fun hashy() {
    assertEquals(expected = empty2.hashCode(), actual = empty.hashCode())
    assertEquals(expected = one2.hashCode(), actual = one.hashCode())
    assertNotEquals(illegal = empty.hashCode(), actual = one.hashCode())
    assertNotEquals(illegal = one.hashCode(), actual = empty.hashCode())
  }
}
