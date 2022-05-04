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
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import android.view.View.OnLongClickListener
import androidx.appcompat.widget.AppCompatImageView
import com.vanniktech.emoji.emoji.Emoji
import com.vanniktech.emoji.listeners.OnEmojiClickListener
import com.vanniktech.emoji.listeners.OnEmojiLongClickListener

class EmojiImageView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
) : AppCompatImageView(context, attrs) {
  private var currentEmoji: Emoji? = null
  internal var clickListener: OnEmojiClickListener? = null
  internal var longClickListener: OnEmojiLongClickListener? = null
  private val variantIndicatorPaint = Paint().apply {
    style = Paint.Style.FILL
    isAntiAlias = true
  }
  private val variantIndicatorPath = Path()
  private val variantIndicatorTop = Point()
  private val variantIndicatorBottomRight = Point()
  private val variantIndicatorBottomLeft = Point()
  private var hasVariants = false
  private var imageLoadingTask: ImageLoadingTask? = null

  public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    val measuredWidth = measuredWidth
    setMeasuredDimension(measuredWidth, measuredWidth)
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    variantIndicatorTop.x = w
    variantIndicatorTop.y = h / VARIANT_INDICATOR_PART_AMOUNT * VARIANT_INDICATOR_PART
    variantIndicatorBottomRight.x = w
    variantIndicatorBottomRight.y = h
    variantIndicatorBottomLeft.x = w / VARIANT_INDICATOR_PART_AMOUNT * VARIANT_INDICATOR_PART
    variantIndicatorBottomLeft.y = h
    variantIndicatorPath.rewind()
    variantIndicatorPath.moveTo(variantIndicatorTop.x.toFloat(), variantIndicatorTop.y.toFloat())
    variantIndicatorPath.lineTo(variantIndicatorBottomRight.x.toFloat(), variantIndicatorBottomRight.y.toFloat())
    variantIndicatorPath.lineTo(variantIndicatorBottomLeft.x.toFloat(), variantIndicatorBottomLeft.y.toFloat())
    variantIndicatorPath.close()
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    if (hasVariants && drawable != null) {
      canvas.drawPath(variantIndicatorPath, variantIndicatorPaint)
    }
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    cancelFuture()
  }

  private fun cancelFuture() {
    imageLoadingTask?.cancel()
    imageLoadingTask = null
  }

  fun setEmoji(theming: EmojiTheming, emoji: Emoji, variantEmoji: VariantEmoji?) {
    val context = context
    variantIndicatorPaint.color = theming.dividerColor(context)
    postInvalidate()

    if (emoji != currentEmoji) {
      setImageDrawable(null)
      currentEmoji = emoji
      hasVariants = emoji.base.hasVariants() && variantEmoji !is NoVariantEmoji
      cancelFuture()
      setOnClickListener {
        clickListener?.onEmojiClick(currentEmoji!!)
      }
      setOnLongClickListener(
        when {
          hasVariants -> OnLongClickListener {
            longClickListener?.onEmojiLongClick(this, emoji)
            true
          }
          else -> null
        }
      )

      imageLoadingTask = ImageLoadingTask(this)
      imageLoadingTask?.start(emoji)
    }
  }

  /**
   * Updates the emoji image directly. This should be called only for updating the variant
   * displayed (of the same base emoji), since it does not run asynchronously and does not update
   * the internal listeners.
   *
   * @param emoji The new emoji variant to show.
   */
  fun updateEmoji(emoji: Emoji) {
    if (emoji != currentEmoji) {
      currentEmoji = emoji
      setImageDrawable(emoji.getDrawable(context))
    }
  }

  internal companion object {
    private const val VARIANT_INDICATOR_PART_AMOUNT = 6
    private const val VARIANT_INDICATOR_PART = 5
  }
}
