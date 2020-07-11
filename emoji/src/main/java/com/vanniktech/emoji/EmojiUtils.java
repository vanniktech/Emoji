package com.vanniktech.emoji;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmojiUtils {
  private static final Pattern SPACE_REMOVAL = Pattern.compile("[\\s]");

  /** returns true when the string contains only emojis. Note that whitespace will be filtered out. */
  public static boolean isOnlyEmojis(@Nullable final CharSequence text) {
    if (text != null && text.length() > 0) {
      final String inputWithoutSpaces = SPACE_REMOVAL.matcher(text).replaceAll(Matcher.quoteReplacement(""));

      return EmojiManager.getInstance()
            .getEmojiRepetitivePattern()
            .matcher(inputWithoutSpaces)
            .matches();
    }

    return false;
  }

  /** returns the emojis that were found in the given text */
  @NonNull public static List<EmojiRange> emojis(@Nullable final CharSequence text) {
    return EmojiManager.getInstance().findAllEmojis(text);
  }

  /** returns the number of all emojis that were found in the given text */
  public static int emojisCount(@Nullable final CharSequence text) {
    return emojis(text).size();
  }

  /** returns a class that contains all of the emoji information that was found in the given text */
  @NonNull public static EmojiInformation emojiInformation(@Nullable final CharSequence text) {
    final List<EmojiRange> emojis = EmojiManager.getInstance().findAllEmojis(text);
    final boolean isOnlyEmojis = isOnlyEmojis(text);
    return new EmojiInformation(isOnlyEmojis, emojis);
  }

  private EmojiUtils() {
    throw new AssertionError("No instances.");
  }
}
