@file:JvmName("EmojiThemings")

package com.vanniktech.emoji

import android.content.Context
import android.os.Parcelable
import android.util.TypedValue
import androidx.annotation.ColorInt
import kotlinx.parcelize.Parcelize

/** Control the colors of all Emoji UI components. */
@Parcelize data class EmojiTheming(
  /**
   * If set, it will be taken.
   * Otherwise it'll look for a theme attribute called emojiBackgroundColor.
   * If that isn't found, the library has a default.
   */
  @ColorInt val backgroundColor: Int?,
  /**
   * If set, it will be taken.
   * Otherwise it'll take the color from your theme.
   */
  @ColorInt val primaryColor: Int?,
  /**
   * If set, it will be taken.
   * Otherwise it'll take the color from your theme.
   */
  @ColorInt val secondaryColor: Int?,
  /**
   * If set, it will be taken.
   * Otherwise it'll look for a theme attribute called emojiDividerColor.
   * If that isn't found, the library has a default.
   */
  @ColorInt val dividerColor: Int?,
  /**
   * If set, it will be taken.
   * Otherwise it'll look for a theme attribute called emojiTextColor.
   * If that isn't found, the library has a default.
   */
  @ColorInt val textColor: Int?,
  /**
   * If set, it will be taken.
   * Otherwise it'll look for a theme attribute called emojiTextSecondaryColor.
   * If that isn't found, the library has a default.
   */
  @ColorInt val textSecondaryColor: Int?,
) : Parcelable {
  constructor() : this(
    backgroundColor = null,
    primaryColor = null,
    secondaryColor = null,
    dividerColor = null,
    textColor = null,
    textSecondaryColor = null,
  )
}

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
