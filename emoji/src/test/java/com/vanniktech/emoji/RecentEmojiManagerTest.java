package com.vanniktech.emoji;

import com.vanniktech.emoji.emoji.Emoji;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import java.util.Collection;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner.class) public class RecentEmojiManagerTest {
  private RecentEmoji recentEmojiManager;

  @Before public void setUp() {
    recentEmojiManager = new RecentEmojiManager(RuntimeEnvironment.application);
  }

  @Test public void getRecentEmojis() {
    assertThat(recentEmojiManager.getRecentEmojis()).isEmpty();
  }

  @Test public void addEmoji() {
    recentEmojiManager.addEmoji(new Emoji(0x1f437, "test", R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0x1f43d, "test", R.drawable.emoji_recent, false));

    assertThat(recentEmojiManager.getRecentEmojis()).hasSize(2)
        .containsExactly(
            new Emoji(0x1f43d, "test", R.drawable.emoji_recent, false),
            new Emoji(0x1f437, "test", R.drawable.emoji_recent, false));
  }

  @Test public void persist() {
    final Emoji firstEmoji = new Emoji(0x1f437, "test", R.drawable.emoji_recent, false);
    recentEmojiManager.addEmoji(firstEmoji);
    final Emoji secondEmoji = new Emoji(0x1f43d, "test", R.drawable.emoji_recent, false);
    recentEmojiManager.addEmoji(secondEmoji);

    recentEmojiManager.persist();

    final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
    assertThat(recentEmojis).hasSize(2).containsExactly(secondEmoji, firstEmoji);
  }

  @Test public void duplicateEmojis() {
    final Emoji emoji = new Emoji(0x1f437, "test", R.drawable.emoji_recent, false);
    recentEmojiManager.addEmoji(emoji);
    recentEmojiManager.addEmoji(emoji);
    recentEmojiManager.persist();

    final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
    assertThat(recentEmojis).hasSize(1).containsExactly(emoji);
  }

  @Test public void inOrder() {
    recentEmojiManager.addEmoji(new Emoji(0x1f55a, "test", R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0x1f561, "test", R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0x1f4e2, "test", R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0x1f562, "test", R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0xe535, "test", R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0x1f563, "test", R.drawable.emoji_recent, false));

    recentEmojiManager.persist();

    final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
    assertThat(recentEmojis).containsExactly(
        new Emoji(0x1f563, "test", R.drawable.emoji_recent, false),
        new Emoji(0xe535, "test", R.drawable.emoji_recent, false),
        new Emoji(0x1f562, "test", R.drawable.emoji_recent, false),
        new Emoji(0x1f4e2, "test", R.drawable.emoji_recent, false),
        new Emoji(0x1f561, "test", R.drawable.emoji_recent, false),
        new Emoji(0x1f55a, "test", R.drawable.emoji_recent, false));
  }

  @Test public void newShouldReplaceOld() {
    recentEmojiManager.addEmoji(new Emoji(0x2764, "test", R.drawable.emoji_recent, false));
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(
        new Emoji(0x2764, "test", R.drawable.emoji_recent, false));

    recentEmojiManager.addEmoji(new Emoji(0x1f577, "test", R.drawable.emoji_recent, false));
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(
        new Emoji(0x1f577, "test", R.drawable.emoji_recent, false),
        new Emoji(0x2764, "test", R.drawable.emoji_recent, false));

    recentEmojiManager.addEmoji(new Emoji(0x2764, "test", R.drawable.emoji_recent, false));
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(
        new Emoji(0x2764, "test", R.drawable.emoji_recent, false),
        new Emoji(0x1f577, "test", R.drawable.emoji_recent, false));
  }

  @Test public void addSkinTone() {
    final Emoji variant1 = new Emoji(0x1f55b, "test", R.drawable.emoji_recent, false);
    final Emoji variant2 = new Emoji(0x1f55c, "test", R.drawable.emoji_recent, false);
    final Emoji variant3 = new Emoji(0x1f55d, "test", R.drawable.emoji_recent, false);
    final Emoji base = new Emoji(0x1f55a, "test", R.drawable.emoji_recent, false, variant1, variant2, variant3);

    recentEmojiManager.addEmoji(base);

    recentEmojiManager.addEmoji(variant1);
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(variant1);

    recentEmojiManager.addEmoji(variant2);
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(variant2);

    recentEmojiManager.addEmoji(variant3);
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(variant3);
  }

  @Test public void maxRecents() {
    for (int i = 0; i < 500; i++) {
      recentEmojiManager.addEmoji(new Emoji(i, "test", R.drawable.emoji_recent, false));
    }

    assertThat(recentEmojiManager.getRecentEmojis()).hasSize(40);
  }
}
