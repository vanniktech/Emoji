package com.vanniktech.emoji.listeners;

import androidx.annotation.NonNull;
import com.vanniktech.emoji.EmojiImageView;
import com.vanniktech.emoji.emoji.Emoji;

public interface OnEmojiLongClickListener {
  void onEmojiLongClick(@NonNull EmojiImageView view, @NonNull Emoji emoji);
}
