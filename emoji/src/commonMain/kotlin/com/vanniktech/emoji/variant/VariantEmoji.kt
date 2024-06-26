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
 */

package com.vanniktech.emoji.variant

import com.vanniktech.emoji.Emoji

/**
 * Interface for providing some custom implementation for variant emojis.
 *
 * @since 0.5.0
 */
interface VariantEmoji {
  /**
   * Returns the variant for the passed emoji that should be displayed as a preview.
   * Could be loaded from a database, shared preferences or just hard coded.
   *
   * [desiredEmoji] The emoji to retrieve the variant for. If none is found, [desiredEmoji] should be returned.
   * @since 0.5.0
   */
  fun getVariant(desiredEmoji: Emoji): Emoji

  /**
   * Return the supported variants for the given [emoji].
   * In contrast to [getVariant], this defines whether an overlay to pick a Variant will be shown or not.
   * @since 0.20.0
   */
  fun getVariants(emoji: Emoji): List<Emoji>

  /**
   * Should add the emoji to the variants. After calling this method, [getVariant]
   * should return the emoji that was just added.
   *
   * [newVariant] The new variant to save.
   * @since 0.5.0
   */
  fun addVariant(newVariant: Emoji)

  /**
   * Should persist all emojis.
   *
   * @since 0.5.0
   */
  fun persist()
}
