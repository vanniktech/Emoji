package com.vanniktech.emoji.sample;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.EmojiUniversal;
import com.vanniktech.emoji.googlecompat.GoogleCompatEmojiProvider;
import com.vanniktech.emoji.google.GoogleEmojiProvider;
import com.vanniktech.emoji.ios.IosEmojiProvider;
import com.vanniktech.emoji.one.EmojiOneProvider;
import com.vanniktech.emoji.twitter.TwitterEmojiProvider;

// We don't care about duplicated code in the sample.
@SuppressWarnings("CPD-START")
public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";

    ChatAdapter chatAdapter;

    EmojiEditText editText;
    ViewGroup rootView;
    ImageView emojiButton;
    EmojiCompat emojiCompat;
    EmojiUniversal emojiUniversal;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        chatAdapter = new ChatAdapter();

        editText = findViewById(R.id.main_activity_chat_bottom_message_edittext);
        rootView = findViewById(R.id.main_activity_root_view);
        emojiButton = findViewById(R.id.main_activity_emoji);
        emojiUniversal = findViewById(R.id.emojiView);
        final ImageView sendButton = findViewById(R.id.main_activity_send);

        emojiButton.setColorFilter(ContextCompat.getColor(this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);
        sendButton.setColorFilter(ContextCompat.getColor(this, R.color.emoji_icons), PorterDuff.Mode.SRC_IN);

        emojiButton.setOnClickListener(ignore -> emojiUniversal.toggle());
        sendButton.setOnClickListener(ignore -> {
            final String text = editText.getText().toString().trim();

            if (text.length() > 0) {
                chatAdapter.add(text);

                editText.setText("");
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.main_activity_recycler_view);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        emojiUniversal.init(editText, this);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_dialog:
                MainDialog.show(this);
                return true;
            case R.id.variantIos:
                EmojiManager.destroy();
                EmojiManager.install(new IosEmojiProvider());
                recreate();
                return true;
            case R.id.variantGoogle:
                EmojiManager.destroy();
                EmojiManager.install(new GoogleEmojiProvider());
                recreate();
                return true;
            case R.id.variantTwitter:
                EmojiManager.destroy();
                EmojiManager.install(new TwitterEmojiProvider());
                recreate();
                return true;
            case R.id.variantGoogleCompat:
                if (emojiCompat == null) {
                    final EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
                    config.setReplaceAll(true);
                    emojiCompat = EmojiCompat.init(config);
                }
                EmojiManager.destroy();
                EmojiManager.install(new GoogleCompatEmojiProvider(emojiCompat));
                recreate();
                return true;
            case R.id.variantEmojiOne:
                EmojiManager.destroy();
                EmojiManager.install(new EmojiOneProvider());
                recreate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
