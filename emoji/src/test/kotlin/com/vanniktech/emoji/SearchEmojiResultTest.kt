package com.vanniktech.emoji

import com.vanniktech.emoji.emoji.Emoji
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class SearchEmojiResultTest {
  private val emoji = Emoji(0x1f437, arrayOf("test"), R.drawable.emoji_recent, false)

  @Test fun valid() {
    val validRanges = listOf(
      0..3,
      0..2,
      0..1,
      1..3,
      1..2,
    )

    validRanges.forEach {
      SearchEmojiResult(
        emoji = emoji,
        shortcode = "test",
        range = it,
      )
    }
  }

  @Test fun invalid() {
    try {
      SearchEmojiResult(
        emoji = emoji,
        shortcode = "test",
        range = 0..4,
      )
      fail("Should have failed")
    } catch (throwable: Throwable) {
      assertEquals("Index 4 is out of bounds in test", throwable.message)
    }

    try {
      SearchEmojiResult(
        emoji = emoji,
        shortcode = "test",
        range = -1..4,
      )
      fail("Should have failed")
    } catch (throwable: Throwable) {
      assertEquals("Index -1 is out of bounds in test", throwable.message)
    }
  }
}
