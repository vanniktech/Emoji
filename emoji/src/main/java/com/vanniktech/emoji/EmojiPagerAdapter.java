package com.vanniktech.emoji;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;

import java.util.ArrayList;

final class EmojiPagerAdapter extends PagerAdapter {
  private final OnEmojiClickedListener listener;
  private final OnEmojiLongClickedListener longListener;
  private final RecentEmoji recentEmoji;

  private RecentEmojiGridView recentEmojiGridView;
  private ArrayList<EmojiGridView> gridViewsList = new ArrayList<>(0);

  EmojiPagerAdapter(final OnEmojiClickedListener listener,
                    final OnEmojiLongClickedListener longListener,
                    final RecentEmoji recentEmoji) {
    this.listener = listener;
    this.longListener = longListener;
    this.recentEmoji = recentEmoji;
    this.recentEmojiGridView = null;
  }

  @Override public int getCount() {
    return EmojiManager.getInstance().getCategories().length + 1;
  }

  @Override public Object instantiateItem(final ViewGroup pager, final int position) {
    final View newView;

    if (position == 0) {
      newView = new RecentEmojiGridView(pager.getContext()).init(listener, longListener, recentEmoji);

      recentEmojiGridView = (RecentEmojiGridView) newView;
    } else {
      newView = new EmojiGridView(pager.getContext()).init(listener, longListener, EmojiManager.getInstance().getCategories()[position - 1]);
      gridViewsList.add((EmojiGridView) newView);
    }

    pager.addView(newView);

    return newView;
  }

  public void notifyPagesChanged(){
    for(EmojiGridView gridView : gridViewsList){
      gridView.notifyCurrentPageChanged();
    }
    recentEmojiGridView.notifyCurrentPageChanged();
  }

  @Override public void destroyItem(final ViewGroup pager, final int position, final Object view) {
    pager.removeView((View) view);

    if (position == 0) {
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
}
