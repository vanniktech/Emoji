package com.vanniktech.emoji

import android.content.Context
import com.vanniktech.emoji.emoji.Emoji

class TestEmoji(
  codePoints: IntArray,
  shortcodes: Array<String>,
  isDuplicate: Boolean,
  vararg variants: Emoji,
) : Emoji(codePoints, shortcodes, isDuplicate, *variants) {
  override fun getDrawable(context: Context) = error("Not available from tests")
  override fun destroy() = Unit
}
