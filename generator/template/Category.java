package com.vanniktech.emoji.<%= package %>.category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.<%= package %>.R;
import com.vanniktech.emoji.<%= package %>.<%= name %>;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class <%= category %>Category implements EmojiCategory {
  private static final <%= name %>[] EMOJIS = CategoryUtils.concatAll(<%= chunks %>);

  @Override @NonNull public <%= name %>[] getEmojis() {
    return EMOJIS;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_<%= package %>_category_<%= icon %>;
  }

  @Override @StringRes public int getCategoryName() {
    return R.string.emoji_<%= package %>_category_<%= icon %>;
  }
}
