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
    EmojiManager.install(emojiProvider())
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

    assertEquals(0..5, emojiInformation.emojis[0].range)
    assertEquals(mage1, text.substring(emojiInformation.emojis[0].range.first, emojiInformation.emojis[0].range.last))
    assertEquals(mage1, emojiInformation.emojis[0].emoji.unicode)

    assertEquals(5..12, emojiInformation.emojis[1].range)
    assertEquals(mage2, text.substring(emojiInformation.emojis[1].range.first, emojiInformation.emojis[1].range.last))
    assertEquals(mage2, emojiInformation.emojis[1].emoji.unicode)
  }

  @Test fun emojiInformationEmojisMixed() {
    val hamburger = """🍔"""
    val cheese = """🧀"""
    val text = """I like $hamburger with lots of $cheese"""
    val emojiInformation = text.emojiInformation()
    assertEquals(false, emojiInformation.isOnlyEmojis)
    assertEquals(2, emojiInformation.emojis.size)

    assertEquals(7..9, emojiInformation.emojis[0].range)
    assertEquals(hamburger, text.substring(emojiInformation.emojis[0].range.first, emojiInformation.emojis[0].range.last))
    assertEquals(hamburger, emojiInformation.emojis[0].emoji.unicode)

    assertEquals(23..25, emojiInformation.emojis[1].range)
    assertEquals(cheese, text.substring(emojiInformation.emojis[1].range.first, emojiInformation.emojis[1].range.last))
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
