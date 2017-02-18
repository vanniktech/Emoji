package com.vanniktech.emoji;

import android.view.View;
import com.vanniktech.emoji.emoji.Emoji;

interface OnEmojiLongClickedListener {
  void onEmojiLongClicked(final View view, final Emoji emoji);
}
