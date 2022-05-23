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

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.RotateDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.util.TypedValue
import android.widget.EdgeEffect
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Field
import kotlin.math.sqrt

// https://stackoverflow.com/a/27343228/1979703
internal fun ViewPager.setEdgeColor(@ColorInt color: Int) {
  (ViewPager::class.java.getFieldByName("mLeftEdge")?.get(this) as? EdgeEffect)?.color = color
  (ViewPager::class.java.getFieldByName("mRightEdge")?.get(this) as? EdgeEffect)?.color = color
}

// https://stackoverflow.com/a/59488928/1979703
@SuppressLint("PrivateApi")
internal fun TextView.setHandlesColor(@ColorInt color: Int) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    val size = 22.spToPx(context).toInt()
    val corner = size.toFloat() / 2
    val inset = 10.spToPx(context).toInt()

    // left drawable
    val drLeft = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
    drLeft.setSize(size, size)
    drLeft.cornerRadii = floatArrayOf(corner, corner, 0f, 0f, corner, corner, corner, corner)
    setTextSelectHandleLeft(InsetDrawable(drLeft, inset, 0, inset, inset))

    // right drawable
    val drRight = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
    drRight.setSize(size, size)
    drRight.cornerRadii = floatArrayOf(0f, 0f, corner, corner, corner, corner, corner, corner)
    setTextSelectHandleRight(InsetDrawable(drRight, inset, 0, inset, inset))

    // middle drawable
    val drMiddle = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
    drMiddle.setSize(size, size)
    drMiddle.cornerRadii = floatArrayOf(0f, 0f, corner, corner, corner, corner, corner, corner)
    val mInset = (sqrt(2f) * corner - corner).toInt()
    val insetDrawable = InsetDrawable(drMiddle, mInset, mInset, mInset, mInset)
    val rotateDrawable = RotateDrawable()
    rotateDrawable.drawable = insetDrawable
    rotateDrawable.toDegrees = 45f
    rotateDrawable.level = 10000
    setTextSelectHandle(rotateDrawable)
    return
  }

  try {
    val editorField = TextView::class.java.getFieldByName("mEditor")
    val editor = editorField?.get(this) ?: this
    val editorClass: Class<*> = if (editorField != null) Class.forName("android.widget.Editor") else TextView::class.java
    val handles = androidx.collection.ArrayMap<String, String>(3).apply {
      put("mSelectHandleLeft", "mTextSelectHandleLeftRes")
      put("mSelectHandleRight", "mTextSelectHandleRightRes")
      put("mSelectHandleCenter", "mTextSelectHandleRes")
    }
    for (i in 0 until handles.size) {
      editorClass.getFieldByName(handles.keyAt(i))?.let { field: Field ->
        val drawable = field.get(editor) as? Drawable
          ?: TextView::class.java.getFieldByName(handles.valueAt(i))
            ?.getInt(this)
            ?.let { ContextCompat.getDrawable(context, it) }

        if (drawable != null) field.set(editor, drawable.tinted(color))
      }
    }
  } catch (ignored: Throwable) {
  }
}

// https://stackoverflow.com/a/59269370/1979703
@SuppressLint("PrivateApi")
internal fun TextView.setCursorDrawableColor(@ColorInt color: Int) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    textCursorDrawable = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(color, color))
      .apply { setSize(2.spToPx(context).toInt(), textSize.toInt()) }
    return
  }

  try {
    val editorField = TextView::class.java.getFieldByName("mEditor")
    val editor = editorField?.get(this) ?: this
    val editorClass: Class<*> = if (editorField != null) editor.javaClass else TextView::class.java
    val cursorRes = TextView::class.java.getFieldByName("mCursorDrawableRes")?.get(this) as? Int
      ?: return

    val tintedCursorDrawable = ContextCompat.getDrawable(context, cursorRes)?.tinted(color)
      ?: return

    val cursorField = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      editorClass.getFieldByName("mDrawableForCursor")
    } else {
      null
    }
    if (cursorField != null) {
      cursorField.set(editor, tintedCursorDrawable)
    } else {
      editorClass.getFieldByName("mCursorDrawable", "mDrawableForCursor")
        ?.set(editor, arrayOf(tintedCursorDrawable, tintedCursorDrawable))
    }
  } catch (ignored: Throwable) {
  }
}

private fun Class<*>.getFieldByName(vararg name: String): Field? {
  name.forEach {
    try {
      return this.getDeclaredField(it).apply { isAccessible = true }
    } catch (ignored: Throwable) {
    }
  }
  return null
}

private fun Drawable.tinted(@ColorInt color: Int): Drawable = when (this) {
  is VectorDrawableCompat -> this.apply { setTintList(ColorStateList.valueOf(color)) }
  is VectorDrawable -> this.apply { setTintList(ColorStateList.valueOf(color)) }
  else -> DrawableCompat.wrap(this)
    .also { DrawableCompat.setTint(it, color) }
    .let { DrawableCompat.unwrap(it) }
}

private fun Number.spToPx(context: Context? = null): Float {
  val res = context?.resources ?: android.content.res.Resources.getSystem()
  return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), res.displayMetrics)
}
