package com.vanniktech.emoji.facebook.category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.facebook.R;
import com.vanniktech.emoji.facebook.FacebookEmoji;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class FoodAndDrinkCategory implements EmojiCategory {
  private static final FacebookEmoji[] EMOJIS = CategoryUtils.concatAll(FoodAndDrinkCategoryChunk0.get());

  @Override @NonNull public FacebookEmoji[] getEmojis() {
    return EMOJIS;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_facebook_category_foodanddrink;
  }

  @Override @StringRes public int getCategoryName() {
    return R.string.emoji_facebook_category_foodanddrink;
  }
}
