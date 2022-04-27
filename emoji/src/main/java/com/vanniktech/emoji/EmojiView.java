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
import java.util.concurrent.Executors;

import static com.vanniktech.emoji.Utils.backspace;
import static java.util.concurrent.TimeUnit.SECONDS;

public final class EmojiView extends LinearLayout {
  private static final long INITIAL_INTERVAL = SECONDS.toMillis(1) / 2;
  private static final int NORMAL_INTERVAL = 50;

  private EmojiTheming theming;
  private ImageButton[] emojiTabs = new ImageButton[0];
  private EmojiPagerAdapter emojiPagerAdapter;
  @Nullable private EditText editText;
  @Nullable private OnEmojiClickListener onEmojiClickListener;

  @Nullable OnEmojiBackspaceClickListener onEmojiBackspaceClickListener;

  private int emojiTabLastSelectedIndex = -1;

  EmojiVariantPopup variantPopup;
  RecentEmoji recentEmoji;
  SearchEmoji searchEmoji;
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

  /**
   * Call this method to set up the EmojiView.
   * Once you're done with it, please call {@link #tearDown()}.
   */
  @SuppressWarnings({ "PMD.JUnit4TestShouldUseBeforeAnnotation" })
  public void setUp(
    @NonNull final View rootView,
    @NonNull final EmojiTheming theming,
    @NonNull final RecentEmoji recentEmoji,
    @NonNull final SearchEmoji searchEmoji,
    @NonNull final VariantEmoji variantEmoji,
    @Nullable final ViewPager.PageTransformer pageTransformer,
    @Nullable final OnEmojiClickListener onEmojiClickListener,
    @Nullable final OnEmojiBackspaceClickListener onEmojiBackspaceClickListener,
    @Nullable final EditText editText
  ) {
    final Context context = getContext();
    this.editText = editText;
    this.theming = theming;
    this.recentEmoji = recentEmoji;
    this.searchEmoji = searchEmoji;
    this.variantEmoji = variantEmoji;
    this.onEmojiBackspaceClickListener = onEmojiBackspaceClickListener;
    this.onEmojiClickListener = onEmojiClickListener;
    variantPopup = new EmojiVariantPopup(rootView, (emojiImageView, emoji) -> {
      handleEmojiClick(emoji);

      emojiImageView.updateEmoji(emoji); // To reflect new variant in the UI.
      dismissVariantPopup();
    });

    setBackgroundColor(EmojiThemings.backgroundColor(theming, context));

    final ViewPager emojisPager = findViewById(R.id.emojiViewPager);
    final View emojiDivider = findViewById(R.id.emojiViewDivider);
    emojiDivider.setBackgroundColor(EmojiThemings.dividerColor(theming, context));

    if (pageTransformer != null) {
      emojisPager.setPageTransformer(true, pageTransformer);
    }

    emojisPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageScrolled(final int i, final float v, final int i2) {
        // No-op.
      }

      @Override public void onPageScrollStateChanged(final int i) {
        // No-op.
      }

      @Override public void onPageSelected(final int i) {
        selectPage(i);
      }
    });

    handleEmojiTabs(context, emojisPager);

    emojisPager.setAdapter(emojiPagerAdapter);

    final int startIndex = emojiPagerAdapter.hasRecentEmoji() ? emojiPagerAdapter.numberOfRecentEmojis() > 0 ? 0 : 1 : 0;
    emojisPager.setCurrentItem(startIndex);
    selectPage(startIndex);
  }

  void selectPage(final int index) {
    final Context context = getContext();

    if (emojiTabLastSelectedIndex != index) {
      if (index == 0) {
        emojiPagerAdapter.invalidateRecentEmojis();
      }

      if (emojiTabLastSelectedIndex >= 0 && emojiTabLastSelectedIndex < emojiTabs.length) {
        emojiTabs[emojiTabLastSelectedIndex].setSelected(false);
        emojiTabs[emojiTabLastSelectedIndex].setColorFilter(EmojiThemings.primaryColor(theming, context), PorterDuff.Mode.SRC_IN);
      }

      emojiTabs[index].setSelected(true);
      emojiTabs[index].setColorFilter(EmojiThemings.secondaryColor(theming, context), PorterDuff.Mode.SRC_IN);

      emojiTabLastSelectedIndex = index;
    }
  }

  @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
  private void handleEmojiTabs(
      final Context context,
      final ViewPager emojisPager
  ) {
    final EmojiCategory[] categories = EmojiManager.getInstance().getCategories();
    final LinearLayout emojisTab = findViewById(R.id.emojiViewTab);

    emojiPagerAdapter = new EmojiPagerAdapter(new EmojiPagerDelegate() {
      @Override public void onEmojiClick(@NonNull final Emoji emoji) {
        handleEmojiClick(emoji);
      }

      @Override public void onEmojiLongClick(@NonNull final EmojiImageView view, @NonNull final Emoji emoji) {
        variantPopup.show(view, emoji);
      }
    }, recentEmoji, variantEmoji, theming);

    final boolean hasBackspace = editText != null || onEmojiBackspaceClickListener != null;
    final boolean hasSearch = !(searchEmoji instanceof NoSearchEmoji);
    final int endIndexes = (hasSearch ? 1 : 0) + (hasBackspace ? 1 : 0);
    final int recentAdapterItemCount = emojiPagerAdapter.recentAdapterItemCount();
    emojiTabs = new ImageButton[recentAdapterItemCount + categories.length + endIndexes];

    if (emojiPagerAdapter.hasRecentEmoji()) {
      emojiTabs[0] = inflateButton(context, R.drawable.emoji_recent, R.string.emoji_category_recent, emojisTab);
    }

    final Integer searchIndex = hasSearch ? emojiTabs.length - (hasBackspace ? 2 : 1) : null;
    final Integer backspaceIndex = hasBackspace ? emojiTabs.length - 1 : null;

    for (int i = 0; i < categories.length; i++) {
      emojiTabs[i + recentAdapterItemCount] = inflateButton(context, categories[i].getIcon(), categories[i].getCategoryName(), emojisTab);
    }

    if (searchIndex != null) {
      emojiTabs[searchIndex] = inflateButton(context, R.drawable.emoji_search, R.string.emoji_search, emojisTab);
      emojiTabs[searchIndex].setOnClickListener(v -> EmojiSearchDialog.show(
          getContext(),
          emoji -> {
            handleEmojiClick(emoji);

            // Maybe the search was opened from the recent tab and hence we'll invalidate.
            emojiPagerAdapter.invalidateRecentEmojis();
          },
          searchEmoji,
          recentEmoji,
          theming
      ));
    }

    if (backspaceIndex != null) {
      emojiTabs[backspaceIndex] = inflateButton(context, R.drawable.emoji_backspace, R.string.emoji_backspace, emojisTab);
      emojiTabs[backspaceIndex].setOnTouchListener(new RepeatListener(INITIAL_INTERVAL, NORMAL_INTERVAL, view -> {
        if (editText != null) {
          backspace(editText);
        }

        if (onEmojiBackspaceClickListener != null) {
          onEmojiBackspaceClickListener.onEmojiBackspaceClick();
        }
      }));
    }

    for (int i = 0; i < emojiTabs.length - endIndexes; i++) {
      emojiTabs[i].setOnClickListener(new EmojiTabsClickListener(emojisPager, i));
    }
  }

  private ImageButton inflateButton(
      final Context context,
      @DrawableRes final int icon,
      @StringRes final int categoryName,
      final ViewGroup parent
  ) {
    final ImageButton button = (ImageButton) LayoutInflater.from(context).inflate(R.layout.emoji_view_category, parent, false);

    button.setImageDrawable(AppCompatResources.getDrawable(context, icon));
    button.setColorFilter(EmojiThemings.primaryColor(theming, context), PorterDuff.Mode.SRC_IN);
    button.setContentDescription(context.getString(categoryName));

    parent.addView(button);

    return button;
  }

  void handleEmojiClick(@NonNull final Emoji emoji) {
    if (editText != null) {
      Utils.input(editText, emoji);
    }

    recentEmoji.addEmoji(emoji);
    variantEmoji.addVariant(emoji);

    if (onEmojiClickListener != null) {
      onEmojiClickListener.onEmojiClick(emoji);
    }
  }

  /**
   * Counterpart of {@link #setUp(View, EmojiTheming, RecentEmoji, SearchEmoji, VariantEmoji, ViewPager.PageTransformer, OnEmojiClickListener, OnEmojiBackspaceClickListener, EditText)}
   */
  @SuppressWarnings({ "PMD.JUnit4TestShouldUseAfterAnnotation" })
  public void tearDown() {
    dismissVariantPopup();

    Executors.newSingleThreadExecutor().submit(() -> {
      recentEmoji.persist();
      variantEmoji.persist();
    });
  }

  void dismissVariantPopup() {
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
