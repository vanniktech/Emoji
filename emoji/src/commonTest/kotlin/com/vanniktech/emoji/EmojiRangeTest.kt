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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class EmojiRangeTest {
  @Test fun equality() {
    assertEquals(expected = EmojiRange(emoji = emojiBalloon, range = 0..1), actual = EmojiRange(emojiBalloon, 0..1))
    assertEquals(expected = EmojiRange(emoji = emojiYoYo, range = 0..1), actual = EmojiRange(emojiYoYo, 0..1))
    assertNotEquals(illegal = EmojiRange(emoji = emojiYoYo, range = 0..1), actual = EmojiRange(emojiYoYo, 0..0))
    assertNotEquals(illegal = EmojiRange(emoji = emojiYoYo, range = 1..10), actual = EmojiRange(emojiYoYo, 0..10))
    assertNotEquals(illegal = EmojiRange(emoji = emojiYoYo, range = 0..1), actual = EmojiRange(emojiBalloon, 0..1))
  }

  @Test fun hashy() {
    assertEquals(expected = EmojiRange(emoji = emojiBalloon, range = 0..1).hashCode(), actual = EmojiRange(emojiBalloon, 0..1).hashCode())
    assertEquals(expected = EmojiRange(emoji = emojiYoYo, range = 0..1).hashCode(), actual = EmojiRange(emojiYoYo, 0..1).hashCode())
    assertNotEquals(illegal = EmojiRange(emoji = emojiYoYo, range = 0..1).hashCode(), actual = EmojiRange(emojiYoYo, 0..0).hashCode())
    assertNotEquals(illegal = EmojiRange(emoji = emojiYoYo, range = 1..10).hashCode(), actual = EmojiRange(emojiYoYo, 0..10).hashCode())
    assertNotEquals(illegal = EmojiRange(emoji = emojiYoYo, range = 0..1).hashCode(), actual = EmojiRange(emojiBalloon, 0..1).hashCode())
  }
}
