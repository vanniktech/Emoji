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
 *
 */
package com.vanniktech.emoji

import android.content.Context
import android.text.Spannable
import com.vanniktech.emoji.emoji.Emoji
import com.vanniktech.emoji.emoji.EmojiCategory
import java.lang.StringBuilder
import java.util.ArrayList
import java.util.Collections
import java.util.LinkedHashMap
import java.util.regex.Pattern

private const val GUESSED_UNICODE_AMOUNT = 3000
private const val GUESSED_TOTAL_PATTERN_LENGTH = GUESSED_UNICODE_AMOUNT * 4

/**
 * EmojiManager where an EmojiProvider can be installed for further usage.
 */
object EmojiManager {
  private val emojiMap: MutableMap<String, Emoji> = LinkedHashMap(GUESSED_UNICODE_AMOUNT)
  private var categories: Array<EmojiCategory>? = null
  private var emojiPattern: Pattern? = null
  internal var emojiRepetitivePattern: Pattern? = null
  private var emojiReplacer: EmojiReplacer? = null
  fun replaceWithImages(context: Context?, text: Spannable?, emojiSize: Float) {
    verifyInstalled()
    emojiReplacer!!.replaceWithImages(context!!, text!!, emojiSize, DEFAULT_EMOJI_REPLACER)
  }

  internal fun categories(): Array<EmojiCategory> {
    verifyInstalled()
    return categories!!
  }

  internal fun findAllEmojis(text: CharSequence?): List<EmojiRange> {
    verifyInstalled()
    val result: MutableList<EmojiRange> = ArrayList()
    if (text != null && text.isNotEmpty()) {
      val matcher = emojiPattern!!.matcher(text)
      while (matcher.find()) {
        val found = findEmoji(text.subSequence(matcher.start(), matcher.end()))
        if (found != null) {
          result.add(EmojiRange(matcher.start(), matcher.end(), found))
        }
      }
    }
    return result
  }

  internal fun findEmoji(candidate: CharSequence): Emoji? {
    verifyInstalled()

    // We need to call toString on the candidate, since the emojiMap may not find the requested entry otherwise, because the type is different.
    return emojiMap[candidate.toString()]
  }

  fun verifyInstalled() {
    checkNotNull(categories) {
      "Please install an EmojiProvider through the EmojiManager.install() method first."
    }
  }

  private val STRING_LENGTH_COMPARATOR = java.util.Comparator<String> { first, second ->
    val firstLength = first.length
    val secondLength = second.length
    secondLength.compareTo(firstLength)
  }

  private val DEFAULT_EMOJI_REPLACER: EmojiReplacer = object : EmojiReplacer {
    override fun replaceWithImages(context: Context, text: Spannable, emojiSize: Float, fallback: EmojiReplacer) {
      val existingSpans = text.getSpans(0, text.length, EmojiSpan::class.java)
      val existingSpanPositions: MutableList<Int> = ArrayList(existingSpans.size)
      val size = existingSpans.size
      for (i in 0 until size) {
        existingSpanPositions.add(text.getSpanStart(existingSpans[i]))
      }
      val findAllEmojis = findAllEmojis(text)
      for (i in findAllEmojis.indices) {
        val (start, end, emoji) = findAllEmojis[i]
        if (!existingSpanPositions.contains(start)) {
          text.setSpan(
            EmojiSpan(context, emoji, emojiSize),
            start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
          )
        }
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
  fun install(provider: EmojiProvider) {
    synchronized(EmojiManager::class.java) {
      categories = provider.categories
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
          val variants: Array<out Emoji> = emoji.variants
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
      Collections.sort(unicodesForPattern, STRING_LENGTH_COMPARATOR)
      val patternBuilder = StringBuilder(GUESSED_TOTAL_PATTERN_LENGTH)
      val unicodesForPatternSize = unicodesForPattern.size
      for (i in 0 until unicodesForPatternSize) {
        patternBuilder.append(Pattern.quote(unicodesForPattern[i])).append('|')
      }
      val regex = patternBuilder.deleteCharAt(patternBuilder.length - 1).toString()
      emojiPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
      emojiRepetitivePattern = Pattern.compile("($regex)+", Pattern.CASE_INSENSITIVE)
    }
  }

  /**
   * Destroys the EmojiManager. This means that all internal data structures are released as well as
   * all data associated with installed [Emoji]s. For the existing [EmojiProvider]s this
   * means the memory-heavy emoji sheet.
   *
   * @see .destroy
   */
  fun destroy() {
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
  fun release() {
    synchronized(EmojiManager::class.java) {
      for (emoji in emojiMap.values) {
        emoji.destroy()
      }
    }
  }
}
