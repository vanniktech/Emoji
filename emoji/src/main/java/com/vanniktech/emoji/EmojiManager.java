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
      return second.length() - first.length();
    }
  };

  private final Map<CharSequence, Emoji> emojiMap = new LinkedHashMap<>();
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
        final Emoji currentEmoji = emojis[j];

        INSTANCE.emojiMap.put(currentEmoji.getUnicode(), currentEmoji);
        unicodesForPattern.add(currentEmoji.getUnicode());

        for (final Emoji variant : currentEmoji.getVariants()) {
          INSTANCE.emojiMap.put(variant.getUnicode(), variant);
          unicodesForPattern.add(variant.getUnicode());
        }
      }
    }

    if (unicodesForPattern.isEmpty()) {
      throw new IllegalArgumentException("Your EmojiProvider must at least have one category with at least one emoji");
    }

    // We need to sort the unicodes by length so the longest get matched first.
    Collections.sort(unicodesForPattern, STRING_LENGTH_COMPARATOR);

    final StringBuilder patternBuilder = new StringBuilder(GUESSED_TOTAL_PATTERN_LENGTH);

    for (final String unicode : unicodesForPattern) {
      patternBuilder.append(Pattern.quote(unicode)).append('|');
    }

    INSTANCE.emojiPattern = Pattern.compile(patternBuilder.deleteCharAt(patternBuilder.length() - 1).toString());
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
    return emojiMap.get(candiate.toString());
  }

  void verifyInstalled() {
    if (categories == null) {
      throw new IllegalStateException("Please install an EmojiProvider through the EmojiManager.install() method first.");
    }
  }

  static class EmojiRange {
    private final int start;
    private final int end;
    private final Emoji emoji;

    EmojiRange(final int start, final int end, @NonNull final Emoji emoji) {
      this.start = start;
      this.end = end;
      this.emoji = emoji;
    }

    public int getStart() {
      return start;
    }

    public int getEnd() {
      return end;
    }

    @NonNull public Emoji getEmoji() {
      return emoji;
    }
  }
}
