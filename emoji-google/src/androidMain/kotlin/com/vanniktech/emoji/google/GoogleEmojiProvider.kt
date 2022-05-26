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

package com.vanniktech.emoji.google

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
import com.vanniktech.emoji.google.category.ActivitiesCategory
import com.vanniktech.emoji.google.category.AnimalsAndNatureCategory
import com.vanniktech.emoji.google.category.FlagsCategory
import com.vanniktech.emoji.google.category.FoodAndDrinkCategory
import com.vanniktech.emoji.google.category.ObjectsCategory
import com.vanniktech.emoji.google.category.SmileysAndPeopleCategory
import com.vanniktech.emoji.google.category.SymbolsCategory
import com.vanniktech.emoji.google.category.TravelAndPlacesCategory
import java.lang.ref.SoftReference

class GoogleEmojiProvider : EmojiProvider, EmojiDrawableProvider {
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

  override fun getIcon(emojiCategory: EmojiCategory): Int = when (emojiCategory) {
    is SmileysAndPeopleCategory -> R.drawable.emoji_google_category_smileysandpeople
    is AnimalsAndNatureCategory -> R.drawable.emoji_google_category_animalsandnature
    is FoodAndDrinkCategory -> R.drawable.emoji_google_category_foodanddrink
    is ActivitiesCategory -> R.drawable.emoji_google_category_activities
    is TravelAndPlacesCategory -> R.drawable.emoji_google_category_travelandplaces
    is ObjectsCategory -> R.drawable.emoji_google_category_objects
    is SymbolsCategory -> R.drawable.emoji_google_category_symbols
    is FlagsCategory -> R.drawable.emoji_google_category_flags
    else -> error("Unknown $emojiCategory")
  }

  override fun getDrawable(emoji: Emoji, context: Context): Drawable {
    require(emoji is GoogleEmoji) { "emoji needs to be of type GoogleEmoji" }
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
          strip = BitmapFactory.decodeResource(resources, SHEETS[x])
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
    private val SHEETS = listOf(
      R.drawable.emoji_google_sheet_0,
      R.drawable.emoji_google_sheet_1,
      R.drawable.emoji_google_sheet_2,
      R.drawable.emoji_google_sheet_3,
      R.drawable.emoji_google_sheet_4,
      R.drawable.emoji_google_sheet_5,
      R.drawable.emoji_google_sheet_6,
      R.drawable.emoji_google_sheet_7,
      R.drawable.emoji_google_sheet_8,
      R.drawable.emoji_google_sheet_9,
      R.drawable.emoji_google_sheet_10,
      R.drawable.emoji_google_sheet_11,
      R.drawable.emoji_google_sheet_12,
      R.drawable.emoji_google_sheet_13,
      R.drawable.emoji_google_sheet_14,
      R.drawable.emoji_google_sheet_15,
      R.drawable.emoji_google_sheet_16,
      R.drawable.emoji_google_sheet_17,
      R.drawable.emoji_google_sheet_18,
      R.drawable.emoji_google_sheet_19,
      R.drawable.emoji_google_sheet_20,
      R.drawable.emoji_google_sheet_21,
      R.drawable.emoji_google_sheet_22,
      R.drawable.emoji_google_sheet_23,
      R.drawable.emoji_google_sheet_24,
      R.drawable.emoji_google_sheet_25,
      R.drawable.emoji_google_sheet_26,
      R.drawable.emoji_google_sheet_27,
      R.drawable.emoji_google_sheet_28,
      R.drawable.emoji_google_sheet_29,
      R.drawable.emoji_google_sheet_30,
      R.drawable.emoji_google_sheet_31,
      R.drawable.emoji_google_sheet_32,
      R.drawable.emoji_google_sheet_33,
      R.drawable.emoji_google_sheet_34,
      R.drawable.emoji_google_sheet_35,
      R.drawable.emoji_google_sheet_36,
      R.drawable.emoji_google_sheet_37,
      R.drawable.emoji_google_sheet_38,
      R.drawable.emoji_google_sheet_39,
      R.drawable.emoji_google_sheet_40,
      R.drawable.emoji_google_sheet_41,
      R.drawable.emoji_google_sheet_42,
      R.drawable.emoji_google_sheet_43,
      R.drawable.emoji_google_sheet_44,
      R.drawable.emoji_google_sheet_45,
      R.drawable.emoji_google_sheet_46,
      R.drawable.emoji_google_sheet_47,
      R.drawable.emoji_google_sheet_48,
      R.drawable.emoji_google_sheet_49,
      R.drawable.emoji_google_sheet_50,
      R.drawable.emoji_google_sheet_51,
      R.drawable.emoji_google_sheet_52,
      R.drawable.emoji_google_sheet_53,
      R.drawable.emoji_google_sheet_54,
      R.drawable.emoji_google_sheet_55,
      R.drawable.emoji_google_sheet_56,
      R.drawable.emoji_google_sheet_57,
      R.drawable.emoji_google_sheet_58,
      R.drawable.emoji_google_sheet_59,
    )

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
