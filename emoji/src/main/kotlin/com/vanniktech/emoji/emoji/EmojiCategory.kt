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
package com.vanniktech.emoji.emoji

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Interface for defining a category.
 *
 * @since 0.4.0
 */
interface EmojiCategory {
  /**
   * Returns all of the emojis it can display.
   *
   * @since 0.4.0
   */
  val emojis: Array<Emoji>

  /**
   * Returns the icon of the category that should be displayed.
   *
   * @since 0.4.0
   */
  @get:DrawableRes
  val icon: Int

  /**
   * Returns category name.
   *
   * @since 0.7.0
   */
  @get:StringRes
  val categoryName: Int
}
