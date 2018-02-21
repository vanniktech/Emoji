package com.vanniktech.emoji.google;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;

public class GoogleEmoji extends Emoji {
  private static final Object LOCK = new Object();
  private static volatile Bitmap sheet;

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
    if (sheet == null) {
      synchronized (LOCK) {
        if (sheet == null) {
          sheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.emoji_google_sheet);
        }
      }
    }

    final Bitmap cut = Bitmap.createBitmap(sheet, x * 66, y * 66, 64, 64);

    return new BitmapDrawable(context.getResources(), cut);
  }

  @Override public void destroy() {
    if (sheet != null) {
      synchronized (LOCK) {
        if (sheet != null) {
          sheet.recycle();
          sheet = null;
        }
      }
    }
  }
}
