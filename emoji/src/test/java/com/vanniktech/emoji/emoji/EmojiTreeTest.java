package com.vanniktech.emoji.emoji;

import com.vanniktech.emoji.R;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmojiTreeTest {
  private EmojiTree tree;

  @Before public void setUp() {
    tree = new EmojiTree();
  }

  @Test public void simple() {
    final Emoji emoji = new Emoji(0x1234, R.drawable.emoji_recent);

    tree.add(emoji);

    assertThat(tree.findEmoji(new String(new int[] { 0x1234 }, 0, 1))).isSameAs(emoji);
  }

  @Test public void samePath() {
    final Emoji emoji = new Emoji(0x1234, R.drawable.emoji_recent);
    final Emoji emoji2 = new Emoji(new int[] { 0x1234, 0x4321 }, R.drawable.emoji_recent);

    tree.add(emoji);
    tree.add(emoji2);

    assertThat(tree.findEmoji(new String(new int[] { 0x1234, 0x4321 }, 0, 2))).isSameAs(emoji2);
  }

  @Test public void earlyOnPath() {
    final Emoji emoji = new Emoji(0x1234, R.drawable.emoji_recent);
    final Emoji emoji2 = new Emoji(new int[] { 0x1234, 0x4321 }, R.drawable.emoji_recent);

    tree.add(emoji);
    tree.add(emoji2);

    assertThat(tree.findEmoji(new String(new int[] { 0x1234 }, 0, 1))).isSameAs(emoji);
  }

  @Test public void earlyOnPathNull() {
    final Emoji emoji = new Emoji(new int[] { 0x1234, 0x4321 }, R.drawable.emoji_recent);

    tree.add(emoji);

    assertThat(tree.findEmoji(new String(new int[] { 0x1234 }, 0, 1))).isNull();
  }

  @Test public void middlePath() {
    final Emoji emoji = new Emoji(new int[] { 0x1234 }, R.drawable.emoji_recent);
    final Emoji emoji2 = new Emoji(new int[] { 0x1234, 0x4321 }, R.drawable.emoji_recent);
    final Emoji emoji3 = new Emoji(new int[] { 0x1234, 0x4321, 0x1111 }, R.drawable.emoji_recent);

    tree.add(emoji);
    tree.add(emoji2);
    tree.add(emoji3);

    assertThat(tree.findEmoji(new String(new int[] { 0x1234, 0x4321 }, 0, 2))).isSameAs(emoji2);
  }

  @Test public void partial() {
    final Emoji emoji = new Emoji(new int[] { 0x1234 }, R.drawable.emoji_recent);
    final Emoji emoji2 = new Emoji(new int[] { 0x1234, 0x4321, 0x1111 }, R.drawable.emoji_recent);

    tree.add(emoji);
    tree.add(emoji2);

    assertThat(tree.findEmoji(new String(new int[] { 0x1234, 0x4321 }, 0, 2))).isSameAs(emoji);
  }

  @Test public void empty() {
    assertThat(tree.findEmoji(new String(new int[] { 0x1234 }, 0, 1))).isNull();
  }
}
