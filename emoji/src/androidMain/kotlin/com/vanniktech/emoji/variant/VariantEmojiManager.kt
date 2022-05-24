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

package com.vanniktech.emoji.variant

import android.content.Context
import com.vanniktech.emoji.Emoji
import com.vanniktech.emoji.EmojiManager

class VariantEmojiManager(
  context: Context,
) : VariantEmoji {
  private val preferences = context.applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
  private var variantsList = mutableListOf<Emoji>()

  override fun getVariant(desiredEmoji: Emoji): Emoji {
    if (variantsList.isEmpty()) {
      initFromSharedPreferences()
    }

    val baseEmoji = desiredEmoji.base
    return variantsList.firstOrNull { it.base == baseEmoji } ?: desiredEmoji
  }

  override fun addVariant(newVariant: Emoji) {
    val newVariantBase = newVariant.base

    for (i in variantsList.indices) {
      val variant = variantsList[i]
      if (variant.base == newVariantBase) {
        if (variant == newVariant) {
          return // Same skin-tone was used.
        } else {
          variantsList.removeAt(i)
          variantsList.add(newVariant)
          return
        }
      }
    }

    variantsList.add(newVariant)
  }

  override fun persist() {
    if (variantsList.size > 0) {
      val stringBuilder = StringBuilder(variantsList.size * EMOJI_GUESS_SIZE)
      for (i in variantsList.indices) {
        stringBuilder.append(variantsList[i].unicode).append(EMOJI_DELIMITER)
      }
      stringBuilder.setLength(stringBuilder.length - EMOJI_DELIMITER.length)
      preferences.edit().putString(VARIANT_EMOJIS, stringBuilder.toString()).apply()
    } else {
      preferences.edit().remove(VARIANT_EMOJIS).apply()
    }
  }

  private fun initFromSharedPreferences() {
    val savedRecentVariants = preferences.getString(VARIANT_EMOJIS, "").orEmpty()
    if (savedRecentVariants.isNotEmpty()) {
      variantsList = savedRecentVariants.split(EMOJI_DELIMITER)
        .mapNotNull { EmojiManager.findEmoji(it) }
        .toMutableList()
    }
  }

  internal companion object {
    private const val PREFERENCE_NAME = "variant-emoji-manager"
    private const val EMOJI_DELIMITER = "~"
    private const val VARIANT_EMOJIS = "variant-emojis"
    private const val EMOJI_GUESS_SIZE = 5
  }
}
