package com.vanniktech.emoji;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;
import java.util.Collection;

/**
 * Interface for providing some custom implementation for recent emojis
 *
 * @since 0.2.0
 */
public interface RecentEmoji {
  /**
   * returns recent emojis. Could be loaded from a database, shared preferences or just hard
   * coded.<br>
   * This method will be called more than one time hence it is recommended to hold a collection of
   * recent emojis
   *
   * @since 0.2.0
   */
  @NonNull Collection<Emoji> getRecentEmojis();

  /**
   * should add the emoji to the recent ones. After calling this method, {@link #getRecentEmojis()}
   * should return the emoji that was just added
   *
   * @since 0.2.0
   */
  void addEmoji(@NonNull final Emoji emoji);

  /**
   * should persist all emojis
   *
   * @since 0.2.0
   */
  void persist();
}
