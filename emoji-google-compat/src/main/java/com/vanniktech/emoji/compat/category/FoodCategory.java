package com.vanniktech.emoji.compat.category;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import com.vanniktech.emoji.compat.CompatEmoji;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.compat.R;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class FoodCategory implements EmojiCategory {
  private static final Emoji[] DATA = new Emoji[] {
    new CompatEmoji(0x1f34f),  //_1f34f),
    new CompatEmoji(0x1f34e),  //_1f34e),
    new CompatEmoji(0x1f350),  //_1f350),
    new CompatEmoji(0x1f34a),  //_1f34a),
    new CompatEmoji(0x1f34b),  //_1f34b),
    new CompatEmoji(0x1f34c),  //_1f34c),
    new CompatEmoji(0x1f349),  //_1f349),
    new CompatEmoji(0x1f347),  //_1f347),
    new CompatEmoji(0x1f353),  //_1f353),
    new CompatEmoji(0x1f348),  //_1f348),
    new CompatEmoji(0x1f352),  //_1f352),
    new CompatEmoji(0x1f351),  //_1f351),
    new CompatEmoji(0x1f34d),  //_1f34d),
    new CompatEmoji(0x1f95d),  //_1f95d),
    new CompatEmoji(0x1f951),  //_1f951),
    new CompatEmoji(0x1f345),  //_1f345),
    new CompatEmoji(0x1f346),  //_1f346),
    new CompatEmoji(0x1f952),  //_1f952),
    new CompatEmoji(0x1f955),  //_1f955),
    new CompatEmoji(0x1f33d),  //_1f33d),
    new CompatEmoji(0x1f336),  //_1f336),
    new CompatEmoji(0x1f954),  //_1f954),
    new CompatEmoji(0x1f360),  //_1f360),
    new CompatEmoji(0x1f330),  //_1f330),
    new CompatEmoji(0x1f95c),  //_1f95c),
    new CompatEmoji(0x1f36f),  //_1f36f),
    new CompatEmoji(0x1f950),  //_1f950),
    new CompatEmoji(0x1f35e),  //_1f35e),
    new CompatEmoji(0x1f956),  //_1f956),
    new CompatEmoji(0x1f9c0),  //_1f9c0),
    new CompatEmoji(0x1f95a),  //_1f95a),
    new CompatEmoji(0x1f373),  //_1f373),
    new CompatEmoji(0x1f953),  //_1f953),
    new CompatEmoji(0x1f95e),  //_1f95e),
    new CompatEmoji(0x1f364),  //_1f364),
    new CompatEmoji(0x1f357),  //_1f357),
    new CompatEmoji(0x1f356),  //_1f356),
    new CompatEmoji(0x1f355),  //_1f355),
    new CompatEmoji(0x1f32d),  //_1f32d),
    new CompatEmoji(0x1f354),  //_1f354),
    new CompatEmoji(0x1f35f),  //_1f35f),
    new CompatEmoji(0x1f959),  //_1f959),
    new CompatEmoji(0x1f32e),  //_1f32e),
    new CompatEmoji(0x1f32f),  //_1f32f),
    new CompatEmoji(0x1f957),  //_1f957),
    new CompatEmoji(0x1f958),  //_1f958),
    new CompatEmoji(0x1f35d),  //_1f35d),
    new CompatEmoji(0x1f35c),  //_1f35c),
    new CompatEmoji(0x1f372),  //_1f372),
    new CompatEmoji(0x1f365),  //_1f365),
    new CompatEmoji(0x1f363),  //_1f363),
    new CompatEmoji(0x1f371),  //_1f371),
    new CompatEmoji(0x1f35b),  //_1f35b),
    new CompatEmoji(0x1f359),  //_1f359),
    new CompatEmoji(0x1f35a),  //_1f35a),
    new CompatEmoji(0x1f358),  //_1f358),
    new CompatEmoji(0x1f362),  //_1f362),
    new CompatEmoji(0x1f361),  //_1f361),
    new CompatEmoji(0x1f367),  //_1f367),
    new CompatEmoji(0x1f368),  //_1f368),
    new CompatEmoji(0x1f366),  //_1f366),
    new CompatEmoji(0x1f370),  //_1f370),
    new CompatEmoji(0x1f382),  //_1f382),
    new CompatEmoji(0x1f36e),  //_1f36e),
    new CompatEmoji(0x1f36d),  //_1f36d),
    new CompatEmoji(0x1f36c),  //_1f36c),
    new CompatEmoji(0x1f36b),  //_1f36b),
    new CompatEmoji(0x1f37f),  //_1f37f),
    new CompatEmoji(0x1f369),  //_1f369),
    new CompatEmoji(0x1f36a),  //_1f36a),
    new CompatEmoji(0x1f95b),  //_1f95b),
    new CompatEmoji(0x1f37c),  //_1f37c),
    new CompatEmoji(0x2615),  //_2615),
    new CompatEmoji(0x1f375),  //_1f375),
    new CompatEmoji(0x1f376),  //_1f376),
    new CompatEmoji(0x1f37a),  //_1f37a),
    new CompatEmoji(0x1f37b),  //_1f37b),
    new CompatEmoji(0x1f942),  //_1f942),
    new CompatEmoji(0x1f377),  //_1f377),
    new CompatEmoji(0x1f943),  //_1f943),
    new CompatEmoji(0x1f378),  //_1f378),
    new CompatEmoji(0x1f379),  //_1f379),
    new CompatEmoji(0x1f37e),  //_1f37e),
    new CompatEmoji(0x1f944),  //_1f944),
    new CompatEmoji(0x1f374),  //_1f374),
    new CompatEmoji(0x1f37d),  //_1f37d)
  };

  @Override @NonNull public Emoji[] getEmojis() {
    return DATA;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_compat_category_food;
  }
}
