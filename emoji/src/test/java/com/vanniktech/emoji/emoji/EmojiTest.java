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

package com.vanniktech.emoji.emoji;

import com.vanniktech.emoji.R;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class EmojiTest {
  @Test public void multipleCodePoints() {
    final Emoji emoji = new Emoji(new int[]{ 0x1234, 0x5678 }, R.drawable.emoji_backspace, false);

    assertThat(emoji.getUnicode()).hasSize(2).isEqualTo(new String(new int[]{ 0x1234, 0x5678 }, 0, 2));
  }

  @Test public void baseWithoutVariant() {
    final Emoji emoji = new Emoji(0x1234, R.drawable.emoji_backspace, false);

    assertThat(emoji.getBase()).isSameAs(emoji);
  }

  @Test public void baseWithVariant() {
    final Emoji variant = new Emoji(0x4321, R.drawable.emoji_recent, false);
    final Emoji emoji = new Emoji(0x1234, R.drawable.emoji_backspace, false, variant);

    assertThat(variant.getBase()).isSameAs(emoji);
  }

  @Test public void baseWithMultipleVariants() {
    final Emoji variant = new Emoji(0x4321, R.drawable.emoji_recent, false);
    final Emoji variant2 = new Emoji(0x5678, R.drawable.emoji_recent, false);
    final Emoji emoji = new Emoji(0x1234, R.drawable.emoji_backspace, false, variant, variant2);

    assertThat(variant.getBase()).isSameAs(emoji);
    assertThat(variant2.getBase()).isSameAs(emoji);
  }

  @Test public void baseWithRecursiveVariant() {
    final Emoji variantOfVariant = new Emoji(0x4321, R.drawable.emoji_recent, false);
    final Emoji variant = new Emoji(0x5678, R.drawable.emoji_recent, false, variantOfVariant);
    final Emoji emoji = new Emoji(0x1234, R.drawable.emoji_backspace, false, variant);

    assertThat(variantOfVariant.getBase()).isSameAs(emoji);
    assertThat(variant.getBase()).isSameAs(emoji);
  }

  @Test public void hasVariants() {
    assertThat(new Emoji(new int[]{ 0x1234, 0x5678 }, R.drawable.emoji_backspace, false).hasVariants()).isFalse();

    assertThat(new Emoji(0x1f3cb, R.drawable.emoji_backspace, false, new Emoji(new int[] { 0x1f3cb, 0x1f3fb }, R.drawable.emoji_backspace, false)).hasVariants()).isTrue();
  }

  @Test public void getResource() {
    assertThat(new Emoji(new int[]{ 0x1234, 0x5678 }, R.drawable.emoji_backspace, false).getResource()).isEqualTo(R.drawable.emoji_backspace);
  }

  @Test public void getLength() {
    assertThat(new Emoji(0x1234, R.drawable.emoji_backspace, false).getLength()).isEqualTo(1);
    assertThat(new Emoji(new int[]{ 0x1234, 0x5678 }, R.drawable.emoji_backspace, false).getLength()).isEqualTo(2);
  }
}
