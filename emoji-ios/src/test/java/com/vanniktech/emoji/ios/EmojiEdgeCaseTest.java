package com.vanniktech.emoji.ios;

import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.EmojiUtils;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class EmojiEdgeCaseTest {
  @Before public void setUp() {
    EmojiManager.install(new IosEmojiProvider());
  }

  @Test public void starWithVariantSelector() {
    String s = "⭐️⭐️⭐️";

    assertThat(EmojiUtils.emojisCount(s)).isEqualTo(3);
    assertThat(EmojiUtils.isOnlyEmojis(s)).isTrue();
  }
}
