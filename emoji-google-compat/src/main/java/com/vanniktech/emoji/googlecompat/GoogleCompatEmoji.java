package com.vanniktech.emoji.googlecompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

import com.vanniktech.emoji.emoji.Emoji;

public final class GoogleCompatEmoji extends Emoji {
  public GoogleCompatEmoji(final int[] ints, @NonNull final String[] shortcodes, final boolean isDuplicate,
                           final Emoji... emojis) {
    super(ints, shortcodes, -1, isDuplicate, emojis);
  }

  public GoogleCompatEmoji(final int codePoint, @NonNull final String[] shortcodes,
                           final boolean isDuplicate, final Emoji... emojis) {
    super(codePoint, shortcodes, -1, isDuplicate, emojis);
  }

  @Override @NonNull public Drawable getDrawable(@NonNull final Context context) {
    return new GoogleCompatEmojiDrawable(getUnicode());
  }
}
