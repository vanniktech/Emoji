package com.vanniktech.emoji.search

import com.vanniktech.emoji.emojiBalloon
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class SearchEmojiResultTest {
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
        emoji = emojiBalloon,
        shortcode = "test",
        range = it,
      )
    }
  }

  @Test fun invalid() {
    try {
      SearchEmojiResult(
        emoji = emojiBalloon,
        shortcode = "test",
        range = 0..4,
      )
      fail("Should have failed")
    } catch (throwable: Throwable) {
      assertEquals(expected = "Index 4 is out of bounds in test", actual = throwable.message)
    }

    try {
      SearchEmojiResult(
        emoji = emojiBalloon,
        shortcode = "test",
        range = -1..4,
      )
      fail("Should have failed")
    } catch (throwable: Throwable) {
      assertEquals(expected = "Index -1 is out of bounds in test", actual = throwable.message)
    }
  }
}
