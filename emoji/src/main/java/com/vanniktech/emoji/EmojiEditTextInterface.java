package com.vanniktech.emoji;

import android.support.annotation.DimenRes;
import android.support.annotation.Px;
import com.vanniktech.emoji.emoji.Emoji;

/**
 * Interface used to allow custom EmojiEditText objects on another project.
 */
public interface EmojiEditTextInterface {
  void backspace();

  void input(Emoji emoji);

  float getEmojiSize();

  void setEmojiSize(@Px int pixels);

  void setEmojiSize(@Px int pixels, boolean shouldInvalidate);

  void setEmojiSizeRes(@DimenRes int res);

  void setEmojiSizeRes(@DimenRes int res, boolean shouldInvalidate);
}
