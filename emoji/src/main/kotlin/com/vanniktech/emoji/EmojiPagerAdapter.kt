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

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

internal class EmojiPagerAdapter(
  private val delegate: EmojiPagerDelegate,
  private val recentEmoji: RecentEmoji,
  private val variantManager: VariantEmoji,
  private val theming: EmojiTheming,
) : PagerAdapter() {
  private var recentEmojiGridView: RecentEmojiGridView? = null

  fun hasRecentEmoji() = recentEmoji !is NoRecentEmoji

  fun recentAdapterItemCount() = if (hasRecentEmoji()) 1 else 0

  override fun getCount() = EmojiManager.categories().size + recentAdapterItemCount()

  override fun instantiateItem(pager: ViewGroup, position: Int): Any {
    val newView = when {
      hasRecentEmoji() && position == RECENT_POSITION -> {
        val view = RecentEmojiGridView(pager.context).init(delegate, delegate, theming, recentEmoji)
        recentEmojiGridView = view
        view
      }
      else -> {
        val category = EmojiManager.categories()[position - recentAdapterItemCount()]
        CategoryGridView(pager.context).init(delegate, delegate, theming, category, variantManager)
      }
    }

    pager.addView(newView)
    return newView
  }

  override fun destroyItem(pager: ViewGroup, position: Int, view: Any) {
    pager.removeView(view as View)
    if (hasRecentEmoji() && position == RECENT_POSITION) {
      recentEmojiGridView = null
    }
  }

  override fun isViewFromObject(view: View, `object`: Any) = view === `object`

  fun numberOfRecentEmojis() = recentEmoji.getRecentEmojis().size

  fun invalidateRecentEmojis() {
    recentEmojiGridView?.invalidateEmojis()
  }

  internal companion object {
    private const val RECENT_POSITION = 0
  }
}
