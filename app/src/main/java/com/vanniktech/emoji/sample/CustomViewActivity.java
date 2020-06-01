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

    final CustomView customView1 = new CustomView(this, null);
    final CustomView customView2 = new CustomView(this, null);
    final LinearLayout linearLayout = new LinearLayout(this);
    linearLayout.setOrientation(LinearLayout.VERTICAL);
    linearLayout.addView(customView1);
    linearLayout.addView(customView2);
    setContentView(linearLayout);
    customView1.setUpEmojiPopup();
    customView2.setUpEmojiPopup();
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
