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
package com.vanniktech.emoji

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.vanniktech.emoji.emoji.Emoji

internal class EmojiVariantPopup internal constructor(
  private val rootView: View,
  private val delegate: EmojiVariantDelegate?,
) {
  private var popupWindow: PopupWindow? = null

  fun show(clickedImage: EmojiImageView, emoji: Emoji) {
    dismiss()

    val context = clickedImage.context
    val content = initView(context, emoji, clickedImage.width, clickedImage)
    content.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
    val location = Utils.locationOnScreen(clickedImage)
    val desiredLocation = Point(
      location.x - content.measuredWidth / 2 + clickedImage.width / 2,
      location.y - content.measuredHeight,
    )

    popupWindow = PopupWindow(content, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT).apply {
      isFocusable = true
      isOutsideTouchable = true
      inputMethodMode = PopupWindow.INPUT_METHOD_NOT_NEEDED
      setBackgroundDrawable(BitmapDrawable(context.resources, null as Bitmap?)) // To avoid borders and overdraw.
      showAtLocation(rootView, Gravity.NO_GRAVITY, desiredLocation.x, desiredLocation.y)
      Utils.fixPopupLocation(this, desiredLocation)
    }

    clickedImage.parent.requestDisallowInterceptTouchEvent(true)
  }

  fun dismiss() {
    popupWindow?.dismiss()
    popupWindow = null
  }

  private fun initView(context: Context, emoji: Emoji, width: Int, clickedImage: EmojiImageView): View {
    val result = View.inflate(context, R.layout.emoji_popup_window_skin, null)
    val imageContainer = result.findViewById<LinearLayout>(R.id.emojiPopupWindowSkinPopupContainer)
    val variants = emoji.base.variants
    variants.add(0, emoji.base)
    val inflater = LayoutInflater.from(context)

    for (variant in variants) {
      val emojiImage = inflater.inflate(R.layout.emoji_adapter_item_emoji, imageContainer, false) as ImageView
      val layoutParams = emojiImage.layoutParams as MarginLayoutParams
      val margin = Utils.dpToPx(context, MARGIN.toFloat())

      // Use the same size for Emojis as in the picker.
      layoutParams.width = width
      layoutParams.setMargins(margin, margin, margin, margin)
      emojiImage.setImageDrawable(variant.getDrawable(context))
      emojiImage.setOnClickListener {
        delegate?.onEmojiClick(clickedImage, variant)
      }
      imageContainer.addView(emojiImage)
    }

    return result
  }

  internal companion object {
    private const val MARGIN = 2
  }
}
