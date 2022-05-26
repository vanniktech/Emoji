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
import android.util.LruCache
import com.vanniktech.emoji.Emoji
import com.vanniktech.emoji.EmojiCategory
import com.vanniktech.emoji.EmojiDrawableProvider
import com.vanniktech.emoji.EmojiProvider
import com.vanniktech.emoji.ios.category.ActivitiesCategory
import com.vanniktech.emoji.ios.category.AnimalsAndNatureCategory
import com.vanniktech.emoji.ios.category.FlagsCategory
import com.vanniktech.emoji.ios.category.FoodAndDrinkCategory
import com.vanniktech.emoji.ios.category.ObjectsCategory
import com.vanniktech.emoji.ios.category.SmileysAndPeopleCategory
import com.vanniktech.emoji.ios.category.SymbolsCategory
import com.vanniktech.emoji.ios.category.TravelAndPlacesCategory
import java.lang.ref.SoftReference

class IosEmojiProvider : EmojiProvider, EmojiDrawableProvider {
  override val categories: Array<EmojiCategory>
    get() = arrayOf(
      SmileysAndPeopleCategory(),
      AnimalsAndNatureCategory(),
      FoodAndDrinkCategory(),
      ActivitiesCategory(),
      TravelAndPlacesCategory(),
      ObjectsCategory(),
      SymbolsCategory(),
      FlagsCategory(),
    )

  override fun getDrawable(emoji: Emoji, context: Context): Drawable {
    require(emoji is IosEmoji) { "emoji needs to be of type IosEmoji" }
    val x = emoji.x
    val y = emoji.y
    val key = Point(x, y)
    val bitmap = BITMAP_CACHE[key]
    if (bitmap != null) {
      return BitmapDrawable(context.resources, bitmap)
    }
    val strip = loadStrip(x, context)
    val cut = Bitmap.createBitmap(strip!!, 1, y * SPRITE_SIZE_INC_BORDER + 1, SPRITE_SIZE, SPRITE_SIZE)
    BITMAP_CACHE.put(key, cut)
    return BitmapDrawable(context.resources, cut)
  }

  private fun loadStrip(x: Int, context: Context?): Bitmap? {
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

  override fun release() {
    synchronized(LOCK) {
      BITMAP_CACHE.evictAll()
      for (i in 0 until NUM_STRIPS) {
        val softReference = STRIP_REFS[i]
        (softReference?.get() as Bitmap?)?.recycle()
        softReference?.clear()
      }
    }
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
