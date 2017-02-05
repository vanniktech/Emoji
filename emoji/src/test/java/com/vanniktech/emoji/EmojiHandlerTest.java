package com.vanniktech.emoji;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner.class) public class EmojiHandlerTest {
  @Before public void setUp() {
    EmojiManager.install(new EmojiProvider() {
      @NonNull @Override public EmojiCategory[] getCategories() {
        return new EmojiCategory[] { new EmojiCategory() {
          @NonNull @Override public Emoji[] getEmojis() {
            return new Emoji[] {
              new Emoji(new int[] { 0x1234 }, R.drawable.emoji_recent),
              new Emoji(new int[] { 0x4321 }, R.drawable.emoji_recent),
              new Emoji(new int[] { 0x5678 }, R.drawable.emoji_backspace),
              new Emoji(new int[] { 0x1234, 0x4321, 0x9999 }, R.drawable.emoji_recent)
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
    PrivateConstructorChecker.forClass(EmojiHandler.class)
        .expectedTypeOfException(AssertionError.class)
        .expectedExceptionMessage("No instances.")
        .check();
  }

  @Test public void simple() {
    final Spannable text = new SpannableString(new String(new int[] { 0x1234 }, 0, 1));

    EmojiHandler.addEmojis(RuntimeEnvironment.application, text, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(1);
  }

  @Test public void inString() {
    final Spannable text = new SpannableString("test" + new String(new int[] { 0x1234 }, 0, 1) + "abc");

    EmojiHandler.addEmojis(RuntimeEnvironment.application, text, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(1);
  }

  @Test public void multiple() {
    final Spannable text = new SpannableString(new String(new int[] { 0x1234 }, 0, 1) + new String(new int[] { 0x5678 }, 0, 1));

    EmojiHandler.addEmojis(RuntimeEnvironment.application, text, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(2);
  }

  @Test public void multipleInString() {
    final Spannable text = new SpannableString("abc" + new String(new int[] { 0x1234 }, 0, 1) + "cba" + new String(new int[] { 0x5678 }, 0, 1) + "xyz");

    EmojiHandler.addEmojis(RuntimeEnvironment.application, text, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(2);
  }

  @Test public void halfPath() {
    final Spannable text = new SpannableString(new String(new int[] { 0x1234, 0x4321 }, 0, 1));

    EmojiHandler.addEmojis(RuntimeEnvironment.application, text, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(1);
  }

  @Test public void fullPath() {
    final Spannable text = new SpannableString(new String(new int[] { 0x1234, 0x4321, 0x9999 }, 0, 1));

    EmojiHandler.addEmojis(RuntimeEnvironment.application, text, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(1);
  }

  @Test public void takeLongest() {
    final Spannable text = new SpannableString(new String(new int[] { 0x1234, 0x4321 }, 0, 1));

    EmojiHandler.addEmojis(RuntimeEnvironment.application, text, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(1);
  }

  @Test public void empty() {
    final Spannable text = new SpannableString("");

    EmojiHandler.addEmojis(RuntimeEnvironment.application, text, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(0);
  }

  @Test public void noneInString() {
    final Spannable text = new SpannableString("abcdefg");

    EmojiHandler.addEmojis(RuntimeEnvironment.application, text, 22);

    assertThat(text.getSpans(0, text.length(), EmojiSpan.class)).hasSize(0);
  }
}
