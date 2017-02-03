package com.vanniktech.emoji;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.EmojiCategory;
import java.util.Map;

/**
 * Interface for custom implementation of an emoji providing class.
 */
public interface EmojiProvider {
  /**
   * Returns a map of categories.
   *
   * @return The map of categories.
   */
  @NonNull Map<String, EmojiCategory> getCategories();
}
