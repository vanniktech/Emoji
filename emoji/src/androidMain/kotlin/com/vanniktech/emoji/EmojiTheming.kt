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

import android.content.Context
import androidx.annotation.ColorInt
import com.vanniktech.emoji.internal.Utils

/** Control the colors of all Emoji UI components. */
@Parcelize data class EmojiTheming(
  @JvmField @ColorInt val backgroundColor: Int,
  @JvmField @ColorInt val primaryColor: Int,
  @JvmField @ColorInt val secondaryColor: Int,
  @JvmField @ColorInt val dividerColor: Int,
  @JvmField @ColorInt val textColor: Int,
  @JvmField @ColorInt val textSecondaryColor: Int,
) : Parcelable {
  companion object {
    fun from(context: Context) = EmojiTheming(
      backgroundColor = Utils.resolveColor(context, R.attr.emojiBackgroundColor, R.color.emoji_background_color),
      primaryColor = Utils.resolveColor(context, android.R.attr.colorPrimary, R.color.emoji_primary_color),
      secondaryColor = Utils.resolveColor(context, android.R.attr.colorAccent, R.color.emoji_secondary_color),
      dividerColor = Utils.resolveColor(context, R.attr.emojiDividerColor, R.color.emoji_divider_color),
      textColor = Utils.resolveColor(context, R.attr.emojiTextColor, R.color.emoji_text_color),
      textSecondaryColor = Utils.resolveColor(context, R.attr.emojiTextSecondaryColor, R.color.emoji_text_secondary_color),
    )
  }
}
