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

@file:JvmName("EmojiThemings")

package com.vanniktech.emoji

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.parcelize.Parcelize

/** Control the colors of all Emoji UI components. */
@Parcelize data class EmojiTheming @JvmOverloads constructor(
  /**
   * If set, it will be taken.
   * Otherwise it'll look for a theme attribute called emojiBackgroundColor.
   * If that isn't found, the library has a default.
   */
  @ColorInt @JvmField val backgroundColor: Int? = null,
  /**
   * If set, it will be taken.
   * Otherwise it'll take the color from your theme.
   */
  @ColorInt @JvmField val primaryColor: Int? = null,
  /**
   * If set, it will be taken.
   * Otherwise it'll take the color from your theme.
   */
  @ColorInt @JvmField val secondaryColor: Int? = null,
  /**
   * If set, it will be taken.
   * Otherwise it'll look for a theme attribute called emojiDividerColor.
   * If that isn't found, the library has a default.
   */
  @ColorInt @JvmField val dividerColor: Int? = null,
  /**
   * If set, it will be taken.
   * Otherwise it'll look for a theme attribute called emojiTextColor.
   * If that isn't found, the library has a default.
   */
  @ColorInt @JvmField val textColor: Int? = null,
  /**
   * If set, it will be taken.
   * Otherwise it'll look for a theme attribute called emojiTextSecondaryColor.
   * If that isn't found, the library has a default.
   */
  @ColorInt @JvmField val textSecondaryColor: Int? = null,
) : Parcelable
