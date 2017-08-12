package com.vanniktech.emoji.sample;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiImageView;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardOpenListener;

@SuppressWarnings("CPD-START") // We don't care about duplicate code in the sample.
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
    super.onCreate(savedInstanceState);

    chatAdapter = new ChatAdapter();
  }

  @Override public void onStop() {
    if (emojiPopup != null) {
      emojiPopup.dismiss();
    }

    super.onStop();
  }

  @NonNull @Override
  public Dialog onCreateDialog(final Bundle savedInstanceState) {
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

    emojiButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {
        emojiPopup.toggle();
      }
    });
    sendButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {
        final String text = editText.getText().toString().trim();

        if (text.length() > 0) {
          chatAdapter.add(text);

          editText.setText("");
        }
      }
    });

    final RecyclerView recyclerView = result.findViewById(R.id.main_dialog_recycler_view);
    recyclerView.setAdapter(chatAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    setUpEmojiPopup();

    return rootView;
  }

  private void setUpEmojiPopup() {
    emojiPopup = EmojiPopup.Builder.fromRootView(rootView)
            .setOnEmojiBackspaceClickListener(new OnEmojiBackspaceClickListener() {
              @Override public void onEmojiBackspaceClick(final View v) {
                Log.d(TAG, "Clicked on Backspace");
              }
            })
            .setOnEmojiClickListener(new OnEmojiClickListener() {
              @Override public void onEmojiClick(@NonNull final EmojiImageView imageView, @NonNull final Emoji emoji) {
                Log.d(TAG, "Clicked on emoji");
              }
            })
            .setOnEmojiPopupShownListener(new OnEmojiPopupShownListener() {
              @Override public void onEmojiPopupShown() {
                emojiButton.setImageResource(R.drawable.ic_keyboard);
              }
            })
            .setOnSoftKeyboardOpenListener(new OnSoftKeyboardOpenListener() {
              @Override public void onKeyboardOpen(@Px final int keyBoardHeight) {
                Log.d(TAG, "Opened soft keyboard");
              }
            })
            .setOnEmojiPopupDismissListener(new OnEmojiPopupDismissListener() {
              @Override public void onEmojiPopupDismiss() {
                emojiButton.setImageResource(R.drawable.emoji_ios_category_people);
              }
            })
            .setOnSoftKeyboardCloseListener(new OnSoftKeyboardCloseListener() {
              @Override public void onKeyboardClose() {
                Log.d(TAG, "Closed soft keyboard");
              }
            })
            .build(editText);
  }
}
