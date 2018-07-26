package com.vanniktech.emoji.<%= package %>;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vanniktech.emoji.emoji.Emoji;

import java.lang.ref.SoftReference;

public class <%= name %> extends Emoji {
  private static final Object LOCK = new Object();
  private static final int NUM_STRIPS = <%= strips %>;
  private static final SoftReference[] STRIP_REFS =
      new SoftReference[NUM_STRIPS];

  static {
    for (int i = 0; i < NUM_STRIPS; i++)
      STRIP_REFS[i] = new SoftReference<Bitmap>(null);
  }

  private final int x;
  private final int y;

  public <%= name %>(@NonNull final int[] codePoints, final int x, final int y) {
    super(codePoints, -1);

    this.x = x;
    this.y = y;
  }

  public <%= name %>(final int codePoint, final int x, final int y) {
    super(codePoint, -1);

    this.x = x;
    this.y = y;
  }

  public <%= name %>(final int codePoint, final int x, final int y, final Emoji... variants) {
    super(codePoint, -1, variants);

    this.x = x;
    this.y = y;
  }

  public <%= name %>(@NonNull final int[] codePoints, final int x, final int y, final Emoji... variants) {
    super(codePoints, -1, variants);

    this.x = x;
    this.y = y;
  }

  @NonNull @Override public Drawable getDrawable(final Context context) {
    Bitmap strip = loadStrip(context);
    Bitmap cut = Bitmap.createBitmap(strip, 1, y * 66 + 1, 64, 64);
    return new BitmapDrawable(context.getResources(), cut);
  }

  private Bitmap loadStrip(final Context context) {
    Bitmap strip = (Bitmap) STRIP_REFS[x].get();
    if (strip == null) {
      synchronized (LOCK) {
        strip = (Bitmap) STRIP_REFS[x].get();
        if (strip == null) {
          Log.i(getClass().getSimpleName(), "Loading strip " + x);
          Resources resources = context.getResources();
          int resId = resources.getIdentifier("emoji_<%= package %>_sheet_" + x,
              "drawable", context.getPackageName());
          strip = BitmapFactory.decodeResource(resources, resId);
          STRIP_REFS[x] = new SoftReference<>(strip);
        }
      }
    }
    return strip;
  }
}
