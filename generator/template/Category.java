package com.vanniktech.emoji.<%= package %>.category;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.<%= package %>.R;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class <%= name %>Category implements EmojiCategory {
  private static final Emoji[] DATA = new Emoji[] {
    <%= data %>
  };

  @Override @NonNull public Emoji[] getEmojis() {
    return DATA;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_<%= package %>_category_<%= icon %>;
  }
}
