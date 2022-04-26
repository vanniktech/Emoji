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

package com.vanniktech.emoji;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

final class EmojiPagerAdapter extends PagerAdapter {
  private static final int RECENT_POSITION = 0;

  private final EmojiPagerDelegate delegate;
  private final RecentEmoji recentEmoji;
  private final VariantEmoji variantManager;
  @NonNull private final EmojiTheming theming;

  private RecentEmojiGridView recentEmojiGridView;

  EmojiPagerAdapter(
      final EmojiPagerDelegate delegate,
      final RecentEmoji recentEmoji,
      final VariantEmoji variantManager,
      @NonNull final EmojiTheming theming
  ) {
    this.delegate = delegate;
    this.recentEmoji = recentEmoji;
    this.variantManager = variantManager;
    this.theming = theming;
  }

  boolean hasRecentEmoji() {
    return !(recentEmoji instanceof NoRecentEmoji);
  }

  int recentAdapterItemCount() {
    return hasRecentEmoji() ? 1 : 0;
  }

  @Override public int getCount() {
    return EmojiManager.getInstance().getCategories().length + recentAdapterItemCount();
  }

  @Override @NonNull public Object instantiateItem(@NonNull final ViewGroup pager, final int position) {
    final View newView;

    if (hasRecentEmoji() && position == RECENT_POSITION) {
      newView = new RecentEmojiGridView(pager.getContext()).init(delegate, delegate, recentEmoji, theming);
      recentEmojiGridView = (RecentEmojiGridView) newView;
    } else {
      newView = new EmojiGridView(pager.getContext()).init(delegate, delegate,
              EmojiManager.getInstance().getCategories()[position - recentAdapterItemCount()], variantManager, theming);
    }

    pager.addView(newView);
    return newView;
  }

  @Override public void destroyItem(final ViewGroup pager, final int position, @NonNull final Object view) {
    pager.removeView((View) view);

    if (hasRecentEmoji() && position == RECENT_POSITION) {
      recentEmojiGridView = null;
    }
  }

  @Override public boolean isViewFromObject(final View view, @NonNull final Object object) {
    return view.equals(object);
  }

  int numberOfRecentEmojis() {
    return recentEmoji.getRecentEmojis().size();
  }

  void invalidateRecentEmojis() {
    if (recentEmojiGridView != null) {
      recentEmojiGridView.invalidateEmojis();
    }
  }
}
