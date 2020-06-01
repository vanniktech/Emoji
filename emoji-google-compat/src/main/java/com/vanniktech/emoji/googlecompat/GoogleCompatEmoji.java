package com.vanniktech.emoji.googlecompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vanniktech.emoji.emoji.Emoji;

public final class GoogleCompatEmoji extends Emoji {
  public GoogleCompatEmoji(final int[] ints, @Nullable final String shortcode, final boolean isDuplicate,
                           final Emoji... emojis) {
    super(ints, shortcode, -1, isDuplicate, emojis);
  }

  public GoogleCompatEmoji(final int codePoint, @Nullable final String shortcode,
                           final boolean isDuplicate, final Emoji... emojis) {
    super(codePoint, shortcode, -1, isDuplicate, emojis);
  }

  @Override @NonNull public Drawable getDrawable(@NonNull final Context context) {
    return new GoogleCompatEmojiDrawable(getUnicode());
  }
}
