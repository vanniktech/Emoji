package com.vanniktech.emoji.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;

public class CustomViewActivity extends AppCompatActivity {
  @Override @SuppressLint("SetTextI18n") protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(new CustomView(this, null));
  }

  static class CustomView extends LinearLayout {
    CustomView(final Context context, @Nullable final AttributeSet attrs) {
      super(context, attrs);
      View.inflate(context, R.layout.view_custom, this);
    }

    @Override @SuppressLint("WrongViewCast") protected void onAttachedToWindow() {
      super.onAttachedToWindow();

      final View button = findViewById(R.id.customViewButton);
      final EmojiEditText editText = findViewById(R.id.customViewEditText);

      final EmojiPopup emojiPopup = EmojiPopup.Builder.fromRootView((View) getParent())
          .build(editText);

      button.setOnClickListener(ignore -> emojiPopup.toggle());
    }
  }
}
