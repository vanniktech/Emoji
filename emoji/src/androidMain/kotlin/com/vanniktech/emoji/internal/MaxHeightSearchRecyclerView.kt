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

import android.content.Context
import android.util.AttributeSet
import android.widget.EdgeEffect
import androidx.recyclerview.widget.RecyclerView
import com.vanniktech.emoji.EmojiTheming
import com.vanniktech.emoji.R

internal class MaxHeightSearchRecyclerView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
) : RecyclerView(context, attrs) {
  init {
    clipToPadding = false
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(
      widthMeasureSpec,
      MeasureSpec.makeMeasureSpec(context.resources.getDimensionPixelSize(R.dimen.emoji_search_max_height), MeasureSpec.AT_MOST),
    )
  }

  fun tint(emojiTheming: EmojiTheming) {
    setBackgroundColor(emojiTheming.backgroundColor)
    edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
      override fun createEdgeEffect(view: RecyclerView, direction: Int) = EdgeEffect(view.context).apply {
        color = emojiTheming.secondaryColor
      }
    }
  }
}
