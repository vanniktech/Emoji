package com.vanniktech.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.text.style.DynamicDrawableSpan;

final class EmojiSpan extends DynamicDrawableSpan {
  private final Context context;
  private final int resourceId;
  private final int size;

  private Drawable drawable;

  EmojiSpan(@NonNull final Context context, @DrawableRes final int resourceId, final int size) {
    this.context = context;
    this.resourceId = resourceId;
    this.size = size;
  }

  @Override public Drawable getDrawable() {
    if (drawable == null) {
      drawable = AppCompatResources.getDrawable(context, resourceId);
      //noinspection ConstantConditions
      drawable.setBounds(0, 0, size, size);
    }

    return drawable;
  }
}
