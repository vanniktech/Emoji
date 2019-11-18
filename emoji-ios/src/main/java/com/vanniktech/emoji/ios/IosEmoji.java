package com.vanniktech.emoji.ios;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.vanniktech.emoji.emoji.CacheKey;
import com.vanniktech.emoji.emoji.Emoji;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class IosEmoji extends Emoji {
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

  public IosEmoji(@NonNull final int[] codePoints, final int x, final int y, final boolean isDuplicate) {
    super(codePoints, -1, isDuplicate);

    this.x = x;
    this.y = y;
  }

  public IosEmoji(final int codePoint, final int x, final int y, final boolean isDuplicate) {
    super(codePoint, -1, isDuplicate);

    this.x = x;
    this.y = y;
  }

  public IosEmoji(final int codePoint, final int x, final int y, final boolean isDuplicate,
                     final Emoji... variants) {
    super(codePoint, -1, isDuplicate, variants);

    this.x = x;
    this.y = y;
  }

  public IosEmoji(@NonNull final int[] codePoints, final int x, final int y, final boolean isDuplicate,
                     final Emoji... variants) {
    super(codePoints, -1, isDuplicate, false, variants);

    this.x = x;
    this.y = y;
  }

  public IosEmoji(@NonNull final int[] codePoints, final int x, final int y, final boolean isDuplicate,
                  final boolean isAnimated, final Emoji... variants) {
    super(codePoints, -1, isDuplicate, isAnimated, variants);

    this.x = x;
    this.y = y;
  }

  @Override @NonNull public Drawable getDrawable(final Context context) {

    final CacheKey key = new CacheKey(x, y);
    final Bitmap bitmap = BITMAP_CACHE.get(key);

    // TODO: Implement returning bitmapdrawable if existing based on one or several bitmaps existing
    // if (bitmap != null) {
    //  return new BitmapDrawable(context.getResources(), bitmap);
    // }

    return getDrawable(context, key);
  }

  private Drawable getDrawable(final Context context, CacheKey key) {
    final Bitmap strip = loadStrip(context);
    final Bitmap cut = Bitmap.createBitmap(strip, 1, y * SPRITE_SIZE_INC_BORDER + 1, SPRITE_SIZE, SPRITE_SIZE);
    BITMAP_CACHE.put(key, cut);
    return new BitmapDrawable(context.getResources(), cut);
  }

  @Override @NonNull public AnimationDrawable getAnimatedDrawable(final Context context) {
    Log.d("TAG", "Still working in AnimatedDrawable");

    final Bitmap strip = loadStrip(context);
    Log.d("TAG", "Still working after loading strip");
    Log.d("Strip is: ", strip.toString());
    final Bitmap[] cut = loadAnimatedStrip(strip);

    AnimationDrawable animationDrawable = new AnimationDrawable();
    for (Bitmap bitmap : cut) {
      animationDrawable.addFrame(new BitmapDrawable(context.getResources(), bitmap), 400);
    }
    animationDrawable.setOneShot(false);

    System.out.println("Frames: " + animationDrawable.getNumberOfFrames());
    System.out.println("Frames: " + animationDrawable.isRunning());

    return animationDrawable;
  }

  private Bitmap[] loadAnimatedStrip(Bitmap bitmap) {
    final List<Bitmap> bitmapStripList = new ArrayList<>();
    int line;

    for(line = 0; line * SPRITE_SIZE_INC_BORDER < bitmap.getHeight(); line++) {
      bitmapStripList.add(Bitmap.createBitmap(bitmap, 1, line * SPRITE_SIZE_INC_BORDER, SPRITE_SIZE, SPRITE_SIZE));
      Log.d("TAG", bitmapStripList.get(bitmapStripList.size() - 1).toString());
    }

    Bitmap[] bitmapStripArray = new Bitmap[bitmapStripList.size()];
    return bitmapStripList.toArray(bitmapStripArray);
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
