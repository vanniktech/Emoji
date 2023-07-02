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

package com.vanniktech.emoji.traits

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.vanniktech.emoji.EmojiPopup
import com.vanniktech.emoji.internal.EmojiSearchPopup
import com.vanniktech.emoji.search.NoSearchEmoji

/**
 * Popup similar to how Telegram and Slack does it to search for an Emoji
 */
class SearchInPlaceTrait(
  private val emojiPopup: EmojiPopup,
) : EmojiTraitable {
  override fun install(editText: EditText): EmojiTrait {
    if (emojiPopup.searchEmoji is NoSearchEmoji) {
      return EmptyEmojiTrait
    }

    val popup = EmojiSearchPopup(emojiPopup.rootView, editText, emojiPopup.theming)
    val handler = Handler(Looper.getMainLooper())

    val watcher = object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

      override fun afterTextChanged(s: Editable) {
        handler.removeCallbacksAndMessages(null)

        // Cheap debounce without RxJava.
        handler.postDelayed({
          val lastColon = s.indexOfLast { it == ':' }

          if (lastColon >= 0) {
            val query = s.drop(lastColon + 1).toString()
            val isProperQuery = query.all { it.isLetterOrDigit() || it == '_' }

            if (isProperQuery) {
              popup.show(
                emojis = emojiPopup.searchEmoji.search(query),
                delegate = {
                  val new = "${it.unicode} "
                  editText.text.replace(lastColon, s.length, new, 0, new.length)
                },
              )
            } else {
              popup.dismiss()
            }
          } else {
            popup.dismiss()
          }
        }, 300L)
      }
    }
    editText.addTextChangedListener(watcher)
    return object : EmojiTrait {
      override fun uninstall() {
        popup.dismiss()
        editText.removeTextChangedListener(watcher)
      }
    }
  }
}
