@file:JvmName("EmojiTextViews")

package com.vanniktech.emoji

import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.Px
import androidx.annotation.StyleableRes

@Px fun TextView.init(
  attrs: AttributeSet?,
  @StyleableRes styleable: IntArray,
  @StyleableRes emojiSizeAttr: Int,
): Float {
  if (!isInEditMode) {
    EmojiManager.verifyInstalled()
  }
  val fontMetrics = paint.fontMetrics
  val defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent
  val emojiSize: Float = if (attrs == null) {
    defaultEmojiSize
  } else {
    val a = context.obtainStyledAttributes(attrs, styleable)
    try {
      a.getDimension(emojiSizeAttr, defaultEmojiSize)
    } finally {
      a.recycle()
    }
  }

  text = text // Reassign.
  return emojiSize
}
