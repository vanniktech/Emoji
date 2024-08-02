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

@file:JvmName("EmojiManagers")

package com.vanniktech.emoji

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import com.vanniktech.emoji.internal.EmojiSpan

fun EmojiManager.replaceWithImages(
  context: Context,
  text: Spannable?,
  emojiSize: Float,
) {
  val emojiReplacer = emojiProvider() as? EmojiReplacer ?: defaultEmojiReplacer
  emojiReplacer.replaceWithImages(
    context = context,
    text = text ?: SpannableStringBuilder(""),
    emojiSize = emojiSize,
    fallback = defaultEmojiReplacer,
  )
}

internal val defaultEmojiReplacer = EmojiReplacer { context, text, emojiSize, _ ->
  val existingSpans = text.getSpans(0, text.length, EmojiSpan::class.java)
  val existingSpanPositions: MutableList<Int> = ArrayList(existingSpans.size)
  val size = existingSpans.size
  for (i in 0 until size) {
    existingSpanPositions.add(text.getSpanStart(existingSpans[i]))
  }
  val findAllEmojis = EmojiManager.findAllEmojis(text)
  for (i in findAllEmojis.indices) {
    val (emoji, range) = findAllEmojis[i]
    if (!existingSpanPositions.contains(range.first)) {
      text.setSpan(
        EmojiSpan(context, emoji, emojiSize),
        range.first,
        range.last + 1,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
      )
    }
  }
}
