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

package com.vanniktech.emoji.ios

import com.vanniktech.emoji.Emoji
import com.vanniktech.emoji.Parcelable
import com.vanniktech.emoji.Parcelize
import kotlinx.parcelize.IgnoredOnParcel

@Parcelize internal class IosEmoji internal constructor(
  override val unicode: String,
  override val shortcodes: List<String>,
  internal val x: Int,
  internal val y: Int,
  override val isDuplicate: Boolean,
  override val variants: List<IosEmoji> = emptyList(),
  private var parent: IosEmoji? = null,
) : Emoji, Parcelable {
  @IgnoredOnParcel override val base by lazy(LazyThreadSafetyMode.NONE) {
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

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as IosEmoji

    if (unicode != other.unicode) return false
    if (shortcodes != other.shortcodes) return false
    if (x != other.x) return false
    if (y != other.y) return false
    if (isDuplicate != other.isDuplicate) return false
    if (variants != other.variants) return false

    return true
  }

  override fun hashCode(): Int {
    var result = unicode.hashCode()
    result = 31 * result + shortcodes.hashCode()
    result = 31 * result + x
    result = 31 * result + y
    result = 31 * result + isDuplicate.hashCode()
    result = 31 * result + variants.hashCode()
    return result
  }

  override fun toString(): String {
    return "IosEmoji(unicode='$unicode', shortcodes=$shortcodes, x=$x, y=$y, isDuplicate=$isDuplicate, variants=$variants)"
  }
}
