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

package com.vanniktech.emoji

import android.content.Context
import android.text.Spannable
import com.vanniktech.emoji.internal.EmojiSpan

/**
 * EmojiManager where an EmojiProvider can be installed for further usage.
 */
object EmojiManager {
  private const val GUESSED_UNICODE_AMOUNT = 3000
  private const val GUESSED_TOTAL_PATTERN_LENGTH = GUESSED_UNICODE_AMOUNT * 4

  private val emojiMap: MutableMap<String, Emoji> = LinkedHashMap(GUESSED_UNICODE_AMOUNT)
  private var emojiDrawableProvider: EmojiDrawableProvider? = null
  private var categories: Array<EmojiCategory>? = null
  private var emojiPattern: Regex? = null
  internal var emojiRepetitivePattern: Regex? = null
  private var emojiReplacer: EmojiReplacer? = null

  @JvmStatic fun replaceWithImages(context: Context?, text: Spannable?, emojiSize: Float) {
    verifyInstalled()
    emojiReplacer!!.replaceWithImages(context!!, text!!, emojiSize, DEFAULT_EMOJI_REPLACER)
  }

  internal fun categories(): Array<EmojiCategory> {
    verifyInstalled()
    return categories!!
  }

  internal fun emojiDrawableProvider(): EmojiDrawableProvider {
    verifyInstalled()
    return emojiDrawableProvider!!
  }

  internal fun findAllEmojis(text: CharSequence?): List<EmojiRange> {
    verifyInstalled()

    if (text != null && text.isNotEmpty()) {
      return emojiPattern?.findAll(text)
        ?.mapNotNull {
          val emoji = findEmoji(it.value)

          if (emoji != null) {
            EmojiRange(emoji, IntRange(it.range.first, it.range.last + 1))
          } else {
            null
          }
        }
        .orEmpty()
        .toList()
    }

    return emptyList()
  }

  internal fun findEmoji(candidate: CharSequence): Emoji? {
    verifyInstalled()

    // We need to call toString on the candidate, since the emojiMap may not find the requested entry otherwise, because the type is different.
    return emojiMap[candidate.toString()]
  }

  internal fun verifyInstalled() {
    checkNotNull(categories) {
      "Please install an EmojiProvider through the EmojiManager.install() method first."
    }
  }

  private val DEFAULT_EMOJI_REPLACER: EmojiReplacer = EmojiReplacer { context, text, emojiSize, _ ->
    val existingSpans = text.getSpans(0, text.length, EmojiSpan::class.java)
    val existingSpanPositions: MutableList<Int> = ArrayList(existingSpans.size)
    val size = existingSpans.size
    for (i in 0 until size) {
      existingSpanPositions.add(text.getSpanStart(existingSpans[i]))
    }
    val findAllEmojis = findAllEmojis(text)
    for (i in findAllEmojis.indices) {
      val (emoji, range) = findAllEmojis[i]
      if (!existingSpanPositions.contains(range.first)) {
        text.setSpan(
          EmojiSpan(context, emoji, emojiSize),
          range.first, range.last, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
      }
    }
  }

  /**
   * Installs the given EmojiProvider.
   *
   * NOTE: That only one can be present at any time.
   *
   * [provider] the provider that should be installed.
   */
  @JvmStatic fun install(provider: EmojiProvider) {
    synchronized(EmojiManager::class.java) {
      require(provider is EmojiDrawableProvider) { "Your provider needs to implement EmojiDrawableProvider" }
      categories = provider.categories
      @Suppress("UNCHECKED_CAST")
      this.emojiDrawableProvider = provider
      emojiMap.clear()
      emojiReplacer = if (provider is EmojiReplacer) provider else DEFAULT_EMOJI_REPLACER
      val unicodesForPattern: MutableList<String> = ArrayList(GUESSED_UNICODE_AMOUNT)
      val categoriesSize = provider.categories.size

      for (i in 0 until categoriesSize) {
        val emojis = provider.categories[i].emojis
        val emojisSize = emojis.size
        for (j in 0 until emojisSize) {
          val emoji = emojis[j]
          val unicode = emoji.unicode
          val variants = emoji.variants
          emojiMap[unicode] = emoji
          unicodesForPattern.add(unicode)
          for (k in variants.indices) {
            val variant = variants[k]
            val variantUnicode = variant.unicode
            emojiMap[variantUnicode] = variant
            unicodesForPattern.add(variantUnicode)
          }
        }
      }
      require(unicodesForPattern.isNotEmpty()) { "Your EmojiProvider must at least have one category with at least one emoji." }

      // We need to sort the unicodes by length so the longest one gets matched first.
      unicodesForPattern.sortWith { first, second -> second.length.compareTo(first.length) }
      val patternBuilder = StringBuilder(GUESSED_TOTAL_PATTERN_LENGTH)
      val unicodesForPatternSize = unicodesForPattern.size
      for (i in 0 until unicodesForPatternSize) {
        patternBuilder.append(Regex.escape(unicodesForPattern[i])).append('|')
      }
      val regex = patternBuilder.deleteCharAt(patternBuilder.length - 1).toString()
      emojiPattern = Regex(regex, RegexOption.IGNORE_CASE)
      emojiRepetitivePattern = Regex("($regex)+", RegexOption.IGNORE_CASE)
    }
  }

  /**
   * Destroys the EmojiManager. This means that all internal data structures are released as well as
   * all data associated with installed [Emoji]s. For the existing [EmojiProvider]s this
   * means the memory-heavy emoji sheet.
   *
   * @see .destroy
   */
  @JvmStatic fun destroy() {
    synchronized(EmojiManager::class.java) {
      release()
      emojiMap.clear()
      categories = null
      emojiPattern = null
      emojiRepetitivePattern = null
      emojiReplacer = null
    }
  }

  /**
   * Releases all data associated with installed [Emoji]s. For the existing [EmojiProvider]s this
   * means the memory-heavy emoji sheet.
   *
   * In contrast to [destroy], this does **not** destroy the internal
   * data structures and thus, you do not need to [install] again before using the EmojiManager.
   *
   * @see .destroy
   */
  @JvmStatic fun release() {
    synchronized(EmojiManager::class.java) {
      (emojiDrawableProvider as EmojiDrawableProvider).release()
    }
  }
}
