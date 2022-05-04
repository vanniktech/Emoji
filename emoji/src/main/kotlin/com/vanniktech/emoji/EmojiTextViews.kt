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
