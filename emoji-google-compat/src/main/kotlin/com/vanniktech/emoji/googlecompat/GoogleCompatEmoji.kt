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

package com.vanniktech.emoji.googlecompat

import android.content.Context
import android.graphics.drawable.Drawable
import com.vanniktech.emoji.emoji.Emoji

internal class GoogleCompatEmoji : Emoji {
  internal constructor(
    ints: IntArray,
    shortcodes: Array<String>,
    isDuplicate: Boolean,
    vararg emojis: Emoji
  ) : super(ints, shortcodes, -1, isDuplicate, *emojis)

  internal constructor(
    codePoint: Int,
    shortcodes: Array<String>,
    isDuplicate: Boolean,
    vararg emojis: Emoji
  ) : super(codePoint, shortcodes, -1, isDuplicate, *emojis)

  override fun getDrawable(context: Context): Drawable = GoogleCompatEmojiDrawable(unicode)
}
