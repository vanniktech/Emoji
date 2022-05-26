package com.vanniktech.emoji.search

import com.vanniktech.emoji.TestEmoji
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class SearchEmojiResultTest {
  private val emoji = TestEmoji(intArrayOf(0x1f437), listOf("test"), false)

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
