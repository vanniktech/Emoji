package com.vanniktech.emoji.ios

import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.emojiInformation
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class EmojiInformationTest {
  @BeforeTest fun setUp() {
    EmojiManager.install(emojiProvider())
  }

  @Test fun empty() {
    val text = " "
    val emojiInformation = text.emojiInformation()
    assertEquals(expected = text.length, actual = emojiInformation.visualLength)
    assertEquals(expected = false, actual = emojiInformation.isOnlyEmojis)
    assertEquals(expected = 0, actual = emojiInformation.emojiCount)
  }

  @Test fun plain() {
    val text = "Hello World!"
    val emojiInformation = text.emojiInformation()
    assertEquals(expected = text.length, actual = emojiInformation.visualLength)
    assertEquals(expected = false, actual = emojiInformation.isOnlyEmojis)
    assertEquals(expected = 0, actual = emojiInformation.emojiCount)
  }

  @Test fun femaleTinted() {
    val mage1 = """🧙‍♀️"""
    val mage2 = """🧙🏿‍♀️"""
    val text = "$mage1$mage2"
    val emojiInformation = text.emojiInformation()
    assertEquals(expected = 2, actual = emojiInformation.visualLength)
    assertEquals(expected = true, actual = emojiInformation.isOnlyEmojis)
    assertEquals(expected = 2, actual = emojiInformation.emojiCount)

    assertEquals(expected = 0..4, actual = emojiInformation.emojiRanges[0].range)
    assertEquals(expected = mage1, actual = text.substring(emojiInformation.emojiRanges[0].range))
    assertEquals(expected = mage1, actual = emojiInformation.emojiRanges[0].emoji.unicode)

    assertEquals(expected = 5..11, actual = emojiInformation.emojiRanges[1].range)
    assertEquals(expected = mage2, actual = text.substring(emojiInformation.emojiRanges[1].range))
    assertEquals(expected = mage2, actual = emojiInformation.emojiRanges[1].emoji.unicode)
  }

  @Test fun mixed() {
    val hamburger = """🍔"""
    val cheese = """🧀"""
    val text = """I like $hamburger with lots of $cheese"""
    val emojiInformation = text.emojiInformation()
    assertEquals(expected = 23, actual = emojiInformation.visualLength)
    assertEquals(expected = false, actual = emojiInformation.isOnlyEmojis)
    assertEquals(expected = 2, actual = emojiInformation.emojiCount)

    assertEquals(expected = 7..8, actual = emojiInformation.emojiRanges[0].range)
    assertEquals(expected = hamburger, actual = text.substring(emojiInformation.emojiRanges[0].range))
    assertEquals(expected = hamburger, actual = emojiInformation.emojiRanges[0].emoji.unicode)

    assertEquals(expected = 23..24, actual = emojiInformation.emojiRanges[1].range)
    assertEquals(expected = cheese, actual = text.substring(emojiInformation.emojiRanges[1].range))
    assertEquals(expected = cheese, actual = emojiInformation.emojiRanges[1].emoji.unicode)
  }

  @Test fun single() {
    val emojis = listOf(
      """⭐""",
      """🗯""",
      """🗨""",
      """🕳""",
      """❤️""",
      """❣️""",
      """🧑‍🎤""",
      """🎗️""",
      """🎟️""",
      """⛸️""",
    )

    emojis.forEach {
      val prefixed = "f$it".emojiInformation()
      assertEquals(message = it, expected = 2, actual = prefixed.visualLength)
      assertEquals(message = it, expected = 1, actual = prefixed.emojiCount)
      assertEquals(message = it, expected = false, actual = prefixed.isOnlyEmojis)

      val postfixed = "${it}f".emojiInformation()
      assertEquals(message = it, expected = 2, actual = postfixed.visualLength)
      assertEquals(message = it, expected = 1, actual = postfixed.emojiCount)
      assertEquals(message = it, expected = false, actual = postfixed.isOnlyEmojis)

      val single = it.emojiInformation()
      assertEquals(message = it, expected = 1, actual = single.visualLength)
      assertEquals(message = it, expected = 1, actual = single.emojiCount)
      assertEquals(message = it, expected = true, actual = single.isOnlyEmojis)

      val triple = "$it$it$it".emojiInformation()
      assertEquals(message = it, expected = 3, actual = triple.visualLength)
      assertEquals(message = it, expected = 3, actual = triple.emojiCount)
      assertEquals(message = it, expected = true, actual = triple.isOnlyEmojis)

      val spaces = "$it $it".emojiInformation()
      assertEquals(message = it, expected = 3, actual = spaces.visualLength)
      assertEquals(message = it, expected = 2, actual = spaces.emojiCount)
      assertEquals(message = it, expected = true, actual = spaces.isOnlyEmojis)

      assertEquals(expected = triple, actual = triple)
      assertEquals(expected = triple.hashCode(), actual = triple.hashCode())

      assertNotEquals(illegal = single, actual = triple)
      assertNotEquals(illegal = single.hashCode(), actual = triple.hashCode())
    }
  }
}
