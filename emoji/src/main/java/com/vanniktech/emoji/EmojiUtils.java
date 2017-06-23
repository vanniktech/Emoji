package com.vanniktech.emoji;

import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmojiUtils {

  private static final Pattern SPACE_REMOVAL = Pattern.compile(" ");

  static boolean isOnlyEmojis(@Nullable String text) {
    if (Utils.isEmpty(text)) return false;

    final String inputWithoutSpaces = SPACE_REMOVAL.matcher(text.trim()).replaceAll(Matcher.quoteReplacement(""));

    return EmojiManager.getInstance()
          .getEmojiRepetitivePattern()
          .matcher(inputWithoutSpaces)
          .matches();
  }

  static int emojisCount(@Nullable String text) {
    return Utils.isEmpty(text) ? 0 : EmojiManager.getInstance().findAllEmojis(text).size();
  }

  private EmojiUtils() {
    throw new AssertionError("No instances.");
  }
}
