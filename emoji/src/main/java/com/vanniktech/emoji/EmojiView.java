package com.vanniktech.emoji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.content.res.AppCompatResources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickListener;
import com.vanniktech.emoji.listeners.OnEmojiLongClickListener;
import com.vanniktech.emoji.listeners.RepeatListener;

import java.util.concurrent.TimeUnit;

@SuppressLint("ViewConstructor")
final class EmojiView extends LinearLayout implements ViewPager.OnPageChangeListener {
  private static final long INITIAL_INTERVAL = TimeUnit.SECONDS.toMillis(1) / 2;
  private static final int NORMAL_INTERVAL = 50;

  @ColorInt
  private int iconNormalColor;
  @ColorInt
  private int iconSelectedColor;

  private final ImageButton[] emojiTabs;
  private final EmojiPagerAdapter emojiPagerAdapter;

  @Nullable
  OnEmojiBackspaceClickListener onEmojiBackspaceClickListener;

  private int emojiTabLastSelectedIndex = -1;

  EmojiView(@NonNull final Context context,
            @NonNull final OnEmojiClickListener onEmojiClickListener,
            @NonNull final OnEmojiLongClickListener onEmojiLongClickListener,
            @NonNull final RecentEmoji recentEmoji,
            @NonNull final VariantEmoji variantManager,
            @NonNull final int backgroundColor,
            @NonNull final int iconNormalColor,
            @NonNull final int iconSelectedColor,
            @NonNull final int variantIndicatorColor,
            @NonNull final int dividerColor) {
    super(context);

    View.inflate(context, R.layout.emoji_view, this);

    setOrientation(VERTICAL);
    setBackgroundColor(backgroundColor != 0 ? backgroundColor : ContextCompat.getColor(getContext(), R.color.emoji_background));

    if (iconNormalColor != 0) {
      this.iconNormalColor = iconNormalColor;
    } else {
      this.iconNormalColor = ContextCompat.getColor(context, R.color.emoji_icons);
    }

    if (iconSelectedColor != 0) {
      this.iconSelectedColor = iconSelectedColor;
    } else {
      final TypedValue value = new TypedValue();
      context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
      this.iconSelectedColor = value.data;
    }

    final View emojisDivider = findViewById(R.id.emojis_divider);
    final ViewPager emojisPager = findViewById(R.id.emojis_pager);
    final LinearLayout emojisTab = findViewById(R.id.emojis_tab);

    emojisPager.addOnPageChangeListener(this);

    emojisDivider.setBackgroundColor(dividerColor != 0 ? dividerColor : ContextCompat.getColor(context, R.color.emoji_divider));

    final EmojiCategory[] categories = EmojiManager.getInstance().getCategories();

    emojiTabs = new ImageButton[categories.length + 2];
    emojiTabs[0] = inflateButton(context, R.drawable.emoji_recent, emojisTab);
    for (int i = 0; i < categories.length; i++) {
      emojiTabs[i + 1] = inflateButton(context, categories[i].getIcon(), emojisTab);
    }
    emojiTabs[emojiTabs.length - 1] = inflateButton(context, R.drawable.emoji_backspace, emojisTab);

    handleOnClicks(emojisPager);

    emojiPagerAdapter = new EmojiPagerAdapter(onEmojiClickListener, onEmojiLongClickListener, recentEmoji, variantManager, variantIndicatorColor);
    emojisPager.setAdapter(emojiPagerAdapter);

    final int startIndex = emojiPagerAdapter.numberOfRecentEmojis() > 0 ? 0 : 1;
    emojisPager.setCurrentItem(startIndex);
    onPageSelected(startIndex);
  }

  private void handleOnClicks(final ViewPager emojisPager) {
    for (int i = 0; i < emojiTabs.length - 1; i++) {
      emojiTabs[i].setOnClickListener(new EmojiTabsClickListener(emojisPager, i));
    }

    emojiTabs[emojiTabs.length - 1].setOnTouchListener(new RepeatListener(INITIAL_INTERVAL, NORMAL_INTERVAL, new OnClickListener() {
      @Override
      public void onClick(final View view) {
        if (onEmojiBackspaceClickListener != null) {
          onEmojiBackspaceClickListener.onEmojiBackspaceClick(view);
        }
      }
    }));
  }

  public void setOnEmojiBackspaceClickListener(@Nullable final OnEmojiBackspaceClickListener onEmojiBackspaceClickListener) {
    this.onEmojiBackspaceClickListener = onEmojiBackspaceClickListener;
  }

  private ImageButton inflateButton(final Context context, @DrawableRes final int icon, final ViewGroup parent) {
    final ImageButton button = (ImageButton) LayoutInflater.from(context).inflate(R.layout.emoji_category, parent, false);

    button.setImageDrawable(AppCompatResources.getDrawable(context, icon));
    button.setColorFilter(iconNormalColor, PorterDuff.Mode.SRC_IN);

    parent.addView(button);

    return button;
  }

  @Override
  public void onPageSelected(final int i) {
    if (emojiTabLastSelectedIndex != i) {
      if (i == 0) {
        emojiPagerAdapter.invalidateRecentEmojis();
      }

      if (emojiTabLastSelectedIndex >= 0 && emojiTabLastSelectedIndex < emojiTabs.length) {
        emojiTabs[emojiTabLastSelectedIndex].setSelected(false);
        emojiTabs[emojiTabLastSelectedIndex].setColorFilter(iconNormalColor, PorterDuff.Mode.SRC_IN);
      }

      emojiTabs[i].setSelected(true);
      emojiTabs[i].setColorFilter(iconSelectedColor, PorterDuff.Mode.SRC_IN);

      emojiTabLastSelectedIndex = i;
    }
  }

  @Override
  public void onPageScrolled(final int i, final float v, final int i2) {
    // No-op.
  }

  @Override
  public void onPageScrollStateChanged(final int i) {
    // No-op.
  }

  static class EmojiTabsClickListener implements OnClickListener {
    private final ViewPager emojisPager;
    private final int position;

    EmojiTabsClickListener(final ViewPager emojisPager, final int position) {
      this.emojisPager = emojisPager;
      this.position = position;
    }

    @Override
    public void onClick(final View v) {
      emojisPager.setCurrentItem(position);
    }
  }
}
