package com.vanniktech.emoji.google;

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

public class GoogleEmoji extends Emoji {
  private static final int CACHE_SIZE = 100;
  private static final int SPRITE_SIZE = 64;
  private static final int SPRITE_SIZE_INC_BORDER = 66;
  private static final int NUM_STRIPS = 51;

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

  public GoogleEmoji(@NonNull final int[] codePoints, final int x, final int y, final boolean isDuplicate) {
    super(codePoints, -1, isDuplicate);

    this.x = x;
    this.y = y;
  }

  public GoogleEmoji(final int codePoint, final int x, final int y, final boolean isDuplicate) {
    super(codePoint, -1, isDuplicate);

    this.x = x;
    this.y = y;
  }

  public GoogleEmoji(final int codePoint, final int x, final int y, final boolean isDuplicate,
                     final Emoji... variants) {
    super(codePoint, -1, isDuplicate, variants);

    this.x = x;
    this.y = y;
  }

  public GoogleEmoji(@NonNull final int[] codePoints, final int x, final int y, final boolean isDuplicate,
                     final Emoji... variants) {
    super(codePoints, -1, isDuplicate, variants);

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
          final int resId = resources.getIdentifier("emoji_google_sheet_" + x,
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
