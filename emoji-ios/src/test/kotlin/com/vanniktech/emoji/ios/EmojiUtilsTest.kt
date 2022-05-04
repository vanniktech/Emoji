package com.vanniktech.emoji.ios

import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.emojisCount
import com.vanniktech.emoji.isOnlyEmojis
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
    assertEquals(true, s.isOnlyEmojis())
    assertEquals(3, s.emojisCount())
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
      assertEquals(it, false, "f$it".isOnlyEmojis())
      assertEquals(it, false, "${it}f".isOnlyEmojis())
      assertEquals(it, 1, it.emojisCount())
      assertEquals(it, true, it.isOnlyEmojis())
    }
  }
}
