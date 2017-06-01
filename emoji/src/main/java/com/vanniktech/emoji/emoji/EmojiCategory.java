package com.vanniktech.emoji.emoji;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

/**
 * Interface for defining a category.
 *
 * @since 0.4.0
 */
public interface EmojiCategory {
  /**
   * Returns all of the emojis it can display.
   *
   * @since 0.4.0
   */
  @NonNull Emoji[] getEmojis();

  /**
   * Returns the icon of the category that should be displayed.
   *
   * @since 0.4.0
   */
  @DrawableRes int getIcon();
}
