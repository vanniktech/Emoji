package com.vanniktech.emoji.traits

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.vanniktech.emoji.EmojiPopup
import com.vanniktech.emoji.EmojiSearchDelegate
import com.vanniktech.emoji.EmojiSearchPopup
import com.vanniktech.emoji.NoSearchEmoji
import com.vanniktech.emoji.emoji.Emoji

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
                emojiPopup.searchEmoji.search(query),
                object : EmojiSearchDelegate {
                  override fun onEmojiClicked(emoji: Emoji) {
                    editText.text.replace(lastColon, s.length, emoji.unicode, 0, emoji.unicode.length)
                    editText.text.append(" ")
                  }
                }
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
