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
    recentEmojiManager.addEmoji(new Emoji(0x1f437, new String[]{"test"}, R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0x1f43d, new String[]{"test"}, R.drawable.emoji_recent, false));

    assertThat(recentEmojiManager.getRecentEmojis()).hasSize(2)
        .containsExactly(
            new Emoji(0x1f43d, new String[]{"test"}, R.drawable.emoji_recent, false),
            new Emoji(0x1f437, new String[]{"test"}, R.drawable.emoji_recent, false));
  }

  @Test public void persist() {
    final Emoji firstEmoji = new Emoji(0x1f437, new String[]{"test"}, R.drawable.emoji_recent, false);
    recentEmojiManager.addEmoji(firstEmoji);
    final Emoji secondEmoji = new Emoji(0x1f43d, new String[]{"test"}, R.drawable.emoji_recent, false);
    recentEmojiManager.addEmoji(secondEmoji);

    recentEmojiManager.persist();

    final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
    assertThat(recentEmojis).hasSize(2).containsExactly(secondEmoji, firstEmoji);
  }

  @Test public void duplicateEmojis() {
    final Emoji emoji = new Emoji(0x1f437, new String[]{"test"}, R.drawable.emoji_recent, false);
    recentEmojiManager.addEmoji(emoji);
    recentEmojiManager.addEmoji(emoji);
    recentEmojiManager.persist();

    final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
    assertThat(recentEmojis).hasSize(1).containsExactly(emoji);
  }

  @Test public void inOrder() {
    recentEmojiManager.addEmoji(new Emoji(0x1f55a, new String[]{"test"}, R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0x1f561, new String[]{"test"}, R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0x1f4e2, new String[]{"test"}, R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0x1f562, new String[]{"test"}, R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0xe535, new String[]{"test"}, R.drawable.emoji_recent, false));
    recentEmojiManager.addEmoji(new Emoji(0x1f563, new String[]{"test"}, R.drawable.emoji_recent, false));

    recentEmojiManager.persist();

    final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
    assertThat(recentEmojis).containsExactly(
        new Emoji(0x1f563, new String[]{"test"}, R.drawable.emoji_recent, false),
        new Emoji(0xe535, new String[]{"test"}, R.drawable.emoji_recent, false),
        new Emoji(0x1f562, new String[]{"test"}, R.drawable.emoji_recent, false),
        new Emoji(0x1f4e2, new String[]{"test"}, R.drawable.emoji_recent, false),
        new Emoji(0x1f561, new String[]{"test"}, R.drawable.emoji_recent, false),
        new Emoji(0x1f55a, new String[]{"test"}, R.drawable.emoji_recent, false));
  }

  @Test public void newShouldReplaceOld() {
    final Emoji first = new Emoji(0x2764, new String[]{"test"}, R.drawable.emoji_recent, false);
    final Emoji second = new Emoji(0x1f577, new String[]{"test"}, R.drawable.emoji_recent, false);

    recentEmojiManager.addEmoji(first);
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(first);

    recentEmojiManager.addEmoji(second);
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(second, first);

    recentEmojiManager.addEmoji(first);
    assertThat(recentEmojiManager.getRecentEmojis()).containsExactly(first, second);
  }

  @Test public void addSkinTone() {
    final Emoji variant1 = new Emoji(0x1f55b, new String[]{"test"}, R.drawable.emoji_recent, false);
    final Emoji variant2 = new Emoji(0x1f55c, new String[]{"test"}, R.drawable.emoji_recent, false);
    final Emoji variant3 = new Emoji(0x1f55d, new String[]{"test"}, R.drawable.emoji_recent, false);
    final Emoji base = new Emoji(0x1f55a, new String[]{"test"}, R.drawable.emoji_recent, false, variant1, variant2, variant3);

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
      recentEmojiManager.addEmoji(new Emoji(i, new String[]{"test"}, R.drawable.emoji_recent, false));
    }

    assertThat(recentEmojiManager.getRecentEmojis()).hasSize(40);
  }
}
