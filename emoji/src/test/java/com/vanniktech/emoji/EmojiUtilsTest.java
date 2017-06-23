package com.vanniktech.emoji;

import android.support.annotation.NonNull;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class EmojiUtilsTest {

  private static final String EMOJI_1 = "\u1234";
  private static final String EMOJI_2 = "\u4321";

  @Before public void setUp() {
    EmojiManager.install(new EmojiProvider() {
      @NonNull
      @Override public EmojiCategory[] getCategories() {
        return new EmojiCategory[]{new EmojiCategory() {
          @NonNull @Override public Emoji[] getEmojis() {
            return new Emoji[]{
                  new Emoji(EMOJI_1.codePointAt(0), R.drawable.emoji_recent),
                  new Emoji(EMOJI_2.codePointAt(0), R.drawable.emoji_backspace),
            };
          }

          @Override public int getIcon() {
            return R.drawable.emoji_recent;
          }
        }};
      }
    });
  }

  @Test public void constructorShouldBePrivate() {
    PrivateConstructorChecker.forClass(EmojiUtils.class)
          .expectedTypeOfException(AssertionError.class)
          .expectedExceptionMessage("No instances.")
          .check();
  }

  @Test public void isOnlyEmojis_empty() {
    boolean onlyEmojis = EmojiUtils.isOnlyEmojis("");

    assertThat(onlyEmojis).isFalse();
  }

  @Test public void isOnlyEmojis_null() {
    boolean onlyEmojis = EmojiUtils.isOnlyEmojis(null);

    assertThat(onlyEmojis).isFalse();
  }

  @Test public void isOnlyEmojis_text() {
    boolean onlyEmojis = EmojiUtils.isOnlyEmojis("hello world!");

    assertThat(onlyEmojis).isFalse();
  }

  @Test public void isOnlyEmojis_singleEmoji() {
    boolean onlyEmojis = EmojiUtils.isOnlyEmojis(EMOJI_1);

    assertThat(onlyEmojis).isTrue();
  }

  @Test public void isOnlyEmojis_multipleEmojis() {
    boolean onlyEmojis = EmojiUtils.isOnlyEmojis(EMOJI_1 + EMOJI_2);

    assertThat(onlyEmojis).isTrue();
  }

  @Test public void isOnlyEmojis_multipleEmojisWithSpaces() {
    boolean onlyEmojis = EmojiUtils.isOnlyEmojis(" " + EMOJI_1 + "    " + EMOJI_2 + "  \n");

    assertThat(onlyEmojis).isTrue();
  }

  @Test public void isOnlyEmojis_singleEmojiAndText() {
    boolean onlyEmojis = EmojiUtils.isOnlyEmojis(EMOJI_1 + "hello");

    assertThat(onlyEmojis).isFalse();
  }

  @Test public void isOnlyEmojis_singleTextAndEmoji() {
    boolean onlyEmojis = EmojiUtils.isOnlyEmojis("hello" + EMOJI_1);

    assertThat(onlyEmojis).isFalse();
  }

  @Test public void isOnlyEmojis_multipleEmojisAndText() {
    boolean onlyEmojis = EmojiUtils.isOnlyEmojis(EMOJI_1 + "hello" + EMOJI_2);

    assertThat(onlyEmojis).isFalse();
  }

  @Test public void isOnlyEmojis_multipleTextAndEmojis() {
    boolean onlyEmojis = EmojiUtils.isOnlyEmojis("hello" + EMOJI_1 + "world");

    assertThat(onlyEmojis).isFalse();
  }

  @Test public void emojisCount_empty() {
    int emojisCount = EmojiUtils.emojisCount("");

    assertThat(emojisCount).isEqualTo(0);
  }

  @Test public void emojisCount_null() {
    int emojisCount = EmojiUtils.emojisCount(null);

    assertThat(emojisCount).isEqualTo(0);
  }

  @Test public void emojisCount_text() {
    int emojisCount = EmojiUtils.emojisCount("hello world!");

    assertThat(emojisCount).isEqualTo(0);
  }

  @Test public void emojisCount_singleEmoji() {
    int emojisCount = EmojiUtils.emojisCount(EMOJI_1);

    assertThat(emojisCount).isEqualTo(1);
  }

  @Test public void emojisCount_multipleEmoji() {
    int emojisCount = EmojiUtils.emojisCount(EMOJI_1 + EMOJI_2);

    assertThat(emojisCount).isEqualTo(2);
  }

  @Test public void emojisCount_multipleEmojisWithSpaces() {
    int emojisCount = EmojiUtils.emojisCount(" " + EMOJI_1 + "    " + EMOJI_2 + "  \n");

    assertThat(emojisCount).isEqualTo(2);
  }

  @Test public void emojisCount_singleEmojiAndText() {
    int emojisCount = EmojiUtils.emojisCount(EMOJI_1 + "hello");

    assertThat(emojisCount).isEqualTo(1);
  }

  @Test public void emojisCount_singleTextAndEmoji() {
    int emojisCount = EmojiUtils.emojisCount("hello" + EMOJI_1);

    assertThat(emojisCount).isEqualTo(1);
  }

  @Test public void emojisCount_multipleEmojisAndText() {
    int emojisCount = EmojiUtils.emojisCount(EMOJI_1 + "hello" + EMOJI_2);

    assertThat(emojisCount).isEqualTo(2);
  }

  @Test public void emojisCount_multipleTextAndEmojis() {
    int emojisCount = EmojiUtils.emojisCount("hello" + EMOJI_1 + "world");

    assertThat(emojisCount).isEqualTo(1);
  }
}