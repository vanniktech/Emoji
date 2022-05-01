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
 *
 */

package com.vanniktech.emoji.ios;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.util.LruCache;

import com.vanniktech.emoji.emoji.CacheKey;
import com.vanniktech.emoji.emoji.Emoji;

import java.lang.ref.SoftReference;

public class IosEmoji extends Emoji {
  private static final int CACHE_SIZE = 100;
  private static final int SPRITE_SIZE = 64;
  private static final int SPRITE_SIZE_INC_BORDER = 66;
  private static final int NUM_STRIPS = 60;

  private static final Object LOCK = new Object();

  private static final SoftReference[] STRIP_REFS = new SoftReference[NUM_STRIPS];
  private static final LruCache<CacheKey, Bitmap> BITMAP_CACHE = new LruCache<>(CACHE_SIZE);

  static {
    for (int i = 0; i < NUM_STRIPS; i++) {
      STRIP_REFS[i] = new SoftReference<Bitmap>(null);
    }
  }

  private final int x;
  private final int y;

  public IosEmoji(@NonNull final int[] codePoints, @NonNull final String[] shortcodes, final int x, final int y,
                     final boolean isDuplicate) {
    super(codePoints, shortcodes, -1, isDuplicate);

    this.x = x;
    this.y = y;
  }

  public IosEmoji(final int codePoint, @NonNull final String[] shortcodes, final int x, final int y,
                     final boolean isDuplicate) {
    super(codePoint, shortcodes, -1, isDuplicate);

    this.x = x;
    this.y = y;
  }

  public IosEmoji(final int codePoint, @NonNull final String[] shortcodes, final int x, final int y,
                     final boolean isDuplicate, final Emoji... variants) {
    super(codePoint, shortcodes, -1, isDuplicate, variants);

    this.x = x;
    this.y = y;
  }

  public IosEmoji(@NonNull final int[] codePoints, @NonNull final String[] shortcodes, final int x, final int y,
                     final boolean isDuplicate, final Emoji... variants) {
    super(codePoints, shortcodes, -1, isDuplicate, variants);

    this.x = x;
    this.y = y;
  }

  @Override @NonNull public Drawable getDrawable(final Context context) {
    final CacheKey key = new CacheKey(x, y);
    final Bitmap bitmap = BITMAP_CACHE.get(key);
    if (bitmap != null) {
      return new BitmapDrawable(context.getResources(), bitmap);
    }
    final Bitmap strip = loadStrip(context);
    final Bitmap cut = Bitmap.createBitmap(strip, 1, y * SPRITE_SIZE_INC_BORDER + 1, SPRITE_SIZE, SPRITE_SIZE);
    BITMAP_CACHE.put(key, cut);
    return new BitmapDrawable(context.getResources(), cut);
  }

  private Bitmap loadStrip(final Context context) {
    Bitmap strip = (Bitmap) STRIP_REFS[x].get();
    if (strip == null) {
      synchronized (LOCK) {
        strip = (Bitmap) STRIP_REFS[x].get();
        if (strip == null) {
          final Resources resources = context.getResources();
          final int resId = resources.getIdentifier("emoji_ios_sheet_" + x,
              "drawable", context.getPackageName());
          strip = BitmapFactory.decodeResource(resources, resId);
          STRIP_REFS[x] = new SoftReference<>(strip);
        }
      }
    }
    return strip;
  }

  @Override public void destroy() {
    synchronized (LOCK) {
      BITMAP_CACHE.evictAll();
      for (int i = 0; i < NUM_STRIPS; i++) {
        final Bitmap strip = (Bitmap) STRIP_REFS[i].get();
        if (strip != null) {
          strip.recycle();
          STRIP_REFS[i].clear();
        }
      }
    }
  }
}
