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

import com.vanniktech.emoji.emoji.Emoji
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class UtilsTest {
  @Test fun checkNull() {
    assertThrows("param is null", IllegalArgumentException::class.java) {
      Utils.checkNotNull<Any>(null, "param is null")
    }
  }

  @Test fun checkNotNull() {
    Utils.checkNotNull("valid", "null is null")
  }

  @Test fun asListFilter() {
    val emojis = arrayOf(
      Emoji("\u1234".codePointAt(0), arrayOf("test"), R.drawable.emoji_backspace, false),
      Emoji("\u1234".codePointAt(0), arrayOf("test"), R.drawable.emoji_backspace, true),
    )
    val filtered = Utils.asListWithoutDuplicates(emojis)
    assertEquals(listOf(emojis[0]), filtered)
  }
}
