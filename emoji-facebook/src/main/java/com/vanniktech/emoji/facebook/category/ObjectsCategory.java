package com.vanniktech.emoji.facebook.category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.facebook.R;
import com.vanniktech.emoji.facebook.FacebookEmoji;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class ObjectsCategory implements EmojiCategory {
  private static final FacebookEmoji[] EMOJIS = CategoryUtils.concatAll(ObjectsCategoryChunk0.get());

  @Override @NonNull public FacebookEmoji[] getEmojis() {
    return EMOJIS;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_facebook_category_objects;
  }

  @Override @StringRes public int getCategoryName() {
    return R.string.emoji_facebook_category_objects;
  }
}
