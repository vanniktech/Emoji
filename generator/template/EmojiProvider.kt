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
import com.vanniktech.emoji.Emoji
import com.vanniktech.emoji.EmojiCategory
import com.vanniktech.emoji.EmojiDrawableProvider
import com.vanniktech.emoji.EmojiProvider
<%= imports %>
import java.lang.ref.SoftReference

class <%= name %>Provider : EmojiProvider, EmojiDrawableProvider {
  override val categories: Array<EmojiCategory>
    get() = arrayOf(<% categories.forEach(function(category) { %>
      <%= category.name %>(),<% }); %>
    )

  override fun getIcon(emojiCategory: EmojiCategory): Int = when (emojiCategory) {<% categories.forEach(function(category) { %>
    is <%= category.name %> -> R.drawable.emoji_<%= package %>_category_<%= category.icon %><% }); %>
    else -> error("Unknown $emojiCategory")
  }

  override fun getDrawable(emoji: Emoji, context: Context): Drawable {
    require(emoji is <%= name %>) { "emoji needs to be of type <%= name %>" }
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
    private const val NUM_STRIPS = <%= strips %>
    private val SHEETS = listOf(<% Array(strips).fill().forEach(function(_, index) { %>
      R.drawable.emoji_<%= package %>_sheet_<%= index %>,<% }); %>
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
