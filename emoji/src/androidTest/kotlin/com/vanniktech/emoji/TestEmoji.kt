package com.vanniktech.emoji

import android.content.Context
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize data class TestEmoji(
  override val unicode: String,
  override val shortcodes: List<String>,
  override val isDuplicate: Boolean,
  override val variants: List<TestEmoji> = emptyList(),
  private var parent: TestEmoji? = null,
) : Emoji, Parcelable {
  constructor(
    codePoints: IntArray,
    shortcodes: List<String>,
    isDuplicate: Boolean,
    variants: List<TestEmoji> = emptyList(),
  ) : this(String(codePoints, 0, codePoints.size), shortcodes, isDuplicate, variants)

  @IgnoredOnParcel override val base by lazy(LazyThreadSafetyMode.NONE) {
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

  override fun getDrawable(context: Context) = error("Not available from tests")
  override fun destroy() = Unit
}
