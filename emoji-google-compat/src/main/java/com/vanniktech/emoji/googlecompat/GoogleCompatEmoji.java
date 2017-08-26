package com.vanniktech.emoji.googlecompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;

public class GoogleCompatEmoji extends Emoji {

    public GoogleCompatEmoji(int codePoint) {
        super(codePoint, -1);
    }

    public GoogleCompatEmoji(@NonNull int[] codePoints) {
        super(codePoints, -1);
    }

    public GoogleCompatEmoji(int[] ints, Emoji... emojis) {
        super(ints, -1, emojis);
    }

    public GoogleCompatEmoji(int codePoint, Emoji... emojis) {
        super(codePoint, -1, emojis);
    }

    @NonNull
    @Override
    public Drawable getDrawable(Context context) {
        return new CompatEmojiDrawable(getUnicode());
    }
}