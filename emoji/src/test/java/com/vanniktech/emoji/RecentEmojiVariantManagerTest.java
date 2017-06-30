package com.vanniktech.emoji;

import android.app.Application;
import com.vanniktech.emoji.RecentEmojiVariantManager.Disposable;
import com.vanniktech.emoji.emoji.Emoji;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.quality.Strictness.STRICT_STUBS;

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner.class) public final class RecentEmojiVariantManagerTest {
  @Rule public final ExpectedException expectedException = ExpectedException.none();
  @Rule public final MockitoRule mockitoRule = MockitoJUnit.rule().strictness(STRICT_STUBS);

  @Mock private RecentEmojiVariantManager.EmojiVariantListener listener;

  private RecentEmojiVariantManager recentEmojiVariantManager;
  private Application context;

  private Emoji variant1;
  private Emoji variant2;
  private Emoji variant3;
  private Emoji base;
  private Emoji emoji;

  @Before public void setUp() {
    recentEmojiVariantManager = new RecentEmojiVariantManager();

    context = RuntimeEnvironment.application;

    emoji = new Emoji(0x1f437, R.drawable.emoji_recent);

    variant1 = new Emoji(0x1f55b, R.drawable.emoji_recent);
    variant2 = new Emoji(0x1f55c, R.drawable.emoji_recent);
    variant3 = new Emoji(0x1f55d, R.drawable.emoji_recent);
    base = new Emoji(0x1f55a, R.drawable.emoji_recent, variant1, variant2, variant3);
  }

  @After public void tearDown() {
    verifyNoMoreInteractions(listener);
  }

  @Test public void getMostRecentVariantDefault() {
    assertThat(recentEmojiVariantManager.getMostRecentVariant(emoji, context)).isEqualTo(emoji);
  }

  @Test public void getMostRecentVariantUsingOnlyVariants() {
    recentEmojiVariantManager.addRecentVariant(variant2);

    assertThat(recentEmojiVariantManager.getMostRecentVariant(base, context)).isEqualTo(variant2);
    assertThat(recentEmojiVariantManager.getMostRecentVariant(variant1, context)).isEqualTo(variant2);
    assertThat(recentEmojiVariantManager.getMostRecentVariant(variant2, context)).isEqualTo(variant2);
    assertThat(recentEmojiVariantManager.getMostRecentVariant(variant3, context)).isEqualTo(variant2);
  }

  @Test public void getMostRecentVariantUsingOnlyVariantsBeforeBase() {
    recentEmojiVariantManager.addRecentVariant(variant1);
    recentEmojiVariantManager.addRecentVariant(base);

    assertThat(recentEmojiVariantManager.getMostRecentVariant(variant1, context)).isEqualTo(base);
  }

  @Test public void getMostRecentVariantUsingSame() {
    recentEmojiVariantManager.addRecentVariant(variant1);
    recentEmojiVariantManager.addRecentVariant(variant1);

    assertThat(recentEmojiVariantManager.getMostRecentVariant(variant1, context)).isEqualTo(variant1);
  }

  @Test public void listener() {
    recentEmojiVariantManager.addListener(base, listener);

    recentEmojiVariantManager.addRecentVariant(variant1);
    verify(listener).onChanged(variant1);
  }

  @Test public void listenerDispose() {
    final Disposable disposable = recentEmojiVariantManager.addListener(base, listener);

    recentEmojiVariantManager.addRecentVariant(variant1);
    verify(listener).onChanged(variant1);

    disposable.dispose();
    recentEmojiVariantManager.addRecentVariant(variant1);
    verify(listener, times(1)).onChanged(variant1);
  }

  @Test public void listenerOnlySupportsBase() {
    expectedException.expect(IllegalStateException.class);
    expectedException.expectMessage("You can only listen with using the base emoji.");
    recentEmojiVariantManager.addListener(variant1, listener);
  }

  @Test public void persist() {
    recentEmojiVariantManager.addRecentVariant(variant1);
    recentEmojiVariantManager.addRecentVariant(variant2);

    recentEmojiVariantManager.persist(context);

    EmojiManager.install(TestEmojiProvider.from(variant1, variant2));
    final RecentEmojiVariantManager sharedPrefsManager = new RecentEmojiVariantManager();

    assertThat(sharedPrefsManager.getMostRecentVariant(base, context)).isEqualTo(variant2);
  }
}
