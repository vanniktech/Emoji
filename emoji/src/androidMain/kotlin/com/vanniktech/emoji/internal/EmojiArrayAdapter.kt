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

package com.vanniktech.emoji.internal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.vanniktech.emoji.Emoji
import com.vanniktech.emoji.EmojiTheming
import com.vanniktech.emoji.R
import com.vanniktech.emoji.listeners.OnEmojiClickListener
import com.vanniktech.emoji.variant.VariantEmoji

internal class EmojiArrayAdapter(
  context: Context,
  emojis: Collection<Emoji>,
  private val variantEmoji: VariantEmoji?,
  private val listener: OnEmojiClickListener?,
  private val longListener: OnEmojiLongClickListener?,
  private val theming: EmojiTheming,
) : ArrayAdapter<Emoji>(context, 0, emojis.filterNot { it.isDuplicate }) {
  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    var image = convertView as? EmojiImageView
    val context = context
    if (image == null) {
      image = LayoutInflater.from(context).inflate(R.layout.emoji_adapter_item_emoji, parent, false) as EmojiImageView
      image.clickListener = listener
      image.longClickListener = longListener
    }
    val emoji = getItem(position)!!
    val variantToUse = variantEmoji?.getVariant(emoji) ?: emoji
    image.contentDescription = emoji.unicode
    image.setEmoji(theming, variantToUse, variantEmoji)
    return image
  }

  fun updateEmojis(emojis: Collection<Emoji>) {
    clear()
    addAll(emojis)
    notifyDataSetChanged()
  }
}
