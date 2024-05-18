package com.vanniktech.emoji

data class TestEmoji internal constructor(
  override val unicode: String,
  override val shortcodes: List<String>,
  override val variants: List<TestEmoji> = emptyList(),
  private var parent: TestEmoji? = null,
) : Emoji {
  constructor(
    codePoints: IntArray,
    shortcodes: List<String>,
    variants: List<TestEmoji> = emptyList(),
  ) : this(String(codePoints, 0, codePoints.size), shortcodes, variants)

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
