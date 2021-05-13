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

import androidx.viewpager.widget.PagerAdapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.vanniktech.emoji.listeners.OnEmojiClickListener;
import com.vanniktech.emoji.listeners.OnEmojiLongClickListener;

public final class EmojiPagerAdapter extends PagerAdapter {
  private static final int RECENT_POSITION = 0;

  private final OnEmojiClickListener listener;
  private final OnEmojiLongClickListener longListener;
  private final RecentEmoji recentEmoji;
  private final VariantEmoji variantManager;

  private RecentEmojiGridView recentEmojiGridView;

  EmojiPagerAdapter(final OnEmojiClickListener listener,
                    final OnEmojiLongClickListener longListener,
                    final RecentEmoji recentEmoji, final VariantEmoji variantManager) {
    this.listener = listener;
    this.longListener = longListener;
    this.recentEmoji = recentEmoji;
    this.variantManager = variantManager;
    this.recentEmojiGridView = null;
  }

  @Override public int getCount() {
    if (recentEmojiAllowed())
      return EmojiManager.getInstance().getCategories().length + 1;
    else
      return EmojiManager.getInstance().getCategories().length;

  }

  @Override public Object instantiateItem(final ViewGroup pager, final int position) {
    final View newView;
    Log.d("hellloooo", String.valueOf(position));

    /*
    this part is modified to cover emojiview with no recent tabs
     */
    if (position == RECENT_POSITION && !(recentEmoji instanceof NoRecentEmoji)) {
      newView = new RecentEmojiGridView(pager.getContext()).init(listener, longListener, recentEmoji);
      recentEmojiGridView = (RecentEmojiGridView) newView;
    } else {
      int correctPosition;
      if (recentEmojiAllowed())
        correctPosition = position - 1;
      else
        correctPosition = position;
      newView = new EmojiGridView(pager.getContext()).init(listener, longListener,
              EmojiManager.getInstance().getCategories()[correctPosition], variantManager);
    }

    pager.addView(newView);
    return newView;
  }

  @Override public void destroyItem(final ViewGroup pager, final int position, final Object view) {
    pager.removeView((View) view);

    if (position == RECENT_POSITION && recentEmojiAllowed()) {
      recentEmojiGridView = null;
    }
  }

  @Override public boolean isViewFromObject(final View view, final Object object) {
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
  boolean recentEmojiAllowed(){
    return !(recentEmoji instanceof  NoRecentEmoji);
  }
}
