package com.vanniktech.emoji;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vanniktech.emoji.Utils.checkNotNull;

/**
 * EmojiManager where an EmojiProvider can be installed for further usage.
 */
public final class EmojiManager {
  private static final EmojiManager INSTANCE = new EmojiManager();
  private static final int GUESSED_UNICODE_AMOUNT = 3000;
  private static final int GUESSED_TOTAL_PATTERN_LENGTH = GUESSED_UNICODE_AMOUNT * 4;

  private static final Comparator<String> STRING_LENGTH_COMPARATOR = new Comparator<String>() {
    @Override public int compare(final String first, final String second) {
      final int firstLength = first.length();
      final int secondLength = second.length();

      return firstLength < secondLength ? 1 : firstLength == secondLength ? 0 : -1;
    }
  };

  private final Map<String, Emoji> emojiMap = new LinkedHashMap<>();
  private EmojiCategory[] categories;
  private Pattern emojiPattern;

  private EmojiManager() {
    // No instances apart from singleton.
  }

  static EmojiManager getInstance() {
    return INSTANCE;
  }

  /**
   * Installs the given EmojiProvider.
   *
   * NOTE: That only one can be present at any time.
   *
   * @param provider the provider that should be installed.
   */
  public static void install(@NonNull final EmojiProvider provider) {
    INSTANCE.categories = checkNotNull(provider.getCategories(), "categories == null");
    INSTANCE.emojiMap.clear();

    final List<String> unicodesForPattern = new ArrayList<>(GUESSED_UNICODE_AMOUNT);

    //noinspection ForLoopReplaceableByForEach
    for (int i = 0; i < INSTANCE.categories.length; i++) {
      final Emoji[] emojis = checkNotNull(INSTANCE.categories[i].getEmojis(), "emojies == null");

      //noinspection ForLoopReplaceableByForEach
      for (int j = 0; j < emojis.length; j++) {
        final Emoji emoji = emojis[j];
        final String unicode = emoji.getUnicode();
        final List<Emoji> variants = emoji.getVariants();

        INSTANCE.emojiMap.put(unicode, emoji);
        unicodesForPattern.add(unicode);

        //noinspection ForLoopReplaceableByForEach
        for (int k = 0; k < variants.size(); k++) {
          final Emoji variant = variants.get(k);
          final String variantUnicode = variant.getUnicode();

          INSTANCE.emojiMap.put(variantUnicode, variant);
          unicodesForPattern.add(variantUnicode);
        }
      }
    }

    if (unicodesForPattern.isEmpty()) {
      throw new IllegalArgumentException("Your EmojiProvider must at least have one category with at least one emoji.");
    }

    // We need to sort the unicodes by length so the longest one gets matched first.
    Collections.sort(unicodesForPattern, STRING_LENGTH_COMPARATOR);

    final StringBuilder patternBuilder = new StringBuilder(GUESSED_TOTAL_PATTERN_LENGTH);

    for (final String unicode : unicodesForPattern) {
      patternBuilder.append(Pattern.quote(unicode)).append('|');
    }

    INSTANCE.emojiPattern = Pattern.compile(patternBuilder.deleteCharAt(patternBuilder.length() - 1).toString());
  }

  static void destroy() {
    INSTANCE.emojiMap.clear();
    INSTANCE.categories = null;
    INSTANCE.emojiPattern = null;
  }

  EmojiCategory[] getCategories() {
    verifyInstalled();
    return categories; // NOPMD
  }

  @NonNull List<EmojiRange> findAllEmojis(@NonNull final CharSequence text) {
    verifyInstalled();

    final List<EmojiRange> result = new ArrayList<>();
    final Matcher matcher = emojiPattern.matcher(text);

    while (matcher.find()) {
      final Emoji found = findEmoji(text.subSequence(matcher.start(), matcher.end()));

      if (found != null) {
        result.add(new EmojiRange(matcher.start(), matcher.end(), found));
      }
    }

    return result;
  }

  @Nullable Emoji findEmoji(@NonNull final CharSequence candiate) {
    verifyInstalled();

    // We need to call toString on the candidate, since the emojiMap may not find the requested entry otherwise, because
    // the type is different.
    return emojiMap.get(candiate.toString());
  }

  void verifyInstalled() {
    if (categories == null) {
      throw new IllegalStateException("Please install an EmojiProvider through the EmojiManager.install() method first.");
    }
  }

  static class EmojiRange {
    final int start;
    final int end;
    final Emoji emoji;

    EmojiRange(final int start, final int end, @NonNull final Emoji emoji) {
      this.start = start;
      this.end = end;
      this.emoji = emoji;
    }

    @Override public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }

      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      final EmojiRange that = (EmojiRange) o;

      return start == that.start
              && end == that.end
              && emoji.equals(that.emoji);
    }

    @Override public int hashCode() {
      int result = start;
      result = 31 * result + end;
      result = 31 * result + emoji.hashCode();
      return result;
    }
  }
}
