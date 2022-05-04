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
package com.vanniktech.emoji.sample

import android.app.Dialog
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.vanniktech.emoji.EmojiPopup
import com.vanniktech.emoji.emoji.Emoji
import com.vanniktech.emoji.material.MaterialEmojiLayoutFactory
import timber.log.Timber

// We don't care about duplicated code in the sample.
class MainDialog : DialogFragment() {
  override fun onCreate(savedInstanceState: Bundle?) {
    layoutInflater.factory2 = MaterialEmojiLayoutFactory(null)
    super.onCreate(savedInstanceState)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return AlertDialog.Builder(requireContext())
      .setView(buildView())
      .create()
  }

  private fun buildView(): View? {
    val context = requireContext()
    val result = View.inflate(context, R.layout.dialog_main, null)
    val editText = result.findViewById<EditText>(R.id.main_dialog_chat_bottom_message_edittext)
    val rootView = result.findViewById<View>(R.id.main_dialog_root_view)
    val emojiButton = result.findViewById<ImageButton>(R.id.main_dialog_emoji)
    val sendButton = result.findViewById<ImageView>(R.id.main_dialog_send)

    val emojiPopup = EmojiPopup(
      rootView = rootView,
      editText = editText,
      onEmojiBackspaceClickListener = { Timber.d(TAG, "Clicked on Backspace") },
      onEmojiClickListener = { emoji: Emoji -> Timber.d(TAG, "Clicked on Emoji " + emoji.unicode) },
      onEmojiPopupShownListener = { emojiButton.setImageResource(R.drawable.ic_keyboard) },
      onSoftKeyboardOpenListener = { px -> Timber.d(TAG, "Opened soft keyboard with height $px") },
      onEmojiPopupDismissListener = { emojiButton.setImageResource(R.drawable.emoji_ios_category_smileysandpeople) },
      onSoftKeyboardCloseListener = { Timber.d(TAG, "Closed soft keyboard") },
      keyboardAnimationStyle = R.style.emoji_fade_animation_style,
    )

    emojiButton.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
    sendButton.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
    val chatAdapter = ChatAdapter()
    emojiButton.setOnClickListener { emojiPopup.toggle() }
    sendButton.setOnClickListener {
      val text = editText.text.toString().trim { it <= ' ' }
      if (text.isNotEmpty()) {
        chatAdapter.add(text)
        editText.setText("")
      }
    }
    val recyclerView: RecyclerView = result.findViewById(R.id.main_dialog_recycler_view)
    recyclerView.adapter = chatAdapter
    return rootView
  }

  internal companion object {
    const val TAG = "MainDialog"

    fun show(activity: AppCompatActivity) {
      MainDialog().show(activity.supportFragmentManager, TAG)
    }
  }
}
