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
import android.widget.GridView
import com.vanniktech.emoji.R

internal open class EmojiGridView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : GridView(context, attrs) {
  init {
    val width = resources.getDimensionPixelSize(R.dimen.emoji_grid_view_column_width)
    val spacing = resources.getDimensionPixelSize(R.dimen.emoji_grid_view_spacing)
    columnWidth = width
    horizontalSpacing = spacing
    verticalSpacing = spacing
    @Suppress("LeakingThis")
    setPadding(spacing, spacing, spacing, spacing)
    numColumns = AUTO_FIT
    clipToPadding = false
    isVerticalScrollBarEnabled = false
  }
}
