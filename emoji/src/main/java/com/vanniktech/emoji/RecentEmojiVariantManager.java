package com.vanniktech.emoji;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static com.vanniktech.emoji.RecentEmojiManager.EMOJI_GUESS_SIZE;

final class RecentEmojiVariantManager {
  private static final RecentEmojiVariantManager INSTANCE = new RecentEmojiVariantManager();

  public static RecentEmojiVariantManager getInstance() {
    return INSTANCE;
  }

  private static final String PREFERENCE_NAME = "emoji-recent-variant-manager";
  private static final String EMOJI_DELIMITER = "~";
  private static final String RECENT_VARIANTS = "recent-variants";

  private List<Emoji> recentVariantsList = new ArrayList<>(0);

  Map<Emoji, EmojiVariantListener> variantListeners = new HashMap<>();

  Emoji getMostRecentVariant(@NonNull final Emoji desiredEmoji, final Context context) {
    if (recentVariantsList.isEmpty()) {
      initFromSharedPreferences(context);
    }

    final Emoji baseEmoji = desiredEmoji.getBase();

    for (int i = 0; i < recentVariantsList.size(); i++) {
      final Emoji emoji = recentVariantsList.get(i);

      if (baseEmoji.equals(emoji.getBase())) {
        return emoji;
      }
    }

    return desiredEmoji;
  }

  void addRecentVariant(@NonNull final Emoji newVariant) {
    final Emoji newVariantBase = newVariant.getBase();

    for (int i = 0; i < recentVariantsList.size(); i++) {
      final Emoji recentVariant = recentVariantsList.get(i);

      if (recentVariant.getBase().equals(newVariantBase)) {
        if (recentVariant.equals(newVariant)) {
          return; // Same skin-tone was used.
        } else {
          recentVariantsList.remove(i);
          recentVariantsList.add(newVariant);

          final EmojiVariantListener emojiVariantListener = variantListeners.get(newVariantBase);

          if (emojiVariantListener != null) {
            emojiVariantListener.onChanged(newVariant);
          }

          return;
        }
      }
    }

    recentVariantsList.add(newVariant);

    final EmojiVariantListener emojiVariantListener = variantListeners.get(newVariantBase);

    if (emojiVariantListener != null) {
      emojiVariantListener.onChanged(newVariant);
    }
  }

  Disposable addListener(final Emoji emoji, final EmojiVariantListener emojiVariantListener) {
    if (!emoji.getBase().equals(emoji)) {
      throw new IllegalStateException("You can only listen with using the base emoji.");
    }

    variantListeners.put(emoji, emojiVariantListener);

    return new Disposable() {
      @Override public void dispose() {
        variantListeners.remove(emoji);
      }
    };
  }

  void persist(final Context context) {
    if (recentVariantsList.size() > 0) {
      final StringBuilder stringBuilder = new StringBuilder(recentVariantsList.size() * EMOJI_GUESS_SIZE);

      for (int i = 0; i < recentVariantsList.size(); i++) {
        stringBuilder.append(recentVariantsList.get(i).getUnicode()).append(EMOJI_DELIMITER);
      }

      stringBuilder.setLength(stringBuilder.length() - EMOJI_DELIMITER.length());

      getPreferences(context.getApplicationContext()).edit()
          .putString(RECENT_VARIANTS, stringBuilder.toString())
          .apply();
    }
  }

  private static SharedPreferences getPreferences(final Context context) {
    return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
  }

  private void initFromSharedPreferences(final Context context) {
    final String savedRecentVariants = getPreferences(context.getApplicationContext()).getString(RECENT_VARIANTS, "");

    if (savedRecentVariants.length() > 0) {
      final StringTokenizer stringTokenizer = new StringTokenizer(savedRecentVariants, EMOJI_DELIMITER);
      recentVariantsList = new ArrayList<>(stringTokenizer.countTokens());

      while (stringTokenizer.hasMoreTokens()) {
        final String token = stringTokenizer.nextToken();
        final Emoji emoji = EmojiManager.getInstance().findEmoji(token);

        if (emoji != null && emoji.getLength() == token.length()) {
          recentVariantsList.add(emoji);
        }
      }
    }
  }

  interface EmojiVariantListener {
    void onChanged(Emoji newEmoji);
  }

  interface Disposable {
    void dispose();
  }
}
