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

package com.vanniktech.emoji.sample;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.material.MaterialEmojiLayoutFactory;

// We don't care about duplicated code in the sample.
public class MainDialog extends DialogFragment {
  static final String FRAGMENT_MANAGER_TAG = "dialog_main";
  static final String TAG = "MainDialog";

  ChatAdapter chatAdapter;
  EmojiPopup emojiPopup;

  EmojiEditText editText;
  ViewGroup rootView;
  ImageView emojiButton;

  public static void show(@NonNull final AppCompatActivity activity) {
    new MainDialog().show(activity.getSupportFragmentManager(), FRAGMENT_MANAGER_TAG);
  }

  @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
    getLayoutInflater().setFactory2(new MaterialEmojiLayoutFactory(null));
    super.onCreate(savedInstanceState);

    chatAdapter = new ChatAdapter();
  }

  @Override @NonNull public Dialog onCreateDialog(final Bundle savedInstanceState) {
    return new AlertDialog.Builder(getContext())
            .setView(buildView())
            .create();
  }

  private View buildView() {
    final View result = View.inflate(getContext(), R.layout.dialog_main, null);

    editText = result.findViewById(R.id.main_dialog_chat_bottom_message_edittext);
    rootView = result.findViewById(R.id.main_dialog_root_view);
    emojiButton = result.findViewById(R.id.main_dialog_emoji);
    final ImageView sendButton = result.findViewById(R.id.main_dialog_send);

    emojiButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.emoji_icons), PorterDuff.Mode.SRC_IN);
    sendButton.setColorFilter(ContextCompat.getColor(getContext(), R.color.emoji_icons), PorterDuff.Mode.SRC_IN);

    emojiButton.setOnClickListener(ignore -> emojiPopup.toggle());
    sendButton.setOnClickListener(ignore -> {
      final String text = editText.getText().toString().trim();

      if (text.length() > 0) {
        chatAdapter.add(text);

        editText.setText("");
      }
    });

    final RecyclerView recyclerView = result.findViewById(R.id.main_dialog_recycler_view);
    recyclerView.setAdapter(chatAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

    setUpEmojiPopup();

    return rootView;
  }

  private void setUpEmojiPopup() {
    emojiPopup = EmojiPopup.Builder.fromRootView(rootView)
        .setOnEmojiBackspaceClickListener(ignore -> Log.d(TAG, "Clicked on Backspace"))
        .setOnEmojiClickListener((ignore, ignore2) -> Log.d(TAG, "Clicked on emoji"))
        .setOnEmojiPopupShownListener(() -> emojiButton.setImageResource(R.drawable.ic_keyboard))
        .setOnSoftKeyboardOpenListener(ignore -> Log.d(TAG, "Opened soft keyboard"))
        .setOnEmojiPopupDismissListener(() -> emojiButton.setImageResource(R.drawable.emoji_ios_category_smileysandpeople))
        .setOnSoftKeyboardCloseListener(() -> Log.d(TAG, "Closed soft keyboard"))
        .setKeyboardAnimationStyle(R.style.emoji_fade_animation_style)
        .setPageTransformer(new PageTransformer())
        .build(editText);
  }
}
