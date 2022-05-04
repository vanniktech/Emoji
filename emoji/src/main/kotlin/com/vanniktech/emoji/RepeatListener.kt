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

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener

internal class RepeatListener(
  private val initialInterval: Long,
  private val normalInterval: Long,
  private val clickListener: View.OnClickListener,
) : OnTouchListener {
  private val handler = Handler(Looper.getMainLooper())
  private var downView: View? = null

  private val handlerRunnable: Runnable = object : Runnable {
    override fun run() {
      if (downView != null) {
        handler.removeCallbacksAndMessages(downView)
        handler.postAtTime(this, downView, SystemClock.uptimeMillis() + normalInterval)
        clickListener.onClick(downView)
      }
    }
  }

  init {
    require(!(initialInterval < 0 || normalInterval < 0)) { "negative interval" }
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouch(view: View, motionEvent: MotionEvent) = when (motionEvent.action) {
    MotionEvent.ACTION_DOWN -> {
      handler.removeCallbacks(handlerRunnable)
      handler.postAtTime(handlerRunnable, downView, SystemClock.uptimeMillis() + initialInterval)
      downView = view
      downView?.isPressed = true
      clickListener.onClick(view)
      true
    }
    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
      handler.removeCallbacksAndMessages(downView)
      downView?.isPressed = false
      downView = null
      true
    }
    else -> false
  }
}
