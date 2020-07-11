/*
 * Copyright (C) 2016 - Niklas Baudy, Ruben Gees, Mario Đanić and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.vanniktech.emoji;

import androidx.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;
import java.util.Collection;

/**
 * Interface for providing some custom implementation for recent emojis.
 *
 * @since 0.2.0
 */
public interface RecentEmoji {
  /**
   * Returns the recent emojis. Could be loaded from a database, shared preferences or just hard
   * coded.<br>
   *
   * This method will be called more than one time hence it is recommended to hold a collection of
   * recent emojis.
   *
   * @since 0.2.0
   */
  @NonNull Collection<Emoji> getRecentEmojis();

  /**
   * Should add the emoji to the recent ones. After calling this method, {@link #getRecentEmojis()}
   * should return the emoji that was just added.
   *
   * @since 0.2.0
   */
  void addEmoji(@NonNull Emoji emoji);

  /**
   * Should persist all emojis.
   *
   * @since 0.2.0
   */
  void persist();
}
