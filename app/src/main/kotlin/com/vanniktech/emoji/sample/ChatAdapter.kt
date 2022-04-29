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
package com.vanniktech.emoji.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vanniktech.emoji.EmojiTextView
import com.vanniktech.emoji.emojiInformation
import com.vanniktech.emoji.sample.ChatAdapter.ChatViewHolder

internal class ChatAdapter : RecyclerView.Adapter<ChatViewHolder>() {
  private val texts = mutableListOf<String>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    return ChatViewHolder(layoutInflater.inflate(R.layout.item_adapter_chat, parent, false))
  }

  override fun onBindViewHolder(chatViewHolder: ChatViewHolder, position: Int) {
    val text = texts[position]
    val (isOnlyEmojis, emojis) = text.emojiInformation()
    val res: Int = when {
      isOnlyEmojis && emojis.size == 1 -> R.dimen.emoji_size_single_emoji
      isOnlyEmojis && emojis.size > 1 -> R.dimen.emoji_size_only_emojis
      else -> R.dimen.emoji_size_default
    }
    chatViewHolder.textView.setEmojiSizeRes(res, false)
    chatViewHolder.textView.text = text
  }

  override fun getItemCount() = texts.size

  fun add(text: String) {
    texts.add(text)
    notifyItemInserted(texts.size - 1)
  }

  internal class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textView: EmojiTextView = view.findViewById(R.id.itemAdapterChatTextView)
  }
}
