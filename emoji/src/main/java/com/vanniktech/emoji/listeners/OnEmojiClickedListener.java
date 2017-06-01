package com.vanniktech.emoji.listeners;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.Emoji;

public interface OnEmojiClickedListener {
  void onEmojiClicked(@NonNull Emoji emoji);
}
