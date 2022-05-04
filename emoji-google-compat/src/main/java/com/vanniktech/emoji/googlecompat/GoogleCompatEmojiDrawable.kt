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
package com.vanniktech.emoji.googlecompat

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.text.Spanned
import android.text.TextPaint
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.EmojiSpan
import kotlin.math.roundToInt

/**
 * An emoji drawable backed by a span generated by the Google emoji support library.
 */
internal class GoogleCompatEmojiDrawable(
  unicode: String,
) : Drawable() {
  private var emojiSpan: EmojiSpan? = null
  private var processed = false
  private var emojiCharSequence: CharSequence = unicode
  private val textPaint = TextPaint().apply {
    style = Paint.Style.FILL
    color = -0x1
    isAntiAlias = true
  }

  private fun process() {
    emojiCharSequence = EmojiCompat.get().process(emojiCharSequence)
    if (emojiCharSequence is Spanned) {
      val spans = (emojiCharSequence as Spanned).getSpans(0, emojiCharSequence.length, EmojiSpan::class.java)

      if (spans.isNotEmpty()) {
        emojiSpan = spans[0] as EmojiSpan
      }
    }
  }

  override fun draw(canvas: Canvas) {
    val bounds = bounds
    textPaint.textSize = bounds.height() * TEXT_SIZE_FACTOR
    val y = (bounds.bottom - bounds.height() * BASELINE_OFFSET_FACTOR).roundToInt()

    if (!processed && EmojiCompat.get().loadState != EmojiCompat.LOAD_STATE_LOADING) {
      processed = true
      if (EmojiCompat.get().loadState != EmojiCompat.LOAD_STATE_FAILED) {
        process()
      }
    }

    if (emojiSpan == null) {
      canvas.drawText(emojiCharSequence, 0, emojiCharSequence.length, bounds.left.toFloat(), y.toFloat(), textPaint)
    } else {
      emojiSpan!!.draw(canvas, emojiCharSequence, 0, emojiCharSequence.length, bounds.left.toFloat(), bounds.top, y, bounds.bottom, textPaint)
    }
  }

  override fun setAlpha(alpha: Int) {
    textPaint.alpha = alpha
  }

  override fun setColorFilter(colorFilter: ColorFilter?) {
    textPaint.colorFilter = colorFilter
  }

  @Suppress("OVERRIDE_DEPRECATION")
  override fun getOpacity(): Int {
    return PixelFormat.UNKNOWN
  }

  internal companion object {
    private const val TEXT_SIZE_FACTOR = 0.8f
    private const val BASELINE_OFFSET_FACTOR = 0.225f
  }
}
