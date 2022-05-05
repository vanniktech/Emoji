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

package com.vanniktech.emoji.internal

import android.content.Context
import android.util.TypedValue
import com.vanniktech.emoji.EmojiTheming
import com.vanniktech.emoji.R

internal fun EmojiTheming.backgroundColor(context: Context) = when {
  backgroundColor != null -> backgroundColor
  else -> Utils.resolveColor(context, R.attr.emojiBackgroundColor, R.color.emoji_background_color)
}

internal fun EmojiTheming.primaryColor(context: Context) = when {
  primaryColor != null -> primaryColor
  else -> {
    val value = TypedValue()
    context.theme.resolveAttribute(R.attr.colorPrimary, value, true)
    value.data
  }
}

internal fun EmojiTheming.secondaryColor(context: Context) = when {
  secondaryColor != null -> secondaryColor
  else -> {
    val value = TypedValue()
    context.theme.resolveAttribute(R.attr.colorAccent, value, true)
    value.data
  }
}

internal fun EmojiTheming.dividerColor(context: Context) = when {
  dividerColor != null -> dividerColor
  else -> Utils.resolveColor(context, R.attr.emojiDividerColor, R.color.emoji_divider_color)
}

internal fun EmojiTheming.textColor(context: Context) = when {
  textColor != null -> textColor
  else -> Utils.resolveColor(context, R.attr.emojiTextColor, R.color.emoji_text_color)
}

internal fun EmojiTheming.textSecondaryColor(context: Context) = when {
  textSecondaryColor != null -> textSecondaryColor
  else -> Utils.resolveColor(context, R.attr.emojiTextSecondaryColor, R.color.emoji_text_secondary_color)
}
