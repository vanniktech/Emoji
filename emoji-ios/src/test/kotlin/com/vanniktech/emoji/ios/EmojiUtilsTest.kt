package com.vanniktech.emoji.ios

import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.emojiInformation
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

  @Test fun emojiInformationEmojisOnly() {
    val mage1 = """🧙‍♀️"""
    val mage2 = """🧙🏿‍♀️"""
    val text = "$mage1$mage2"
    val emojiInformation = text.emojiInformation()
    assertEquals(true, emojiInformation.isOnlyEmojis)
    assertEquals(2, emojiInformation.emojis.size)

    assertEquals(0, emojiInformation.emojis[0].start)
    assertEquals(5, emojiInformation.emojis[0].end)
    assertEquals(mage1, text.substring(emojiInformation.emojis[0].start, emojiInformation.emojis[0].end))
    assertEquals(mage1, emojiInformation.emojis[0].emoji.unicode)

    assertEquals(5, emojiInformation.emojis[1].start)
    assertEquals(12, emojiInformation.emojis[1].end)
    assertEquals(mage2, text.substring(emojiInformation.emojis[1].start, emojiInformation.emojis[1].end))
    assertEquals(mage2, emojiInformation.emojis[1].emoji.unicode)
  }

  @Test fun emojiInformationEmojisMixed() {
    val hamburger = """🍔"""
    val cheese = """🧀"""
    val text = """I like $hamburger with lots of $cheese"""
    val emojiInformation = text.emojiInformation()
    assertEquals(false, emojiInformation.isOnlyEmojis)
    assertEquals(2, emojiInformation.emojis.size)

    assertEquals(7, emojiInformation.emojis[0].start)
    assertEquals(9, emojiInformation.emojis[0].end)
    assertEquals(hamburger, text.substring(emojiInformation.emojis[0].start, emojiInformation.emojis[0].end))
    assertEquals(hamburger, emojiInformation.emojis[0].emoji.unicode)

    assertEquals(23, emojiInformation.emojis[1].start)
    assertEquals(25, emojiInformation.emojis[1].end)
    assertEquals(cheese, text.substring(emojiInformation.emojis[1].start, emojiInformation.emojis[1].end))
    assertEquals(cheese, emojiInformation.emojis[1].emoji.unicode)
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
