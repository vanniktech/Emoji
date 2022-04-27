package com.vanniktech.emoji.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vanniktech.emoji.EmojiTheming
import com.vanniktech.emoji.RecentEmojiManager
import com.vanniktech.emoji.SearchEmojiManager
import com.vanniktech.emoji.VariantEmojiManager
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

    binding.emojiView.setUp(
      binding.root,
      EmojiTheming(),
      RecentEmojiManager(this),
      SearchEmojiManager(),
      VariantEmojiManager(this),
      null,
      this,
      this,
      null,
    )
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
