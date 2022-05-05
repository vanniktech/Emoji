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

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.View
import android.view.View.MeasureSpec
import android.view.WindowManager
import android.widget.EditText
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.vanniktech.emoji.EmojiTheming
import com.vanniktech.emoji.R
import com.vanniktech.emoji.emoji.Emoji
import com.vanniktech.emoji.search.SearchEmojiResult

internal fun interface EmojiSearchDelegate {
  fun onEmojiClicked(emoji: Emoji)
}

internal class EmojiSearchPopup(
  private val rootView: View,
  private val editText: EditText,
  private val theming: EmojiTheming,
) {
  private var popupWindow: PopupWindow? = null

  internal fun show(
    emojis: List<SearchEmojiResult>,
    delegate: EmojiSearchDelegate?,
  ) {
    if (emojis.isNotEmpty()) {
      val context = editText.context
      val recyclerView = View.inflate(context, R.layout.emoji_popup_search, null) as RecyclerView
      recyclerView.setBackgroundColor(theming.backgroundColor(context))

      val adapter = EmojiAdapter(
        theming = theming,
        emojiSearchDialogDelegate = {
          delegate?.onEmojiClicked(it)
          dismiss()
        },
      )
      recyclerView.adapter = adapter

      val editTextLocation = Utils.locationOnScreen(editText)
      adapter.update(emojis, marginStart = editTextLocation.x)

      val resources = context.resources
      recyclerView.measure(
        MeasureSpec.makeMeasureSpec(rootView.width, MeasureSpec.EXACTLY),
        0, // Internals will already limit this.
      )

      val height = recyclerView.measuredHeight
      val desiredLocation = Point(
        rootView.x.toInt(),
        editTextLocation.y - height,
      )

      popupWindow = PopupWindow(recyclerView, WindowManager.LayoutParams.MATCH_PARENT, height).apply {
        isFocusable = false
        isOutsideTouchable = true
        inputMethodMode = PopupWindow.INPUT_METHOD_NOT_NEEDED
        setBackgroundDrawable(BitmapDrawable(resources, null as Bitmap?)) // To avoid borders and overdraw.
        showAtLocation(rootView, Gravity.NO_GRAVITY, desiredLocation.x, desiredLocation.y)
        Utils.fixPopupLocation(this, desiredLocation)
      }
    } else {
      dismiss()
    }
  }

  internal fun dismiss() {
    popupWindow?.dismiss()
    popupWindow = null
  }
}
