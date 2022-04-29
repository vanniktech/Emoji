package com.vanniktech.emoji.traits

import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vanniktech.emoji.EmojiPopup

/**
 * Disables the keyboard so that letters, digits etc can't be input.
 * Instead only the EmojiPopup will be displayed.
 *
 * Note: This is using a OnFocusChangeListener. Don't overwrite it.
 */
class DisableKeyboardInputTrait(
  private val emojiPopup: EmojiPopup,
) : EmojiTraitable {
  override fun install(editText: EditText): EmojiTrait {
    val forceEmojisOnlyFocusChangeListener = ForceEmojisOnlyFocusChangeListener(
      delegate = editText.onFocusChangeListener,
      emojiPopup = emojiPopup,
    )
    editText.onFocusChangeListener = forceEmojisOnlyFocusChangeListener

    val isKeyboardOpen = ViewCompat.getRootWindowInsets(editText.rootView)?.isVisible(WindowInsetsCompat.Type.ime()) == true

    if (editText.hasFocus() || isKeyboardOpen) {
      forceEmojisOnlyFocusChangeListener.focus()
    }

    return object : EmojiTrait {
      override fun uninstall() {
        editText.onFocusChangeListener = forceEmojisOnlyFocusChangeListener.delegate
      }
    }
  }

  internal class ForceEmojisOnlyFocusChangeListener(
    internal val delegate: OnFocusChangeListener?,
    private val emojiPopup: EmojiPopup,
  ) : OnFocusChangeListener {
    override fun onFocusChange(view: View, hasFocus: Boolean) {
      if (hasFocus) {
        emojiPopup.start()
        emojiPopup.show()
      } else {
        emojiPopup.dismiss()
      }

      delegate?.onFocusChange(view, hasFocus)
    }

    internal fun focus() {
      if (!emojiPopup.isShowing) {
        emojiPopup.start()
        emojiPopup.show()
      }
    }
  }
}
