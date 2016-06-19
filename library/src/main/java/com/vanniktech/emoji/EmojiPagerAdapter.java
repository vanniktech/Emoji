package com.vanniktech.emoji;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

final class EmojiPagerAdapter extends PagerAdapter {
    private final List<? extends View> views;

    EmojiPagerAdapter(final List<? extends View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup pager, final int position) {
        final View view = views.get(position);
        pager.addView(view);
        return view;
    }

    @Override
    public void destroyItem(final ViewGroup pager, final int position, final Object view) {
        pager.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }
}
