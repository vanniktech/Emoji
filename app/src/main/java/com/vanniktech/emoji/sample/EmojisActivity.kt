package com.vanniktech.emoji.sample

import android.os.Bundle
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

    binding.emojiView.setUp(
      rootView = binding.root,
      onEmojiClickListener = this,
      onEmojiBackspaceClickListener = this,
      editText = null,
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
