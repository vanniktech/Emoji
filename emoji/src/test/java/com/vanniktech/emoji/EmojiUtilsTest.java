package com.vanniktech.emoji;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;
import com.vanniktech.emoji.emoji.Emoji;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner.class) public class EmojiUtilsTest {
  static final String EMOJI_1 = "\u1234";
  static final int CODE_POINT_1 = EMOJI_1.codePointAt(0);
  static final String EMOJI_2 = "\u4321";
  static final int CODE_POINT_2 = EMOJI_2.codePointAt(0);

  @Before public void setUp() {
    final Emoji emoji1 = new Emoji(CODE_POINT_1, R.drawable.emoji_recent, false);
    final Emoji emoji2 = new Emoji(CODE_POINT_2, R.drawable.emoji_backspace, false);
    EmojiManager.install(TestEmojiProvider.from(emoji1, emoji2));
  }

  @Test public void constructorShouldBePrivate() {
    PrivateConstructorChecker.forClass(EmojiUtils.class)
          .expectedTypeOfException(AssertionError.class)
          .expectedExceptionMessage("No instances.")
          .check();
  }

  @Test public void isOnlyEmojisEmpty() {
    assertThat(EmojiUtils.isOnlyEmojis("")).isFalse();
  }

  @Test public void isOnlyEmojisNull() {
    assertThat(EmojiUtils.isOnlyEmojis(null)).isFalse();
  }

  @Test public void isOnlyEmojisText() {
    assertThat(EmojiUtils.isOnlyEmojis("hello world!")).isFalse();
  }

  @Test public void isOnlyEmojisSingleEmoji() {
    assertThat(EmojiUtils.isOnlyEmojis(EMOJI_1)).isTrue();
  }

  @Test public void isOnlyEmojisMultipleEmojis() {
    assertThat(EmojiUtils.isOnlyEmojis(EMOJI_1 + EMOJI_2)).isTrue();
  }

  @Test public void isOnlyEmojisMultipleEmojisWithSpaces() {
    assertThat(EmojiUtils.isOnlyEmojis(" \t" + EMOJI_1 + "\t    " + EMOJI_2 + "  \r\n")).isTrue();
  }

  @Test public void isOnlyEmojisSingleEmojiAndText() {
    assertThat(EmojiUtils.isOnlyEmojis(EMOJI_1 + "hello")).isFalse();
  }

  @Test public void isOnlyEmojisSingleTextAndEmoji() {
    assertThat(EmojiUtils.isOnlyEmojis("hello" + EMOJI_1)).isFalse();
  }

  @Test public void isOnlyEmojisMultipleEmojisAndText() {
    assertThat(EmojiUtils.isOnlyEmojis(EMOJI_1 + "hello" + EMOJI_2)).isFalse();
  }

  @Test public void isOnlyEmojisMultipleTextAndEmojis() {
    assertThat(EmojiUtils.isOnlyEmojis("hello" + EMOJI_1 + "world")).isFalse();
  }

  @Test public void emojisCountEmpty() {
    assertThat(EmojiUtils.emojisCount("")).isEqualTo(0);
  }

  @Test public void emojisCountNull() {
    assertThat(EmojiUtils.emojisCount(null)).isEqualTo(0);
  }

  @Test public void emojisCountText() {
    assertThat(EmojiUtils.emojisCount("hello world!")).isEqualTo(0);
  }

  @Test public void emojisCountSingleEmoji() {
    assertThat(EmojiUtils.emojisCount(EMOJI_1)).isEqualTo(1);
  }

  @Test public void emojisCountMultipleEmoji() {
    assertThat(EmojiUtils.emojisCount(EMOJI_1 + EMOJI_2)).isEqualTo(2);
  }

  @Test public void emojisCountMultipleEmojisWithSpaces() {
    assertThat(EmojiUtils.emojisCount(" " + EMOJI_1 + "    " + EMOJI_2 + "  \n")).isEqualTo(2);
  }

  @Test public void emojisCountSingleEmojiAndText() {
    assertThat(EmojiUtils.emojisCount(EMOJI_1 + "hello")).isEqualTo(1);
  }

  @Test public void emojisCountSingleTextAndEmoji() {
    assertThat(EmojiUtils.emojisCount("hello" + EMOJI_1)).isEqualTo(1);
  }

  @Test public void emojisCountMultipleEmojisAndText() {
    assertThat(EmojiUtils.emojisCount(EMOJI_1 + "hello" + EMOJI_2)).isEqualTo(2);
  }

  @Test public void emojisCountMultipleTextAndEmojis() {
    assertThat(EmojiUtils.emojisCount("hello" + EMOJI_1 + "world")).isEqualTo(1);
  }
}
