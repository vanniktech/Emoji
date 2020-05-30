package com.vanniktech.emoji;

import com.vanniktech.emoji.emoji.Emoji;

public interface EmojiEditable extends EmojiDisplayable {
  /** Issues a backspace. */
  void backspace();

  /** Adds the emoji. */
  void input(Emoji emoji);
}
