package com.vanniktech.emoji.googlecompat.category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.vanniktech.emoji.googlecompat.GoogleCompatEmoji;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.googlecompat.R;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class FoodCategory implements EmojiCategory {
  private static final Emoji[] DATA = new Emoji[] {
    new GoogleCompatEmoji(0x1f34f),
    new GoogleCompatEmoji(0x1f34e),
    new GoogleCompatEmoji(0x1f350),
    new GoogleCompatEmoji(0x1f34a),
    new GoogleCompatEmoji(0x1f34b),
    new GoogleCompatEmoji(0x1f34c),
    new GoogleCompatEmoji(0x1f349),
    new GoogleCompatEmoji(0x1f347),
    new GoogleCompatEmoji(0x1f353),
    new GoogleCompatEmoji(0x1f348),
    new GoogleCompatEmoji(0x1f352),
    new GoogleCompatEmoji(0x1f351),
    new GoogleCompatEmoji(0x1f34d),
    new GoogleCompatEmoji(0x1f95d),
    new GoogleCompatEmoji(0x1f951),
    new GoogleCompatEmoji(0x1f345),
    new GoogleCompatEmoji(0x1f346),
    new GoogleCompatEmoji(0x1f952),
    new GoogleCompatEmoji(0x1f955),
    new GoogleCompatEmoji(0x1f33d),
    new GoogleCompatEmoji(0x1f336),
    new GoogleCompatEmoji(0x1f954),
    new GoogleCompatEmoji(0x1f360),
    new GoogleCompatEmoji(0x1f330),
    new GoogleCompatEmoji(0x1f95c),
    new GoogleCompatEmoji(0x1f36f),
    new GoogleCompatEmoji(0x1f950),
    new GoogleCompatEmoji(0x1f35e),
    new GoogleCompatEmoji(0x1f956),
    new GoogleCompatEmoji(0x1f9c0),
    new GoogleCompatEmoji(0x1f95a),
    new GoogleCompatEmoji(0x1f373),
    new GoogleCompatEmoji(0x1f953),
    new GoogleCompatEmoji(0x1f95e),
    new GoogleCompatEmoji(0x1f364),
    new GoogleCompatEmoji(0x1f357),
    new GoogleCompatEmoji(0x1f356),
    new GoogleCompatEmoji(0x1f355),
    new GoogleCompatEmoji(0x1f32d),
    new GoogleCompatEmoji(0x1f354),
    new GoogleCompatEmoji(0x1f35f),
    new GoogleCompatEmoji(0x1f959),
    new GoogleCompatEmoji(0x1f32e),
    new GoogleCompatEmoji(0x1f32f),
    new GoogleCompatEmoji(0x1f957),
    new GoogleCompatEmoji(0x1f958),
    new GoogleCompatEmoji(0x1f35d),
    new GoogleCompatEmoji(0x1f35c),
    new GoogleCompatEmoji(0x1f372),
    new GoogleCompatEmoji(0x1f365),
    new GoogleCompatEmoji(0x1f363),
    new GoogleCompatEmoji(0x1f371),
    new GoogleCompatEmoji(0x1f35b),
    new GoogleCompatEmoji(0x1f359),
    new GoogleCompatEmoji(0x1f35a),
    new GoogleCompatEmoji(0x1f358),
    new GoogleCompatEmoji(0x1f362),
    new GoogleCompatEmoji(0x1f361),
    new GoogleCompatEmoji(0x1f367),
    new GoogleCompatEmoji(0x1f368),
    new GoogleCompatEmoji(0x1f366),
    new GoogleCompatEmoji(0x1f370),
    new GoogleCompatEmoji(0x1f382),
    new GoogleCompatEmoji(0x1f36e),
    new GoogleCompatEmoji(0x1f36d),
    new GoogleCompatEmoji(0x1f36c),
    new GoogleCompatEmoji(0x1f36b),
    new GoogleCompatEmoji(0x1f37f),
    new GoogleCompatEmoji(0x1f369),
    new GoogleCompatEmoji(0x1f36a),
    new GoogleCompatEmoji(0x1f95b),
    new GoogleCompatEmoji(0x1f37c),
    new GoogleCompatEmoji(0x2615),
    new GoogleCompatEmoji(0x1f375),
    new GoogleCompatEmoji(0x1f376),
    new GoogleCompatEmoji(0x1f37a),
    new GoogleCompatEmoji(0x1f37b),
    new GoogleCompatEmoji(0x1f942),
    new GoogleCompatEmoji(0x1f377),
    new GoogleCompatEmoji(0x1f943),
    new GoogleCompatEmoji(0x1f378),
    new GoogleCompatEmoji(0x1f379),
    new GoogleCompatEmoji(0x1f37e),
    new GoogleCompatEmoji(0x1f944),
    new GoogleCompatEmoji(0x1f374),
    new GoogleCompatEmoji(0x1f37d),
  };

  @Override @NonNull public Emoji[] getEmojis() {
    return DATA;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_compat_category_food;
  }

  @Override @StringRes public int getCategoryName() {
    return R.string.emoji_compat_category_food;
  }
}
