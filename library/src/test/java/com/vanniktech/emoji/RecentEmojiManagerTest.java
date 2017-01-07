package com.vanniktech.emoji;

import com.vanniktech.emoji.emoji.Emoji;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class RecentEmojiManagerTest {
    private RecentEmoji recentEmojiManager;

    @Before
    public void setUp() {
        recentEmojiManager = new RecentEmojiManager(RuntimeEnvironment.application);
    }

    @Test
    public void getRecentEmojis() {
        assertThat(recentEmojiManager.getRecentEmojis()).isEmpty();
    }

    @Test
    public void addEmoji() {
        recentEmojiManager.addEmoji(Emoji.fromCodePoint(0x1f437));
        recentEmojiManager.addEmoji(Emoji.fromCodePoint(0x1f43d));

        assertThat(recentEmojiManager.getRecentEmojis()).hasSize(2).containsExactly(Emoji.fromCodePoint(0x1f43d), Emoji.fromCodePoint(0x1f437));
    }

    @Test
    public void persist() {
        final Emoji firstEmoji = Emoji.fromCodePoint(0x1f437);
        recentEmojiManager.addEmoji(firstEmoji);
        final Emoji secondEmoji = Emoji.fromCodePoint(0x1f43d);
        recentEmojiManager.addEmoji(secondEmoji);

        recentEmojiManager.persist();

        final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
        assertThat(recentEmojis).hasSize(2).containsExactly(secondEmoji, firstEmoji);
    }

    @Test
    public void duplicateEmojis() {
        final Emoji emoji = Emoji.fromCodePoint(0x1f437);
        recentEmojiManager.addEmoji(emoji);
        recentEmojiManager.addEmoji(emoji);
        recentEmojiManager.persist();

        final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
        assertThat(recentEmojis).hasSize(1).containsExactly(emoji);
    }

    @Test
    public void inOrder() {
        recentEmojiManager.addEmoji(Emoji.fromCodePoint(0x1f55a));
        recentEmojiManager.addEmoji(Emoji.fromCodePoint(0x1f561));
        recentEmojiManager.addEmoji(Emoji.fromCodePoint(0x1f4e2));
        recentEmojiManager.addEmoji(Emoji.fromCodePoint(0x1f562));
        recentEmojiManager.addEmoji(Emoji.fromChar((char) 0xe535));
        recentEmojiManager.addEmoji(Emoji.fromCodePoint(0x1f563));

        recentEmojiManager.persist();

        final Collection<Emoji> recentEmojis = recentEmojiManager.getRecentEmojis();
        assertThat(recentEmojis).hasSize(6).containsExactly(Emoji.fromCodePoint(0x1f563), Emoji.fromChar((char) 0xe535), Emoji.fromCodePoint(0x1f562), Emoji.fromCodePoint(0x1f4e2), Emoji.fromCodePoint(0x1f561), Emoji.fromCodePoint(0x1f55a));
    }

    @Test
    public void newShouldReplaceOld() {
        recentEmojiManager.addEmoji(Emoji.fromChar((char) 0x2764));
        assertThat(recentEmojiManager.getRecentEmojis()).hasSize(1).containsExactly(Emoji.fromChar((char) 0x2764));

        recentEmojiManager.addEmoji(Emoji.fromCodePoint(0x1f577));
        assertThat(recentEmojiManager.getRecentEmojis()).hasSize(2).containsExactly(Emoji.fromCodePoint(0x1f577), Emoji.fromChar((char) 0x2764));

        recentEmojiManager.addEmoji(Emoji.fromChar((char) 0x2764));
        assertThat(recentEmojiManager.getRecentEmojis()).hasSize(2).containsExactly(Emoji.fromChar((char) 0x2764), Emoji.fromCodePoint(0x1f577));
    }

    @Test
    public void maxRecents() {
        for (int i = 0; i < 500; i++) {
            recentEmojiManager.addEmoji(Emoji.fromChar((char) i));
        }

        assertThat(recentEmojiManager.getRecentEmojis()).hasSize(40);
    }
}
