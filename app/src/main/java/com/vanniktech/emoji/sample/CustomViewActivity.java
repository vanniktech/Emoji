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

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.SingleEmojiTrait;

public class CustomViewActivity extends AppCompatActivity {
  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final CustomView customView = new CustomView(this, null);
    setContentView(customView);
    customView.setUpEmojiPopup();
  }

  static class CustomView extends LinearLayout {
    final Button emojiButton;
    final EmojiEditText editText;
    EmojiPopup emojiPopup;

    CustomView(final Context context, @Nullable final AttributeSet attrs) {
      super(context, attrs);
      View.inflate(context, R.layout.view_custom, this);

      emojiButton = findViewById(R.id.customViewButton);
      editText = findViewById(R.id.customViewEditText);
      SingleEmojiTrait.install(editText);
    }

    void setUpEmojiPopup() {
      emojiPopup = EmojiPopup.Builder.fromRootView(this)
          .setKeyboardAnimationStyle(R.style.emoji_fade_animation_style)
          .setPageTransformer(new PageTransformer())
          .build(editText);

      emojiButton.setOnClickListener(ignore -> emojiPopup.toggle());
    }
  }
}
