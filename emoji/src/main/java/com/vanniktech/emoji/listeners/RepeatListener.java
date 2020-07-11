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

package com.vanniktech.emoji.listeners;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

public final class RepeatListener implements View.OnTouchListener {
  final long normalInterval;
  final View.OnClickListener clickListener;

  final Handler handler = new Handler();
  private final long initialInterval;
  View downView;

  private final Runnable handlerRunnable = new Runnable() {
    @Override public void run() {
      if (downView != null) {
        handler.removeCallbacksAndMessages(downView);
        handler.postAtTime(this, downView, SystemClock.uptimeMillis() + normalInterval);
        clickListener.onClick(downView);
      }
    }
  };

  public RepeatListener(final long initialInterval, final long normalInterval,
      final View.OnClickListener clickListener) {
    if (clickListener == null) {
      throw new IllegalArgumentException("null runnable");
    }

    if (initialInterval < 0 || normalInterval < 0) {
      throw new IllegalArgumentException("negative interval");
    }

    this.initialInterval = initialInterval;
    this.normalInterval = normalInterval;
    this.clickListener = clickListener;
  }

  @Override @SuppressLint("ClickableViewAccessibility") public boolean onTouch(final View view, final MotionEvent motionEvent) {
    switch (motionEvent.getAction()) {
      case MotionEvent.ACTION_DOWN:
        handler.removeCallbacks(handlerRunnable);
        handler.postAtTime(handlerRunnable, downView, SystemClock.uptimeMillis() + initialInterval);
        downView = view;
        downView.setPressed(true);
        clickListener.onClick(view);
        return true;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_OUTSIDE:
        handler.removeCallbacksAndMessages(downView);
        downView.setPressed(false);
        downView = null;
        return true;
      default:
        break;
    }

    return false;
  }
}
