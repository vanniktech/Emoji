package com.vanniktech.emoji

data class TestEmoji(
  override val unicode: String,
  override val shortcodes: List<String>,
  override val isDuplicate: Boolean,
  override val variants: List<TestEmoji> = emptyList(),
  private var parent: TestEmoji? = null,
) : Emoji {
  constructor(
    codePoints: IntArray,
    shortcodes: List<String>,
    isDuplicate: Boolean,
    variants: List<TestEmoji> = emptyList(),
  ) : this(String(codePoints, 0, codePoints.size), shortcodes, isDuplicate, variants)

  override val base by lazy(LazyThreadSafetyMode.NONE) {
    var result = this
    while (result.parent != null) {
      result = result.parent!!
    }
    result
  }

  init {
    for (variant in variants) {
      variant.parent = this
    }
  }
}
