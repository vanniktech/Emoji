package com.vanniktech.emoji.google.category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.google.R;
import com.vanniktech.emoji.google.GoogleEmoji;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class AnimalsAndNatureCategory implements EmojiCategory {
  private static final GoogleEmoji[] EMOJIS = CategoryUtils.concatAll(AnimalsAndNatureCategoryChunk0.get());

  @Override @NonNull public GoogleEmoji[] getEmojis() {
    return EMOJIS;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_google_category_animalsandnature;
  }

  @Override @StringRes public int getCategoryName() {
    return R.string.emoji_google_category_animalsandnature;
  }
}
