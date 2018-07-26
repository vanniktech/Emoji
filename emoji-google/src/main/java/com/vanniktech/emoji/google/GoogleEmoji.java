package com.vanniktech.emoji.google;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vanniktech.emoji.emoji.Emoji;

public class GoogleEmoji extends Emoji {
  private static final Object LOCK = new Object();
  private static final int NUM_STRIPS = 51;
  private static final Bitmap[] STRIPS = new Bitmap[NUM_STRIPS];

  private final int x;
  private final int y;

  public GoogleEmoji(@NonNull final int[] codePoints, final int x, final int y) {
    super(codePoints, -1);

    this.x = x;
    this.y = y;
  }

  public GoogleEmoji(final int codePoint, final int x, final int y) {
    super(codePoint, -1);

    this.x = x;
    this.y = y;
  }

  public GoogleEmoji(final int codePoint, final int x, final int y, final Emoji... variants) {
    super(codePoint, -1, variants);

    this.x = x;
    this.y = y;
  }

  public GoogleEmoji(@NonNull final int[] codePoints, final int x, final int y, final Emoji... variants) {
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
    Bitmap strip = STRIPS[x];
    if (strip == null) {
      synchronized (LOCK) {
        strip = STRIPS[x];
        if (strip == null) {
          Log.i(getClass().getSimpleName(), "Loading strip " + x);
          Resources resources = context.getResources();
          int resId = resources.getIdentifier("emoji_google_sheet_" + x,
              "drawable", context.getPackageName());
          strip = BitmapFactory.decodeResource(resources, resId);
          STRIPS[x] = strip;
        }
      }
    }
    return strip;
  }
}
