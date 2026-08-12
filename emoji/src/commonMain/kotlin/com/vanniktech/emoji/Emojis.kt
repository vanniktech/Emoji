/*
 * Copyright (C) 2016 - Niklas Baudy, Ruben Gees, Mario Đanić and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmName("Emojis")

package com.vanniktech.emoji

import com.vanniktech.emoji.variant.VariantEmoji
import kotlin.jvm.JvmName

private val SPACE_REMOVAL = Regex("[\\s]")

internal const val VARIANT_SELECTOR_16 = '️'

internal fun Emoji.isVariantSelector16() = variants.isNotEmpty() && variants.all { it.unicode == "$unicode$VARIANT_SELECTOR_16" }

/** Returns true when the string contains only emojis. Note that whitespace will be filtered out. */
@Deprecated("Please use emojiInformation() function", replaceWith = ReplaceWith(expression = "this.emojiInformation().isOnlyEmojis"))
fun CharSequence?.isOnlyEmojis(): Boolean {
  if (isNullOrEmpty()) {
    return false
  }

  val inputWithoutSpaces = replace(SPACE_REMOVAL, "")
  return EmojiManager.emojiPattern!!.findAll(inputWithoutSpaces)
    .map { it.range }
    .toList()
    .reversed()
    .fold(inputWithoutSpaces) { string, range -> string.removeRange(range) }
    .isEmpty()
}

/** Returns the emojis that were found in the given text. */
@Deprecated("Please use emojiInformation() function", replaceWith = ReplaceWith(expression = "this.emojiInformation().emojiRanges"))
fun CharSequence?.emojis(): List<EmojiRange> = EmojiManager.findAllEmojis(this)

/** Returns the number of all emojis that were found in the given text. */
@Deprecated("Please use emojiInformation() function", replaceWith = ReplaceWith(expression = "this.emojiInformation().emojiCount"))
fun CharSequence?.emojisCount() = EmojiManager.findAllEmojis(this).size

/** Returns the [EmojiInformation] on the given [CharSequence]. */
fun CharSequence.emojiInformation(): EmojiInformation {
  val emojiRanges = EmojiManager.findAllEmojis(this)
  return EmojiInformation(
    visualLength = length - emojiRanges.sumOf { it.range.last + 1 - it.range.first } + emojiRanges.size,
    isOnlyEmojis = isNotBlank() && emojiRanges.reversed().fold(this) { string, emojiRange -> string.removeRange(emojiRange.range) }.isBlank(),
    emojiRanges = emojiRanges,
  )
}

/**
 * Returns the preferred Unicode representation of this emoji, preferring Variation Selector 16
 * (\uFE0F) to ensure full-color graphical presentation.
 */
fun Emoji.preferredUnicode(): String {
  if (unicode.contains(VARIANT_SELECTOR_16)) {
    return unicode
  }
  val vs16Variant = variants.firstOrNull { it.unicode.contains(VARIANT_SELECTOR_16) }
    ?: base.variants.firstOrNull { it.unicode.contains(VARIANT_SELECTOR_16) }
  return vs16Variant?.unicode ?: unicode
}

/**
 * Filters and returns the list of skin tone variants for the given [emoji].
 * Excludes non-variant selector entries (`isVariantSelector16`) and base duplicates.
 */
fun filterMeaningfulVariants(emoji: Emoji, variantEmoji: VariantEmoji? = null): List<Emoji> {
  val rootBase = emoji.base
  if (rootBase.isVariantSelector16()) {
    return emptyList()
  }
  val managedVariants = variantEmoji?.getVariants(rootBase).orEmpty()
  val candidates = managedVariants.ifEmpty { rootBase.variants }
  return candidates.filterNot { variant ->
    variant.isVariantSelector16() || variant.unicode == rootBase.unicode
  }
}
