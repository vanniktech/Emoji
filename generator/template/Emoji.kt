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

package com.vanniktech.emoji.<%= package %>

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.LruCache
import com.vanniktech.emoji.emoji.Emoji
import java.lang.ref.SoftReference

internal class <%= name %> internal constructor(
  codePoints: IntArray,
  override val shortcodes: Array<String>,
  private val x: Int,
  private val y: Int,
  override val isDuplicate: Boolean,
  override vararg val variants: <%= name %>,
) : Emoji {
  override val unicode: String = String(codePoints, 0, codePoints.size)

  private var parent: <%= name %>? = null

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

  override fun getDrawable(context: Context): Drawable {
    val key = Point(x, y)
    val bitmap = BITMAP_CACHE[key]
    if (bitmap != null) {
      return BitmapDrawable(context.resources, bitmap)
    }
    val strip = loadStrip(context)
    val cut = Bitmap.createBitmap(strip!!, 1, y * SPRITE_SIZE_INC_BORDER + 1, SPRITE_SIZE, SPRITE_SIZE)
    BITMAP_CACHE.put(key, cut)
    return BitmapDrawable(context.resources, cut)
  }

  private fun loadStrip(context: Context?): Bitmap? {
    var strip = STRIP_REFS[x]?.get() as Bitmap?
    if (strip == null) {
      synchronized(LOCK) {
        strip = STRIP_REFS[x]?.get() as Bitmap?
        if (strip == null) {
          val resources = context!!.resources
          val resId = resources.getIdentifier("emoji_<%= package %>_sheet_$x", "drawable", context.packageName)
          strip = BitmapFactory.decodeResource(resources, resId)
          STRIP_REFS[x] = SoftReference(strip)
        }
      }
    }

    return strip
  }

  override fun destroy() {
    synchronized(LOCK) {
      BITMAP_CACHE.evictAll()
      for (i in 0 until NUM_STRIPS) {
        val softReference = STRIP_REFS[i]
        (softReference?.get() as Bitmap?)?.recycle()
        softReference?.clear()
      }
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }
    val emoji = other as <%= name %>
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

  private companion object {
    private const val CACHE_SIZE = 100
    private const val SPRITE_SIZE = 64
    private const val SPRITE_SIZE_INC_BORDER = 66
    private const val NUM_STRIPS = <%= strips %>
    private val LOCK = Any()
    private val STRIP_REFS: Array<SoftReference<*>?> = arrayOfNulls(NUM_STRIPS)
    private val BITMAP_CACHE = LruCache<Point, Bitmap>(CACHE_SIZE)

    init {
      for (i in 0 until NUM_STRIPS) {
        STRIP_REFS[i] = SoftReference<Bitmap?>(null)
      }
    }
  }
}
