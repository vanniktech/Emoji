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

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Px
import androidx.annotation.StyleableRes
import androidx.core.content.ContextCompat
import com.vanniktech.emoji.EmojiManager.verifyInstalled
import com.vanniktech.emoji.emoji.Emoji
import java.lang.IllegalArgumentException
import java.util.ArrayList
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

private const val DONT_UPDATE_FLAG = -1

object Utils {
  @PrivateApi
  @Px
  fun initTextView(textView: TextView, attrs: AttributeSet?, @StyleableRes stylable: IntArray?, @StyleableRes emojiSizeAttr: Int): Float {
    if (!textView.isInEditMode) {
      verifyInstalled()
    }
    val fontMetrics = textView.paint.fontMetrics
    val defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent
    val emojiSize: Float = if (attrs == null) {
      defaultEmojiSize
    } else {
      val a = textView.context.obtainStyledAttributes(attrs, stylable!!)
      try {
        a.getDimension(emojiSizeAttr, defaultEmojiSize)
      } finally {
        a.recycle()
      }
    }
    textView.text = textView.text
    return emojiSize
  }

  internal fun dpToPx(context: Context, dp: Float): Int {
    return (
      TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp,
        context.resources.displayMetrics
      ) + 0.5f
      ).roundToInt()
  }

  private fun getOrientation(context: Context): Int {
    return context.resources.configuration.orientation
  }

  internal fun getProperWidth(activity: Activity): Int {
    val rect = windowVisibleDisplayFrame(activity)
    return if (getOrientation(activity) == Configuration.ORIENTATION_PORTRAIT) rect.right else getScreenWidth(activity)
  }

  internal fun shouldOverrideRegularCondition(context: Context, editText: EditText): Boolean {
    return if (editText.imeOptions and EditorInfo.IME_FLAG_NO_EXTRACT_UI == 0) {
      getOrientation(context) == Configuration.ORIENTATION_LANDSCAPE
    } else false
  }

  fun getProperHeight(activity: Activity): Int {
    return windowVisibleDisplayFrame(activity).bottom
  }

  private fun getScreenWidth(context: Activity): Int {
    return dpToPx(context, context.resources.configuration.screenWidthDp.toFloat())
  }

  internal fun locationOnScreen(view: View): Point {
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    return Point(location[0], location[1])
  }

  private fun windowVisibleDisplayFrame(context: Activity): Rect {
    val result = Rect()
    context.window.decorView.getWindowVisibleDisplayFrame(result)
    return result
  }

  internal fun asListWithoutDuplicates(emojis: Array<Emoji>): List<Emoji> {
    val result: MutableList<Emoji> = ArrayList(emojis.size)
    for (emoji in emojis) {
      if (!emoji.isDuplicate) {
        result.add(emoji)
      }
    }
    return result
  }

  @PrivateApi
  fun input(editText: EditText, emoji: Emoji) {
    val start = editText.selectionStart
    val end = editText.selectionEnd
    if (start < 0) {
      editText.append(emoji.unicode)
    } else {
      editText.text.replace(min(start, end), max(start, end), emoji.unicode, 0, emoji.unicode.length)
    }
  }

  internal fun asActivity(context: Context): Activity {
    var result: Context? = context
    while (result is ContextWrapper) {
      if (result is Activity) {
        return result
      }
      result = result.baseContext
    }
    throw IllegalArgumentException("The passed Context is not an Activity.")
  }

  internal fun fixPopupLocation(popupWindow: PopupWindow, desiredLocation: Point) {
    popupWindow.contentView.post {
      val actualLocation = locationOnScreen(popupWindow.contentView)
      if (!(actualLocation.x == desiredLocation.x && actualLocation.y == desiredLocation.y)) {
        val differenceX = actualLocation.x - desiredLocation.x
        val differenceY = actualLocation.y - desiredLocation.y
        val fixedOffsetX = if (actualLocation.x > desiredLocation.x) {
          desiredLocation.x - differenceX
        } else {
          desiredLocation.x + differenceX
        }
        val fixedOffsetY = if (actualLocation.y > desiredLocation.y) {
          desiredLocation.y - differenceY
        } else {
          desiredLocation.y + differenceY
        }
        popupWindow.update(fixedOffsetX, fixedOffsetY, DONT_UPDATE_FLAG, DONT_UPDATE_FLAG)
      }
    }
  }

  @ColorInt
  internal fun resolveColor(context: Context, @AttrRes resource: Int, @ColorRes fallback: Int): Int {
    val value = TypedValue()
    context.theme.resolveAttribute(resource, value, true)
    val resolvedColor = if (value.resourceId != 0) {
      ContextCompat.getColor(context, value.resourceId)
    } else {
      value.data
    }
    return if (resolvedColor != 0) {
      resolvedColor
    } else {
      ContextCompat.getColor(context, fallback)
    }
  }
}

/** Dispatches a KeyEvent which mimics the press of the Backspace key */
fun EditText.dispatchBackspace() {
  val event = KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL)
  dispatchKeyEvent(event)
}
