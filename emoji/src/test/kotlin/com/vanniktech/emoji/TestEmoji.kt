package com.vanniktech.emoji

import android.content.Context
import com.vanniktech.emoji.emoji.Emoji

class TestEmoji(
  codePoints: IntArray,
  override val shortcodes: Array<String>,
  override val isDuplicate: Boolean,
  override val variants: List<TestEmoji> = emptyList(),
) : Emoji {
  override val unicode: String = String(codePoints, 0, codePoints.size)

  private var parent: TestEmoji? = null

  override val base by lazy(LazyThreadSafetyMode.NONE) {
    var result = this
    while (result.parent != null) {
      result = result.parent!!
    }
    result
  }

  init {
    @Suppress("LeakingThis")
    for (variant in variants) {
      variant.parent = this
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }
    val emoji = other as TestEmoji
    return (
      unicode == emoji.unicode && shortcodes.contentEquals(emoji.shortcodes) &&
        variants == emoji.variants
      )
  }

  override fun hashCode(): Int {
    var result = unicode.hashCode()
    result = 31 * result + shortcodes.contentHashCode()
    result = 31 * result + variants.hashCode()
    return result
  }

  override fun getDrawable(context: Context) = error("Not available from tests")
  override fun destroy() = Unit
}
