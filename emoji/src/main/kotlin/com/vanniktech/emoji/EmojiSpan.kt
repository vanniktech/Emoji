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

package com.vanniktech.emoji

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.text.style.DynamicDrawableSpan
import com.vanniktech.emoji.emoji.Emoji
import kotlin.math.abs
import kotlin.math.roundToInt

internal class EmojiSpan(
  private val context: Context,
  private val emoji: Emoji,
  private val size: Float,
) : DynamicDrawableSpan() {
  private val deferredDrawable by lazy(LazyThreadSafetyMode.NONE) {
    val drawable = emoji.getDrawable(context)
    drawable.setBounds(0, 0, size.toInt(), size.toInt())
    drawable
  }

  override fun getDrawable() = deferredDrawable

  override fun getSize(
    paint: Paint,
    text: CharSequence,
    start: Int,
    end: Int,
    fontMetrics: FontMetricsInt?,
  ): Int {
    if (fontMetrics != null) {
      val paintFontMetrics = paint.fontMetrics
      val ascent = paintFontMetrics.ascent
      val descent = paintFontMetrics.descent
      val targetSize = abs(ascent) + abs(descent)
      val roundEmojiSize = size.roundToInt()
      // equal size use default font metrics.
      if (roundEmojiSize == targetSize.roundToInt()) {
        fontMetrics.ascent = ascent.toInt()
        fontMetrics.descent = descent.toInt()
        fontMetrics.top = paintFontMetrics.top.toInt()
        fontMetrics.bottom = paintFontMetrics.bottom.toInt()
      } else {
        val fontHeight = paintFontMetrics.descent - paintFontMetrics.ascent
        val centerY = paintFontMetrics.ascent + fontHeight / 2
        fontMetrics.ascent = (centerY - size / 2).toInt()
        fontMetrics.top = fontMetrics.ascent
        fontMetrics.bottom = (centerY + size / 2).toInt()
        fontMetrics.descent = fontMetrics.bottom
      }
    }
    return size.toInt()
  }

  override fun draw(
    canvas: Canvas,
    text: CharSequence,
    start: Int,
    end: Int,
    x: Float,
    top: Int,
    y: Int,
    bottom: Int,
    paint: Paint
  ) {
    val drawable = drawable
    val paintFontMetrics = paint.fontMetrics
    val fontHeight = paintFontMetrics.descent - paintFontMetrics.ascent
    val centerY = y + paintFontMetrics.descent - fontHeight / 2
    val transitionY = centerY - size / 2
    canvas.save()
    canvas.translate(x, transitionY)
    drawable.draw(canvas)
    canvas.restore()
  }
}
