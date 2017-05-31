package com.vanniktech.emoji;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner.class) public class EmojiManagerTest {
  @Rule public final ExpectedException expectedException = ExpectedException.none();

  private EmojiProvider provider;

  @Before public void setUp() {
    provider = new EmojiProvider() {
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
        } };
      }
    };
  }

  @Test public void installNormalCategory() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().getCategories()).isNotEmpty();
  }

  @Test public void installEmptyCategory() {
    final EmojiProvider emptyProvider = new EmojiProvider() {
      @NonNull @Override public EmojiCategory[] getCategories() {
        return new EmojiCategory[0];
      }
    };

    assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
      @Override
      public void call() throws Throwable {
        EmojiManager.install(emptyProvider);
      }
    }).isInstanceOf(IllegalArgumentException.class);
  }

  @Test public void installNormalEmoji() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().findEmoji(new String(new int[] { 0x1234 }, 0, 1)))
            .isEqualTo(new Emoji(new int[] { 0x1234 }, R.drawable.emoji_recent));
  }

  @Test public void installMultiple() {
    EmojiManager.install(provider);
    EmojiManager.install(provider);

    // No duplicate categories.
    assertThat(EmojiManager.getInstance().getCategories()).hasSize(1);
  }

  @Test public void findEmojiEmpty() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().findEmoji("")).isNull();
  }

  @Test public void findAllEmojisEmpty() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().findAllEmojis("")).isEmpty();
  }
}
