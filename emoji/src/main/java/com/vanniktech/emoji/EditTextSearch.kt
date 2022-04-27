package com.vanniktech.emoji

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.vanniktech.emoji.emoji.Emoji
import com.vanniktech.emoji.listeners.OnEmojiClickListener

internal fun EditText.include(
  rootView: View,
  searchEmoji: SearchEmoji,
  theming: EmojiTheming,
  onEmojiClickListener: OnEmojiClickListener?,
) {
  if (searchEmoji is NoSearchEmoji) {
    return
  }

  val popup = EmojiSearchPopup(rootView, this, theming)
  val handler = Handler(Looper.getMainLooper())

  addTextChangedListener(object : TextWatcher {
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
              searchEmoji.search(query),
              object : EmojiSearchDelegate {
                override fun onEmojiClicked(emoji: Emoji) {
                  text.replace(lastColon, s.length, emoji.unicode, 0, emoji.unicode.length)
                  text.append(" ")
                  onEmojiClickListener?.onEmojiClick(emoji)
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
  })
}
