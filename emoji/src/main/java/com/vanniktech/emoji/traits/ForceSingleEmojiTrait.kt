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
package com.vanniktech.emoji.traits

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.vanniktech.emoji.OnlyEmojisInputFilter

/**
 * Forces the [EditText] to only contain one Emoji,
 * while also being able to replace the previous one.
 */
class ForceSingleEmojiTrait : EmojiTraitable {
  override fun install(editText: EditText): EmojiTrait {
    val listener = object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit
      override fun afterTextChanged(s: Editable) = Unit

      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        editText.removeTextChangedListener(this)

        val emoji = s.subSequence(start, start + count)
        editText.text = null
        editText.append(emoji)
        editText.addTextChangedListener(this)
      }
    }

    editText.filters = editText.filters
      .plus(OnlyEmojisInputFilter())
    editText.addTextChangedListener(listener)

    return object : EmojiTrait {
      override fun uninstall() {
        editText.filters = editText.filters
          .filterNot { it is OnlyEmojisInputFilter }
          .toList()
          .toTypedArray()
        editText.removeTextChangedListener(listener)
      }
    }
  }
}
