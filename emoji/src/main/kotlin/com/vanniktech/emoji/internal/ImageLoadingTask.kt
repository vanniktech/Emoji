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

@file:Suppress("DEPRECATION")

package com.vanniktech.emoji.internal

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.widget.ImageView
import com.vanniktech.emoji.Emoji
import java.lang.ref.WeakReference

internal class ImageLoadingTask(imageView: ImageView) : AsyncTask<Emoji, Void?, Drawable?>() {
  private val imageViewReference: WeakReference<ImageView> = WeakReference(imageView)
  private val contextReference: WeakReference<Context> = WeakReference(imageView.context)

  @Deprecated("Deprecated in Java")
  override fun doInBackground(vararg emoji: Emoji): Drawable? {
    val context = contextReference.get()
    return if (context != null && !isCancelled) {
      emoji[0].getDrawable(context)
    } else null
  }

  @Deprecated("Deprecated in Java")
  override fun onPostExecute(drawable: Drawable?) {
    if (!isCancelled && drawable != null) {
      val imageView = imageViewReference.get()
      imageView?.setImageDrawable(drawable)
    }
  }

  fun cancel() {
    cancel(true)
  }

  fun start(emoji: Emoji?) {
    execute(emoji)
  }
}
