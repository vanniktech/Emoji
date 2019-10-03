package com.vanniktech.emoji.sample;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.provider.FontRequest;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.text.FontRequestEmojiCompatConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.EmojiMultiAutoCompleteTextView;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.google.GoogleEmojiProvider;
import com.vanniktech.emoji.googlecompat.GoogleCompatEmojiProvider;
import com.vanniktech.emoji.ios.IosEmojiProvider;
import com.vanniktech.emoji.twitter.TwitterEmojiProvider;

// We don't care about duplicated code in the sample.
@SuppressWarnings("CPD-START") public class MainActivityMultiAutoCompeteTextView extends AppCompatActivity {
  static final String TAG = "MainActivity";

  ChatAdapter chatAdapter;
  EmojiPopup emojiPopup;

  EmojiMultiAutoCompleteTextView editText;
  ViewGroup rootView;
  ImageView emojiButton;
  EmojiCompat emojiCompat;

  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main_multiautocompletetextview);

    chatAdapter = new ChatAdapter();

    editText = findViewById(R.id.main_activity_chat_bottom_message_edittext);
    rootView = findViewById(R.id.main_activity_root_view);
    emojiButton = findViewById(R.id.main_activity_emoji);
    final ImageView sendButton = findViewById(R.id.main_activity_send);

    emojiButton.setColorFilter(ContextCompat.getColor(this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);
    sendButton.setColorFilter(ContextCompat.getColor(this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);

    emojiButton.setOnClickListener(ignore -> {
      emojiPopup.toggle();
    });
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
    switch (item.getItemId()) {
      case R.id.menuMainShowDialog:
        emojiPopup.dismiss();
        MainDialog.show(this);
        return true;
      case R.id.menuMainVariantIos:
        EmojiManager.destroy();
        EmojiManager.install(new IosEmojiProvider());
        recreate();
        return true;
      case R.id.menuMainGoogle:
        EmojiManager.destroy();
        EmojiManager.install(new GoogleEmojiProvider());
        recreate();
        return true;
      case R.id.menuMainTwitter:
        EmojiManager.destroy();
        EmojiManager.install(new TwitterEmojiProvider());
        recreate();
        return true;
      case R.id.menuMainGoogleCompat:
        if (emojiCompat == null) {
          emojiCompat = EmojiCompat.init(new FontRequestEmojiCompatConfig(this,
              new FontRequest("com.google.android.gms.fonts", "com.google.android.gms", "Noto Color Emoji Compat", R.array.com_google_android_gms_fonts_certs)
          ).setReplaceAll(true));
        }
        EmojiManager.destroy();
        EmojiManager.install(new GoogleCompatEmojiProvider(emojiCompat));
        recreate();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void onBackPressed() {
    if (emojiPopup != null && emojiPopup.isShowing()) {
      emojiPopup.dismiss();
    } else {
      super.onBackPressed();
    }
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
