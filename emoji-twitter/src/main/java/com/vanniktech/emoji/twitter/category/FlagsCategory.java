package com.vanniktech.emoji.twitter.category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.twitter.R;
import com.vanniktech.emoji.twitter.TwitterEmoji;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class FlagsCategory implements EmojiCategory {
  private static final TwitterEmoji[] EMOJIS = CategoryUtils.concatAll(FlagsCategoryChunk0.get(), FlagsCategoryChunk1.get());

  @Override @NonNull public TwitterEmoji[] getEmojis() {
    return EMOJIS;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_twitter_category_flags;
  }

  @Override @StringRes public int getCategoryName() {
    return R.string.emoji_twitter_category_flags;
  }
}
