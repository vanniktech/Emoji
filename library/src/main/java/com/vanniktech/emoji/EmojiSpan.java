package com.vanniktech.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.style.DynamicDrawableSpan;

final class EmojiSpan extends DynamicDrawableSpan {
    private final Context context;
    private final int resourceId;
    private final int size;

    private Drawable drawable;

    EmojiSpan(final Context context, final int resourceId, final int size) {
        this.context = context;
        this.resourceId = resourceId;
        this.size = size;
    }

    @Override
    public Drawable getDrawable() {
        if (drawable == null) {
            drawable = ContextCompat.getDrawable(context, resourceId);
            drawable.setBounds(0, 0, size, size);
        }

        return drawable;
    }
}
