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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.provider.FontRequest;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.text.FontRequestEmojiCompatConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.facebook.FacebookEmojiProvider;
import com.vanniktech.emoji.google.GoogleEmojiProvider;
import com.vanniktech.emoji.googlecompat.GoogleCompatEmojiProvider;
import com.vanniktech.emoji.ios.IosEmojiProvider;
import com.vanniktech.emoji.material.MaterialEmojiLayoutFactory;
import com.vanniktech.emoji.twitter.TwitterEmojiProvider;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

// We don't care about duplicated code in the sample.
public class MainActivity extends AppCompatActivity {
  static final String TAG = "MainActivity";

  ChatAdapter chatAdapter;
  EmojiPopup emojiPopup;

  EmojiEditText editText;
  ViewGroup rootView;
  ImageView emojiButton;
  EmojiCompat emojiCompat;

  @Override @SuppressLint("SetTextI18n") protected void onCreate(final Bundle savedInstanceState) {
    getLayoutInflater().setFactory2(new MaterialEmojiLayoutFactory((LayoutInflater.Factory2) getDelegate()));
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    chatAdapter = new ChatAdapter();

    final Button button = findViewById(R.id.main_activity_material_button);
    button.setText("\uD83D\uDE18\uD83D\uDE02\uD83E\uDD8C");
    editText = findViewById(R.id.main_activity_chat_bottom_message_edittext);
    rootView = findViewById(R.id.main_activity_root_view);
    emojiButton = findViewById(R.id.main_activity_emoji);
    final ImageView sendButton = findViewById(R.id.main_activity_send);

    emojiButton.setColorFilter(ContextCompat.getColor(this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);
    sendButton.setColorFilter(ContextCompat.getColor(this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);

    final CheckBox forceEmojisOnly = findViewById(R.id.main_activity_force_emojis_only);
    forceEmojisOnly.setOnCheckedChangeListener((ignore, isChecked) -> {
      if (isChecked) {
        editText.clearFocus();
        emojiButton.setVisibility(GONE);
        editText.disableKeyboardInput(emojiPopup);
      } else {
        emojiButton.setVisibility(VISIBLE);
        editText.enableKeyboardInput();
      }
    });

    emojiButton.setOnClickListener(ignore -> emojiPopup.toggle());

    sendButton.setOnClickListener(ignore -> {
      final String text = editText.getText().toString().trim();

      if (text.length() > 0) {
        chatAdapter.add(text);

        editText.setText("");
      }
    });

    final RecyclerView recyclerView = findViewById(R.id.main_activity_recycler_view);
    recyclerView.setAdapter(chatAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    setUpEmojiPopup();
  }

  @Override public boolean onCreateOptionsMenu(final Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(final MenuItem item) {
    final int itemId = item.getItemId();
    if (itemId == R.id.menuMainShowDialog) {
      emojiPopup.dismiss();
      MainDialog.show(this);
      return true;
    } else if (itemId == R.id.menuMainCustomView) {
      emojiPopup.dismiss();
      startActivity(new Intent(this, CustomViewActivity.class));
      return true;
    } else if (itemId == R.id.menuMainVariantIos) {
      EmojiManager.destroy();
      EmojiManager.install(new IosEmojiProvider());
      recreate();
      return true;
    } else if (itemId == R.id.menuMainGoogle) {
      EmojiManager.destroy();
      EmojiManager.install(new GoogleEmojiProvider());
      recreate();
      return true;
    } else if (itemId == R.id.menuMainTwitter) {
      EmojiManager.destroy();
      EmojiManager.install(new TwitterEmojiProvider());
      recreate();
      return true;
    } else if (itemId == R.id.menuMainFacebook) {
      EmojiManager.destroy();
      EmojiManager.install(new FacebookEmojiProvider());
      recreate();
      return true;
    } else if (itemId == R.id.menuMainGoogleCompat) {
      if (emojiCompat == null) {
        emojiCompat = EmojiCompat.init(new FontRequestEmojiCompatConfig(this,
            new FontRequest("com.google.android.gms.fonts", "com.google.android.gms",
                "Noto Color Emoji Compat", R.array.com_google_android_gms_fonts_certs)
        ).setReplaceAll(true));
      }
      EmojiManager.destroy();
      EmojiManager.install(new GoogleCompatEmojiProvider(emojiCompat));
      recreate();
      return true;
    }
    return super.onOptionsItemSelected(item);
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
        //.setRecentEmoji(NoRecentEmoji.INSTANCE) // Uncomment this to hide recent emojis.
        .build(editText);
  }
}
