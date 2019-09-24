package com.vanniktech.emoji;

import com.vanniktech.emoji.emoji.Emoji;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Java6Assertions.assertThat;

public final class EmojiInformationTest {
  private EmojiInformation empty;
  private EmojiInformation empty2;
  private EmojiInformation one;
  private EmojiInformation one2;

  @Before public void setUp() {
    final List<EmojiRange> emptyList = emptyList();
    empty = new EmojiInformation(false, emptyList);
    empty2 = new EmojiInformation(false, emptyList);

    final Emoji emoji = new Emoji(new int[] { 0x1234 }, R.drawable.emoji_recent, false);
    one = new EmojiInformation(false, singletonList(new EmojiRange(0, 1, emoji)));
    one2 = new EmojiInformation(false, singletonList(new EmojiRange(0, 1, emoji)));
  }

  @Test public void equality() {
    assertThat(empty).isEqualTo(empty2);
    assertThat(one).isEqualTo(one2);

    assertThat(one).isNotEqualTo(empty);
    assertThat(empty).isNotEqualTo(one);
  }

  @Test public void hashy() {
    assertThat(empty.hashCode()).isEqualTo(empty2.hashCode());
    assertThat(one.hashCode()).isEqualTo(one2.hashCode());

    assertThat(one.hashCode()).isNotEqualTo(empty.hashCode());
    assertThat(empty.hashCode()).isNotEqualTo(one.hashCode());
  }
}
