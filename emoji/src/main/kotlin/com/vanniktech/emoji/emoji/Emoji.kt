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

package com.vanniktech.emoji.emoji

import android.content.Context
import android.graphics.drawable.Drawable
import java.io.Serializable

abstract class Emoji(
  codePoints: IntArray,
  val shortcodes: Array<String>,
  val isDuplicate: Boolean,
  vararg val variants: Emoji,
) : Serializable {
  val unicode: String = String(codePoints, 0, codePoints.size)

  private var parent: Emoji? = null

  val base by lazy(LazyThreadSafetyMode.NONE) {
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

  abstract fun getDrawable(context: Context): Drawable
  abstract fun destroy()

  val length: Int
    get() = unicode.length

  fun hasVariants(): Boolean {
    return variants.isNotEmpty()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }
    val emoji = other as Emoji
    return (
      unicode == emoji.unicode && shortcodes.contentEquals(emoji.shortcodes) &&
        variants.contentEquals(emoji.variants)
      )
  }

  override fun hashCode(): Int {
    var result = unicode.hashCode()
    result = 31 * result + shortcodes.contentHashCode()
    result = 31 * result + variants.hashCode()
    return result
  }

  companion object {
    private const val serialVersionUID = 3L
  }
}
