package com.vanniktech.emoji;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmojiUtils {
  private static final Pattern SPACE_REMOVAL = Pattern.compile("[\\s]");

  /** returns true when the string only contains emojis. Note that whitespace will be filtered out. */
  public static boolean isOnlyEmojis(@Nullable final String text) {
    if (TextUtils.isEmpty(text)) {
      return false;
    }

    final String inputWithoutSpaces = SPACE_REMOVAL.matcher(text).replaceAll(Matcher.quoteReplacement(""));

    return EmojiManager.getInstance()
          .getEmojiRepetitivePattern()
          .matcher(inputWithoutSpaces)
          .matches();
  }

  /** returns the number of all emojis that were found in the given text */
  public static int emojisCount(@Nullable final String text) {
    return TextUtils.isEmpty(text) ? 0 : EmojiManager.getInstance().findAllEmojis(text).size();
  }

  private EmojiUtils() {
    throw new AssertionError("No instances.");
  }
}
