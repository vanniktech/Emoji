package com.vanniktech.emoji.<%= package %>.category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.<%= package %>.R;
import com.vanniktech.emoji.<%= package %>.<%= name %>;

import java.util.Arrays;

public final class <%= category %>Category implements EmojiCategory {

  private static final <%= name %>[] emojis = concatAll(<%= chunks %>);

  @Override @NonNull public <%= name %>[] getEmojis() {
    return emojis;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_<%= package %>_category_<%= icon %>;
  }

  @Override @StringRes public int getCategoryName() {
    return R.string.emoji_<%= package %>_category_<%= icon %>;
  }

  private static <%= name %>[] concatAll(<%= name %>[] first, <%= name %>[]... rest) {
    int totalLength = first.length;
    for (final <%= name %>[] array : rest) {
      totalLength += array.length;
    }

    <%= name %>[] result = Arrays.copyOf(first, totalLength);

    int offset = first.length;
    for (final <%= name %>[] array : rest) {
      System.arraycopy(array, 0, result, offset, array.length);
      offset += array.length;
    }

    return result;
  }
}
