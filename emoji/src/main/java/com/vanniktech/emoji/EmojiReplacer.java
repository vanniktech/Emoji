package com.vanniktech.emoji;

import android.content.Context;
import android.text.Spannable;

/**
 * EmojiProviders can implement this interface to perform text emoji image replacement in a more efficient way.
 * For instance, the GooogleCompatEmojiProvider calls the corresponding AppCompat Emoji
 * Support library replace method directly for emoji in the default size.
 *
 * @since 6.0.0
 */
public interface EmojiReplacer {
  void replaceWithImages(Context context, Spannable text, float emojiSize, float defaultEmojiSize, EmojiReplacer fallback);
}
