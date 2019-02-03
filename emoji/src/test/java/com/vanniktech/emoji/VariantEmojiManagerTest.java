package com.vanniktech.emoji;

import com.vanniktech.emoji.emoji.Emoji;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Config(manifest = Config.NONE) @RunWith(RobolectricTestRunner.class) public final class VariantEmojiManagerTest {
  @Rule public final ExpectedException expectedException = ExpectedException.none();

  private VariantEmojiManager variantEmojiManager;

  private Emoji variant1;
  private Emoji variant2;
  private Emoji variant3;
  private Emoji base;
  private Emoji emoji;

  @Before public void setUp() {
    variantEmojiManager = new VariantEmojiManager(RuntimeEnvironment.application);

    emoji = new Emoji(0x1f437, R.drawable.emoji_recent, false);
    variant1 = new Emoji(0x1f55b, R.drawable.emoji_recent, false);
    variant2 = new Emoji(0x1f55c, R.drawable.emoji_recent, false);
    variant3 = new Emoji(0x1f55d, R.drawable.emoji_recent, false);
    base = new Emoji(0x1f55a, R.drawable.emoji_recent, false, variant1, variant2, variant3);
  }

  @Test public void getVariantDefault() {
    assertThat(variantEmojiManager.getVariant(emoji)).isEqualTo(emoji);
  }

  @Test public void getVariantUsingOnlyVariants() {
    variantEmojiManager.addVariant(variant2);

    assertThat(variantEmojiManager.getVariant(base)).isEqualTo(variant2);
    assertThat(variantEmojiManager.getVariant(variant1)).isEqualTo(variant2);
    assertThat(variantEmojiManager.getVariant(variant2)).isEqualTo(variant2);
    assertThat(variantEmojiManager.getVariant(variant3)).isEqualTo(variant2);
  }

  @Test public void getVariantUsingOnlyVariantsBeforeBase() {
    variantEmojiManager.addVariant(variant1);
    variantEmojiManager.addVariant(base);

    assertThat(variantEmojiManager.getVariant(variant1)).isEqualTo(base);
  }

  @Test public void getVariantUsingSame() {
    variantEmojiManager.addVariant(variant1);
    variantEmojiManager.addVariant(variant1);

    assertThat(variantEmojiManager.getVariant(variant1)).isEqualTo(variant1);
  }

  @Test public void persist() {
    variantEmojiManager.addVariant(variant1);
    variantEmojiManager.addVariant(variant2);

    variantEmojiManager.persist();

    EmojiManager.install(TestEmojiProvider.from(variant1, variant2));
    final VariantEmojiManager sharedPrefsManager = new VariantEmojiManager(RuntimeEnvironment.application);

    assertThat(sharedPrefsManager.getVariant(base)).isEqualTo(variant2);
  }

  @Test public void persistEmpty() {
    variantEmojiManager.persist();

    EmojiManager.install(TestEmojiProvider.from(variant1, variant2));
    final VariantEmojiManager sharedPrefsManager = new VariantEmojiManager(RuntimeEnvironment.application);

    assertThat(sharedPrefsManager.getVariant(base)).isEqualTo(base);
  }
}
