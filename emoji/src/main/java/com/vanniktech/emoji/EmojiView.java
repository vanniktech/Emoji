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

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewpager.widget.ViewPager;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickListener;
import com.vanniktech.emoji.listeners.RepeatListener;
import org.jetbrains.annotations.NotNull;

import static com.vanniktech.emoji.Utils.backspace;
import static java.util.concurrent.TimeUnit.SECONDS;

public final class EmojiView extends LinearLayout implements ViewPager.OnPageChangeListener, EmojiSearchDialogDelegate, EmojiPagerDelegate {
  private static final long INITIAL_INTERVAL = SECONDS.toMillis(1) / 2;
  private static final int NORMAL_INTERVAL = 50;

  private EmojiTheming theming;
  private ImageButton[] emojiTabs = new ImageButton[0];
  private EmojiPagerAdapter emojiPagerAdapter;
  private EditText editText;
  @Nullable private OnEmojiClickListener onEmojiClickListener;

  @Nullable OnEmojiBackspaceClickListener onEmojiBackspaceClickListener;

  private int emojiTabLastSelectedIndex = -1;

  EmojiVariantPopup variantPopup;
  RecentEmoji recentEmoji;
  VariantEmoji variantEmoji;

  public EmojiView(final Context context) {
    super(context);
    init(context);
  }

  public EmojiView(final Context context, @Nullable final AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public EmojiView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(final Context context) {
    View.inflate(context, R.layout.emoji_view, this);
    setOrientation(VERTICAL);
  }

  @SuppressWarnings({ "PMD.JUnit4TestShouldUseBeforeAnnotation" })
  void setUp(
    @Nullable final OnEmojiClickListener onEmojiClickListener,
    @Nullable final OnEmojiBackspaceClickListener onEmojiBackspaceClickListener,
    @NonNull final EmojiTheming theming,
    @NonNull final RecentEmoji recentEmoji,
    @NonNull final VariantEmoji variantEmoji,
    @Nullable final ViewPager.PageTransformer pageTransformer,
    @NonNull final View rootView,
    @NonNull final EditText editText
  ) {
    final Context context = getContext();
    this.editText = editText;
    this.theming = theming;
    this.recentEmoji = recentEmoji;
    this.variantEmoji = variantEmoji;
    this.onEmojiBackspaceClickListener = onEmojiBackspaceClickListener;
    this.onEmojiClickListener = onEmojiClickListener;
    this.variantPopup = new EmojiVariantPopup(rootView, this);

    setBackgroundColor(EmojiThemings.backgroundColor(theming, context));

    final ViewPager emojisPager = findViewById(R.id.emojiViewPager);
    final View emojiDivider = findViewById(R.id.emojiViewDivider);
    emojiDivider.setBackgroundColor(EmojiThemings.dividerColor(theming, context));

    if (pageTransformer != null) {
      emojisPager.setPageTransformer(true, pageTransformer);
    }

    final LinearLayout emojisTab = findViewById(R.id.emojiViewTab);
    emojisPager.addOnPageChangeListener(this);

    final EmojiCategory[] categories = EmojiManager.getInstance().getCategories();

    emojiPagerAdapter = new EmojiPagerAdapter(this, recentEmoji, variantEmoji, theming);
    emojiTabs = new ImageButton[emojiPagerAdapter.recentAdapterItemCount() + categories.length + 1];

    if (emojiPagerAdapter.hasRecentEmoji()) {
      emojiTabs[0] = inflateButton(context, R.drawable.emoji_recent, R.string.emoji_category_recent, emojisTab, theming);
    }

    for (int i = 0; i < categories.length; i++) {
      emojiTabs[i + emojiPagerAdapter.recentAdapterItemCount()] = inflateButton(context, categories[i].getIcon(), categories[i].getCategoryName(), emojisTab, theming);
    }

    emojiTabs[emojiTabs.length - 2] = inflateButton(context, R.drawable.emoji_search, R.string.emoji_search, emojisTab, theming);
    emojiTabs[emojiTabs.length - 1] = inflateButton(context, R.drawable.emoji_backspace, R.string.emoji_backspace, emojisTab, theming);

    handleOnClicks(emojisPager);

    emojisPager.setAdapter(emojiPagerAdapter);

    final int startIndex = emojiPagerAdapter.hasRecentEmoji() ? emojiPagerAdapter.numberOfRecentEmojis() > 0 ? 0 : 1 : 0;
    emojisPager.setCurrentItem(startIndex);
    onPageSelected(startIndex);
  }

  private void handleOnClicks(
      final ViewPager emojisPager
  ) {
    for (int i = 0; i < emojiTabs.length - 1; i++) {
      emojiTabs[i].setOnClickListener(new EmojiTabsClickListener(emojisPager, i));
    }

    final EmojiSearchDialogDelegate emojiSearchDialogDelegate = this;

    emojiTabs[emojiTabs.length - 2].setOnClickListener(v -> EmojiSearchDialog.show(
      getContext(),
      emojiSearchDialogDelegate,
      recentEmoji,
      theming
    ));
    emojiTabs[emojiTabs.length - 1].setOnTouchListener(new RepeatListener(INITIAL_INTERVAL, NORMAL_INTERVAL, view -> {
      backspace(editText);

      if (onEmojiBackspaceClickListener != null) {
        onEmojiBackspaceClickListener.onEmojiBackspaceClick(view);
      }
    }));
  }

  private ImageButton inflateButton(
      final Context context,
      @DrawableRes final int icon,
      @StringRes final int categoryName,
      final ViewGroup parent,
      final EmojiTheming theming
  ) {
    final ImageButton button = (ImageButton) LayoutInflater.from(context).inflate(R.layout.emoji_view_category, parent, false);

    button.setImageDrawable(AppCompatResources.getDrawable(context, icon));
    button.setColorFilter(EmojiThemings.primaryColor(theming, context), PorterDuff.Mode.SRC_IN);
    button.setContentDescription(context.getString(categoryName));

    parent.addView(button);

    return button;
  }

  @Override public void onPageSelected(final int i) {
    final Context context = getContext();

    if (emojiTabLastSelectedIndex != i) {
      if (i == 0) {
        emojiPagerAdapter.invalidateRecentEmojis();
      }

      if (emojiTabLastSelectedIndex >= 0 && emojiTabLastSelectedIndex < emojiTabs.length) {
        emojiTabs[emojiTabLastSelectedIndex].setSelected(false);
        emojiTabs[emojiTabLastSelectedIndex].setColorFilter(EmojiThemings.primaryColor(theming, context), PorterDuff.Mode.SRC_IN);
      }

      emojiTabs[i].setSelected(true);
      emojiTabs[i].setColorFilter(EmojiThemings.secondaryColor(theming, context), PorterDuff.Mode.SRC_IN);

      emojiTabLastSelectedIndex = i;
    }
  }

  @Override public void onPageScrolled(final int i, final float v, final int i2) {
    // No-op.
  }

  @Override public void onPageScrollStateChanged(final int i) {
    // No-op.
  }

  @Override public void onClicked(@NotNull final Emoji emoji) {
    Utils.input(editText, emoji);
    emojiPagerAdapter.invalidateRecentEmojis();
  }

  @Override public void onEmojiClick(@NonNull final EmojiImageView emojiImageView, @NonNull final Emoji emoji) {
    Utils.input(editText, emoji);

    recentEmoji.addEmoji(emoji);
    variantEmoji.addVariant(emoji);
    emojiImageView.updateEmoji(emoji);

    if (onEmojiClickListener != null) {
      onEmojiClickListener.onEmojiClick(emojiImageView, emoji);
    }
    dismiss();
  }

  @Override public void onEmojiLongClick(@NonNull final EmojiImageView view, @NonNull final Emoji emoji) {
    variantPopup.show(view, emoji);
  }

  void dismiss() {
    variantPopup.dismiss();
  }

  static class EmojiTabsClickListener implements OnClickListener {
    private final ViewPager emojisPager;
    private final int position;

    EmojiTabsClickListener(final ViewPager emojisPager, final int position) {
      this.emojisPager = emojisPager;
      this.position = position;
    }

    @Override public void onClick(final View v) {
      emojisPager.setCurrentItem(position);
    }
  }
}
