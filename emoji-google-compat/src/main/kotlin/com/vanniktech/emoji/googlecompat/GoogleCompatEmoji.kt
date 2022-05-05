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

internal class GoogleCompatEmoji internal constructor(
  override val unicode: String,
  override val shortcodes: List<String>,
  override val isDuplicate: Boolean,
  override val variants: List<GoogleCompatEmoji> = emptyList(),
) : Emoji {
  private var parent: GoogleCompatEmoji? = null

  override val base by lazy(LazyThreadSafetyMode.NONE) {
    var result = this
    while (result.parent != null) {
      result = result.parent!!
    }
    result
  }

  init {
    @Suppress("LeakingThis")
    for (variant in variants) {
      variant.parent = this
    }
  }

  override fun getDrawable(context: Context): Drawable = GoogleCompatEmojiDrawable(unicode)
  override fun destroy() = Unit

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }
    val emoji = other as GoogleCompatEmoji
    return (
      unicode == emoji.unicode && shortcodes == emoji.shortcodes &&
        variants == emoji.variants
      )
  }

  override fun hashCode(): Int {
    var result = unicode.hashCode()
    result = 31 * result + shortcodes.hashCode()
    result = 31 * result + variants.hashCode()
    return result
  }
}
