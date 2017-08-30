package com.vanniktech.emoji.googlecompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;

public final class GoogleCompatEmoji extends Emoji {
  private static final int EMOJI_STYLE_VARIANT_SELECTOR = 0xfe0f;
  private static final int UNAMBIGOUS_EMOJI_START_POINT = 0x1f600;

  public GoogleCompatEmoji(final int[] ints, final Emoji... emojis) {
    super(ints, -1, emojis);
  }

  public GoogleCompatEmoji(final int codePoint, final Emoji... emojis) {
    // The support library seems to skip low emoji code points if not explicitly marked as emoji using the modifier.
    super(codePoint < UNAMBIGOUS_EMOJI_START_POINT ? new int[] {codePoint, EMOJI_STYLE_VARIANT_SELECTOR} : new int[] {codePoint}, -1, emojis);
  }

  @NonNull @Override public Drawable getDrawable(@NonNull final Context context) {
    return new GoogleCompatEmojiDrawable(getUnicode());
  }
}
