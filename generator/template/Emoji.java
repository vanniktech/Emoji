package com.vanniktech.emoji.<%= package %>;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;

public class <%= name %> extends Emoji {
  private static final Object lock = new Object();
  private static Bitmap sheet = null;

  private final int x;
  private final int y;

  public <%= name %>(@NonNull int[] codePoints, int x, int y) {
    super(codePoints, -1);

    this.x = x;
    this.y = y;
  }

  public <%= name %>(int codePoint, int x, int y) {
    super(codePoint, -1);

    this.x = x;
    this.y = y;
  }

  public <%= name %>(int codePoint, int x, int y, Emoji... variants) {
    super(codePoint, -1, variants);

    this.x = x;
    this.y = y;
  }

  public <%= name %>(@NonNull int[] codePoints, int x, int y, Emoji... variants) {
    super(codePoints, -1, variants);

    this.x = x;
    this.y = y;
  }

  @NonNull @Override public Drawable getDrawable(Context context) {
    synchronized (lock) {
      if (sheet == null) {
        sheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.emoji_<%= package %>_sheet);
      }
    }

    final Bitmap cut = Bitmap.createBitmap(sheet, x * 66, y * 66, 64, 64);

    return new BitmapDrawable(context.getResources(), cut);
  }

  @Override public void destroy() {
    if (sheet != null) {
      synchronized (lock) {
        if (sheet != null) {
          sheet.recycle();
          sheet = null;
        }
      }
    }
  }
}
