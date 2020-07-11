/*
 * Copyright (C) 2016 - Niklas Baudy, Ruben Gees, Mario Đanić and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.vanniktech.emoji;

import com.vanniktech.emoji.emoji.Emoji;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public final class EmojiRangeTest {
  private Emoji emoji;
  private Emoji emoji2;

  @Before public void setUp() {
    emoji = new Emoji(new int[] { 0x1234 }, R.drawable.emoji_recent, false);
    emoji2 = new Emoji(new int[] { 0x5678 }, R.drawable.emoji_backspace, false);
  }

  @Test public void equality() {
    assertThat(new EmojiRange(0, 1, emoji)).isEqualTo(new EmojiRange(0, 1, emoji));
    assertThat(new EmojiRange(0, 1, emoji2)).isEqualTo(new EmojiRange(0, 1, emoji2));

    assertThat(new EmojiRange(0, 0, emoji2)).isNotEqualTo(new EmojiRange(0, 1, emoji2));
    assertThat(new EmojiRange(10, 0, emoji2)).isNotEqualTo(new EmojiRange(10, 1, emoji2));

    assertThat(new EmojiRange(0, 1, emoji)).isNotEqualTo(new EmojiRange(0, 1, emoji2));
  }

  @Test public void hashy() {
    assertThat(new EmojiRange(0, 1, emoji).hashCode()).isEqualTo(new EmojiRange(0, 1, emoji).hashCode());
    assertThat(new EmojiRange(0, 1, emoji2).hashCode()).isEqualTo(new EmojiRange(0, 1, emoji2).hashCode());

    assertThat(new EmojiRange(0, 0, emoji2).hashCode()).isNotEqualTo(new EmojiRange(0, 1, emoji2).hashCode());
    assertThat(new EmojiRange(10, 0, emoji2).hashCode()).isNotEqualTo(new EmojiRange(10, 1, emoji2).hashCode());

    assertThat(new EmojiRange(0, 1, emoji).hashCode()).isNotEqualTo(new EmojiRange(0, 1, emoji2).hashCode());
  }
}
