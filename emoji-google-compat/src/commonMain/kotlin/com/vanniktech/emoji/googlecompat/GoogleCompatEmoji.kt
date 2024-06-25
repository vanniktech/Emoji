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

import com.vanniktech.emoji.Emoji

internal class GoogleCompatEmoji internal constructor(
  override val unicode: String,
  override val shortcodes: List<String>,
  override val variants: List<GoogleCompatEmoji> = emptyList(),
  private var parent: GoogleCompatEmoji? = null,
) : Emoji {
  override val base by lazy(LazyThreadSafetyMode.NONE) {
    var result = this
    while (result.parent != null) {
      result = result.parent!!
    }
    result
  }

  init {
    for (variant in variants) {
      variant.parent = this
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as GoogleCompatEmoji

    if (unicode != other.unicode) return false
    if (shortcodes != other.shortcodes) return false
    if (variants != other.variants) return false

    return true
  }

  override fun toString() = "GoogleCompatEmoji(unicode='$unicode', shortcodes=$shortcodes, variants=$variants)"

  override fun hashCode(): Int {
    var result = unicode.hashCode()
    result = 31 * result + shortcodes.hashCode()
    result = 31 * result + variants.hashCode()
    return result
  }
}
