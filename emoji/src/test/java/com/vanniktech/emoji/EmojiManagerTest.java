package com.vanniktech.emoji;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner.class)
public class EmojiManagerTest {
  private EmojiProvider provider;

  @Before public void setUp() {
    provider = new EmojiProvider() {
      @NonNull @Override public Map<String, EmojiCategory> getCategories() {
        final Map<String, EmojiCategory> result = new HashMap<>();

        result.put("Test", new EmojiCategory() {
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
        });

        return result;
      }
    };
  }

  @After public void tearDown() {
    EmojiManager.destroy();
  }

  @Test public void installNormalCategory() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().getCategories()).isNotEmpty();
  }

  @Test public void installNormalEmoji() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().findEmoji(new String(new int[] { 0x1234 }, 0, 1))).isNotNull();
  }

  @Test(expected = IllegalStateException.class) public void getCategoriesNotInstalled() {
    EmojiManager.getInstance().getCategories();
  }

  @Test(expected = IllegalStateException.class) public void findEmojiNotInstalled() {
    EmojiManager.getInstance().findEmoji("abc");
  }

  @Test(expected = IllegalStateException.class) public void installMultiple() {
    EmojiManager.install(provider);
    EmojiManager.install(provider);
  }

  @Test public void findEmojiEmpty() {
    EmojiManager.install(provider);

    assertThat(EmojiManager.getInstance().findEmoji("")).isNull();
  }
}
