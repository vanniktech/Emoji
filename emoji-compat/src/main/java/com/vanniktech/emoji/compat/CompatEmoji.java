package com.vanniktech.emoji.compat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;

public class CompatEmoji extends Emoji {

    public CompatEmoji(int codePoint) {
        super(codePoint, -1);
    }

    public CompatEmoji(@NonNull int[] codePoints) {
        super(codePoints, -1);
    }

    public CompatEmoji(int[] ints, Emoji... emojis) {
        super(ints, -1, emojis);
    }

    public CompatEmoji(int codePoint, Emoji... emojis) {
        super(codePoint, -1, emojis);
    }

    @NonNull
    @Override
    public Drawable getDrawable(Context context) {
        return new CompatEmojiDrawable(context, getUnicode());
    }
}