package com.vanniktech.emoji.traits

import android.widget.EditText

interface EmojiTraitable {
  fun install(editText: EditText): EmojiTrait
}
