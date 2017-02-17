package com.vanniktech.emoji.listeners;

import android.support.annotation.RestrictTo;
import android.view.View;

import com.vanniktech.emoji.emoji.Emoji;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface OnEmojiLongClickedListener {
  void onEmojiLongClicked(final View view, final Emoji emoji);
}
