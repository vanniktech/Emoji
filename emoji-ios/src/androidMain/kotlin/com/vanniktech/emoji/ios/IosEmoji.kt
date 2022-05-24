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

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.util.LruCache
import com.vanniktech.emoji.Emoji
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.lang.ref.SoftReference

@Parcelize internal class IosEmoji internal constructor(
  override val unicode: String,
  override val shortcodes: List<String>,
  private val x: Int,
  private val y: Int,
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
          val resId = resources.getIdentifier("emoji_ios_sheet_$x", "drawable", context.packageName)
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

  private companion object {
    private const val CACHE_SIZE = 100
    private const val SPRITE_SIZE = 64
    private const val SPRITE_SIZE_INC_BORDER = 66
    private const val NUM_STRIPS = 60
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
