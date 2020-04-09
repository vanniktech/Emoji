package com.vanniktech.emoji.sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;

public class CustomViewActivity extends AppCompatActivity {
  EmojiPopup emojiPopup;
  ViewGroup rootView;
  Button emojiButton;
  EmojiEditText editText;

  @Override @SuppressLint("SetTextI18n") protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    CustomView customView = new CustomView((Activity)this, null);
    setContentView(customView);
    rootView = customView;
    emojiButton = findViewById(R.id.customViewButton);
    editText = findViewById(R.id.customViewEditText);
    setUpEmojiPopup();
  }

  private void setUpEmojiPopup() {
    emojiPopup = EmojiPopup.Builder.fromRootView(rootView)
            .setKeyboardAnimationStyle(R.style.emoji_fade_animation_style)
            .setPageTransformer(new PageTransformer())
            .build(editText);

    emojiButton.setOnClickListener(ignore -> emojiPopup.toggle());
  }

  static class CustomView extends LinearLayout {
    CustomView(final Context context, @Nullable final AttributeSet attrs) {
      super(context, attrs);
      View.inflate(context, R.layout.view_custom, this);
    }
  }
}
