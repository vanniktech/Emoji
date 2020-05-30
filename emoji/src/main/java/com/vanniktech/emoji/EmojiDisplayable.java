package com.vanniktech.emoji;

import androidx.annotation.DimenRes;
import androidx.annotation.Px;

public interface EmojiDisplayable {
  /** Returns the emoji size */
  float getEmojiSize();

  /** Sets the emoji size in pixels and automatically invalidates the text and renders it with the new size. */
  void setEmojiSize(@Px int pixels);

  /** Sets the emoji size in pixels and automatically invalidates the text and renders it with the new size when {@code shouldInvalidate} is true. */
  void setEmojiSize(@Px int pixels, boolean shouldInvalidate);

  /** Sets the emoji size in pixels with the provided resource and automatically invalidates the text and renders it with the new size. */
  void setEmojiSizeRes(@DimenRes int res);

  /** Sets the emoji size in pixels with the provided resource and invalidates the text and renders it with the new size when {@code shouldInvalidate} is true. */
  void setEmojiSizeRes(@DimenRes int res, boolean shouldInvalidate);
}
