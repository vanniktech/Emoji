package com.vanniktech.emoji.ios.category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.ios.IosEmoji;
import com.vanniktech.emoji.ios.R;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class CustomAnimatedCategory implements EmojiCategory {
    private static final IosEmoji[] DATA = new IosEmoji[]{
            new IosEmoji(new int[] { 0x1F43F, 0xFE0F, 0x1F43F, 0xFE0F }, 1, 0, false, true),
    };

    @Override @NonNull public Emoji[] getEmojis() { return DATA; }

    @Override @DrawableRes
    public int getIcon() {
        return R.drawable.emoji_ios_category_flags;
    }

    @Override
    public int getCategoryName() { return R.string.emoji_ios_category_flags; }
}
