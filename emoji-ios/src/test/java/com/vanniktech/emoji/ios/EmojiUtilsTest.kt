package com.vanniktech.emoji.ios

import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.EmojiUtils
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class EmojiUtilsTest {
  @Before fun setUp() {
    EmojiManager.install(IosEmojiProvider())
  }

  @Test fun starWithVariantSelector() {
    val s = "⭐️⭐️⭐️"
    assertEquals(true, EmojiUtils.isOnlyEmojis(s))
    assertEquals(3, EmojiUtils.emojisCount(s))
  }

  @Ignore("https://github.com/vanniktech/Emoji/issues/485")
  @Test fun isOnlyEmojis() {
    val emojis = listOf(
      """🗯""",
      """🗨""",
      """🕳""",
      """❤️""",
      """❣️️""",
    )

    emojis.forEach {
      assertEquals(it, false, EmojiUtils.isOnlyEmojis("f$it"))
      assertEquals(it, false, EmojiUtils.isOnlyEmojis("${it}f"))
      assertEquals(it, 1, EmojiUtils.emojisCount(it))
      assertEquals(it, true, EmojiUtils.isOnlyEmojis(it))
    }
  }
}
