package com.vanniktech.emoji;

import android.text.Spannable;
import android.text.SpannableString;
import com.vanniktech.emoji.emoji.Emoji;
import org.assertj.core.api.ThrowableAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner.class) public class EmojiManagerTest {
  @Rule public final ExpectedException expectedException = ExpectedException.none();

  private EmojiProvider provider;

  @Before public void setUp() {
    final Emoji emoji1 = new Emoji(new int[] { 0x1234 }, R.drawable.emoji_recent, false);
    final Emoji emoji2 = new Emoji(new int[] { 0x4321 }, R.drawable.emoji_recent, false);
    final Emoji emoji3 = new Emoji(new int[] { 0x5678 }, R.drawable.emoji_backspace, false);
    final Emoji emoji4 = new Emoji(new int[] { 0x1234, 0x4321, 0x9999 }, R.drawable.emoji_recent, false);

    provider = TestEmojiProvider.from(emoji1, emoji2, emoji3, emoji4);
  }

  @After public void tearDown() {
    EmojiManager.destroy();
  }

  @Test public void installNormalCategory() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().getCategories()).isNotEmpty();
  }

  @Test public void noProviderInstalled() {
    assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
      @Override public void call() throws Throwable {
        EmojiManager.getInstance().findEmoji("test");
      }
    }).isInstanceOf(IllegalStateException.class).hasMessage("Please install an EmojiProvider through the EmojiManager.install() method first.");
  }

  @Test public void installEmptyProvider() {
    assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
      @Override public void call() throws Throwable {
        EmojiManager.install(TestEmojiProvider.emptyCategories());
      }
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Your EmojiProvider must at least have one category with at least one emoji.");
  }

  @Test public void installEmptyCategory() {
    assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
      @Override public void call() throws Throwable {
        EmojiManager.install(TestEmojiProvider.emptyEmojis());
      }
    }).isInstanceOf(IllegalArgumentException.class).hasMessage("Your EmojiProvider must at least have one category with at least one emoji.");
  }

  @Test public void installNormalEmoji() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().findEmoji(new String(new int[] { 0x1234 }, 0, 1)))
            .isEqualTo(new Emoji(new int[] { 0x1234 }, R.drawable.emoji_recent, false));
  }

  @Test public void installMultiple() {
    EmojiManager.install(provider);
    EmojiManager.install(provider);

    // No duplicate categories.
    assertThat(EmojiManager.getInstance().getCategories()).hasSize(1);
  }

  @Test public void destroy() {
    EmojiManager.destroy();

    assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
      @Override public void call() throws Throwable {
        EmojiManager.getInstance().findEmoji("test");
      }
    }).isInstanceOf(IllegalStateException.class).hasMessage("Please install an EmojiProvider through the EmojiManager.install() method first.");
  }

  @Test public void findEmojiNormal() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().findEmoji(new String(new int[]{0x5678}, 0, 1)))
            .isEqualTo(new Emoji(new int[]{0x5678}, R.drawable.emoji_backspace, false));
  }

  @Test public void findEmojiEmpty() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().findEmoji("")).isNull();
  }

  @Test public void findAllEmojisNormal() {
    EmojiManager.install(provider);

    final String text = "te" + new String(new int[]{0x5678}, 0, 1)
            + "st" + new String(new int[]{0x1234}, 0, 1);

    final EmojiRange firstExpectedRange = new EmojiRange(2, 3, new Emoji(new int[]{0x5678}, R.drawable.emoji_backspace, false));
    final EmojiRange secondExpectedRange = new EmojiRange(5, 6, new Emoji(new int[]{0x1234}, R.drawable.emoji_recent, false));

    assertThat(EmojiManager.getInstance().findAllEmojis(text))
            .containsExactly(firstExpectedRange, secondExpectedRange);
  }

  @Test public void findAllEmojisEmpty() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().findAllEmojis("")).isEmpty();
  }

  @Test public void findAllEmojisNull() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().findAllEmojis(null)).isEmpty();
  }

  @Test public void simple() {
    EmojiManager.install(provider);

    final Spannable text = new SpannableString(new String(new int[] { 0x1234 }, 0, 1));

    EmojiManager.getInstance().replaceWithImages(RuntimeEnvironment.application, text, 44, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(1);
  }

  @Test public void inString() {
    EmojiManager.install(provider);

    final Spannable text = new SpannableString("test" + new String(new int[] { 0x1234 }, 0, 1) + "abc");

    EmojiManager.getInstance().replaceWithImages(RuntimeEnvironment.application, text, 22, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(1);
  }

  @Test public void multiple() {
    EmojiManager.install(provider);

    final Spannable text = new SpannableString(new String(new int[] { 0x1234 }, 0, 1) + new String(new int[] { 0x5678 }, 0, 1));

    EmojiManager.getInstance().replaceWithImages(RuntimeEnvironment.application, text, 22, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(2);
  }

  @Test public void multipleInString() {
    EmojiManager.install(provider);

    final Spannable text = new SpannableString("abc" + new String(new int[] { 0x1234 }, 0, 1) + "cba" + new String(new int[] { 0x5678 }, 0, 1) + "xyz");

    EmojiManager.getInstance().replaceWithImages(RuntimeEnvironment.application, text, 22, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(2);
  }

  @Test public void halfPath() {
    EmojiManager.install(provider);

    final Spannable text = new SpannableString(new String(new int[] { 0x1234, 0x4321 }, 0, 1));

    EmojiManager.getInstance().replaceWithImages(RuntimeEnvironment.application, text, 11, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(1);
  }

  @Test public void fullPath() {
    EmojiManager.install(provider);

    final Spannable text = new SpannableString(new String(new int[] { 0x1234, 0x4321, 0x9999 }, 0, 1));

    EmojiManager.getInstance().replaceWithImages(RuntimeEnvironment.application, text, 22, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(1);
  }

  @Test public void empty() {
    EmojiManager.install(provider);

    final Spannable text = new SpannableString("");

    EmojiManager.getInstance().replaceWithImages(RuntimeEnvironment.application, text, 22, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(0);
  }

  @Test public void noneInString() {
    EmojiManager.install(provider);

    final Spannable text = new SpannableString("abcdefg");

    EmojiManager.getInstance().replaceWithImages(RuntimeEnvironment.application, text, 22, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(0);
  }
}
