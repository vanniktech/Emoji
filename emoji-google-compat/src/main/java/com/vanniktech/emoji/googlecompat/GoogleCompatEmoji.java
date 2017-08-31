package com.vanniktech.emoji.googlecompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;

public final class GoogleCompatEmoji extends Emoji {
  public GoogleCompatEmoji(final int[] ints, final Emoji... emojis) {
    super(ints, -1, emojis);
  }

  public GoogleCompatEmoji(final int codePoint, final Emoji... emojis) {
    super(codePoint, -1, emojis);
  }

  @NonNull @Override public Drawable getDrawable(@NonNull final Context context) {
    return new GoogleCompatEmojiDrawable(getUnicode());
  }
}
