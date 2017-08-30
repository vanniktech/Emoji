package com.vanniktech.emoji;

import android.content.Context;
import android.text.Spannable;

public interface EmojiReplacer {
    void replaceWithImages(Context context, Spannable text, float emojiSize, float defaultEmojiSize);
}
