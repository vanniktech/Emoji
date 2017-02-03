package com.vanniktech.emoji.emoji;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

public interface EmojiCategory {
  @NonNull Emoji[] getEmojis();

  @DrawableRes int getIcon();
}
