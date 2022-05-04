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

package com.vanniktech.emoji.sample

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.vanniktech.emoji.emoji.Emoji
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener
import com.vanniktech.emoji.listeners.OnEmojiClickListener
import com.vanniktech.emoji.sample.databinding.ActivityEmojisBinding

class EmojisActivity : AppCompatActivity(), OnEmojiClickListener, OnEmojiBackspaceClickListener {
  private lateinit var binding: ActivityEmojisBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityEmojisBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.toolbar)

    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

    binding.emojiView.setUp(
      rootView = binding.root,
      onEmojiClickListener = this,
      onEmojiBackspaceClickListener = this,
      editText = null,
    )
  }

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    android.R.id.home -> {
      finish()
      true
    }
    else -> super.onOptionsItemSelected(item)
  }

  override fun onDestroy() {
    super.onDestroy()
    binding.emojiView.tearDown()
  }

  override fun onEmojiBackspaceClick() {
    binding.selectedEmoji.text = ""
  }

  override fun onEmojiClick(emoji: Emoji) {
    binding.selectedEmoji.text = emoji.unicode
  }
}
