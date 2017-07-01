package com.vanniktech.emoji;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;

final class TestEmojiProvider implements EmojiProvider {
  static EmojiProvider from(final Emoji... emojis) {
    return new TestEmojiProvider(emojis);
  }

  static EmojiProvider emptyCategories() {
    return new EmojiProvider() {
      @NonNull @Override public EmojiCategory[] getCategories() {
        return new EmojiCategory[0];
      }
    };
  }

  static EmojiProvider emptyEmojis() {
    return new EmojiProvider() {
      @NonNull @Override public EmojiCategory[] getCategories() {
        return new EmojiCategory[] {
          new EmojiCategory() {
            @NonNull @Override public Emoji[] getEmojis() {
              return new Emoji[0];
            }

            @Override public int getIcon() {
              return 0;
            }
          }
        };
      }
    };
  }

  final Emoji[] emojis;

  private TestEmojiProvider(final Emoji[] emojis) { // NOPMD
    this.emojis = emojis;
  }

  @NonNull @Override public EmojiCategory[] getCategories() {
    return new EmojiCategory[] {
      new EmojiCategory() {
        @NonNull @Override public Emoji[] getEmojis() {
          return emojis; // NOPMD
        }

        @Override public int getIcon() {
          return R.drawable.emoji_recent;
        }
      }
    };
  }
}
