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

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupWindow
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.lang.IllegalArgumentException
import kotlin.math.roundToInt

private const val DONT_UPDATE_FLAG = -1

internal object Utils {
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

  internal fun getProperHeight(activity: Activity): Int {
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

internal inline val Context.inputMethodManager get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
internal fun EditText.showKeyboardAndFocus() {
  post {
    requestFocus()
    context.inputMethodManager.showSoftInput(this, 0)
  }
}

internal fun EditText.hideKeyboardAndFocus() {
  post {
    clearFocus()
    context.inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
  }
}
