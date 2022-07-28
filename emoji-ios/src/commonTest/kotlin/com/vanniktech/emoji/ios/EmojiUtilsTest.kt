package com.vanniktech.emoji.ios

import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.emojiInformation
import com.vanniktech.emoji.emojisCount
import com.vanniktech.emoji.isOnlyEmojis
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class EmojiUtilsTest {
  @BeforeTest fun setUp() {
    EmojiManager.install(emojiProvider())
  }

  @Test fun starWithVariantSelector() {
    val s = "⭐️⭐️⭐️"
    assertEquals(expected = true, actual = s.isOnlyEmojis())
    assertEquals(expected = 3, actual = s.emojisCount())
  }

  @Test fun emojiInformationEmojisOnly() {
    val mage1 = """🧙‍♀️"""
    val mage2 = """🧙🏿‍♀️"""
    val text = "$mage1$mage2"
    val emojiInformation = text.emojiInformation()
    assertEquals(expected = true, actual = emojiInformation.isOnlyEmojis)
    assertEquals(expected = 2, actual = emojiInformation.emojis.size)

    assertEquals(expected = 0..5, actual = emojiInformation.emojis[0].range)
    assertEquals(expected = mage1, actual = text.substring(emojiInformation.emojis[0].range.first, emojiInformation.emojis[0].range.last))
    assertEquals(expected = mage1, actual = emojiInformation.emojis[0].emoji.unicode)

    assertEquals(expected = 5..12, actual = emojiInformation.emojis[1].range)
    assertEquals(expected = mage2, actual = text.substring(emojiInformation.emojis[1].range.first, emojiInformation.emojis[1].range.last))
    assertEquals(expected = mage2, actual = emojiInformation.emojis[1].emoji.unicode)
  }

  @Test fun emojiInformationEmojisMixed() {
    val hamburger = """🍔"""
    val cheese = """🧀"""
    val text = """I like $hamburger with lots of $cheese"""
    val emojiInformation = text.emojiInformation()
    assertEquals(expected = false, actual = emojiInformation.isOnlyEmojis)
    assertEquals(expected = 2, actual = emojiInformation.emojis.size)

    assertEquals(expected = 7..9, actual = emojiInformation.emojis[0].range)
    assertEquals(expected = hamburger, actual = text.substring(emojiInformation.emojis[0].range.first, emojiInformation.emojis[0].range.last))
    assertEquals(expected = hamburger, actual = emojiInformation.emojis[0].emoji.unicode)

    assertEquals(expected = 23..25, actual = emojiInformation.emojis[1].range)
    assertEquals(expected = cheese, actual = text.substring(emojiInformation.emojis[1].range.first, emojiInformation.emojis[1].range.last))
    assertEquals(expected = cheese, actual = emojiInformation.emojis[1].emoji.unicode)
  }

  @Ignore // https://github.com/vanniktech/Emoji/issues/485
  @Test fun isOnlyEmojis() {
    val emojis = listOf(
      """🗯""",
      """🗨""",
      """🕳""",
      """❤️""",
      """❣️️""",
    )

    emojis.forEach {
      assertEquals(message = it, expected = false, actual = "f$it".isOnlyEmojis())
      assertEquals(message = it, expected = false, actual = "${it}f".isOnlyEmojis())
      assertEquals(message = it, expected = 1, actual = it.emojisCount())
      assertEquals(message = it, expected = true, actual = it.isOnlyEmojis())
    }
  }
}
