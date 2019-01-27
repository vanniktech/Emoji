package com.vanniktech.emoji;

import androidx.annotation.NonNull;
import com.vanniktech.emoji.emoji.EmojiCategory;

/**
 * Interface for a custom emoji implementation that can be used with {@link EmojiManager}.
 *
 * @since 0.4.0
 */
public interface EmojiProvider {
  /**
   * @return The Array of categories.
   * @since 0.4.0
   */
  @NonNull EmojiCategory[] getCategories();
}
