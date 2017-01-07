package com.vanniktech.emoji;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Config(sdk = 21, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class EmojiViewTest {
    private EmojiView emojiView;
    private RecentEmoji recentEmoji;

    @Before
    public void setUp() {
        recentEmoji = mock(RecentEmoji.class);
        emojiView = new EmojiView(RuntimeEnvironment.application, null, recentEmoji);
    }

    @Test
    public void onPageSelectedRecentShouldUpdateRecentEmojis() {
        verify(recentEmoji, times(1)).getRecentEmojis(); // During init phase it is called

        emojiView.onPageSelected(2);
        verify(recentEmoji, times(1)).getRecentEmojis(); // Should not be called again

        emojiView.onPageSelected(0);
        verify(recentEmoji, times(2)).getRecentEmojis(); // Should be to update view

        emojiView.onPageSelected(0);
        verify(recentEmoji, times(2)).getRecentEmojis(); // Should not update again
    }
}
