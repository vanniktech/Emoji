package com.vanniktech.emoji;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiClickListener;
import com.vanniktech.emoji.listeners.OnEmojiLongClickListener;

import java.util.Collection;

import static com.vanniktech.emoji.Utils.checkNotNull;
import static com.vanniktech.emoji.Utils.asListWithoutDuplicates;

final class EmojiArrayAdapter extends ArrayAdapter<Emoji> {
  @Nullable private final VariantEmoji variantManager;

  @Nullable private final OnEmojiClickListener listener;
  @Nullable private final OnEmojiLongClickListener longListener;

  EmojiArrayAdapter(@NonNull final Context context, @NonNull final Emoji[] emojis, @Nullable final VariantEmoji variantManager,
                    @Nullable final OnEmojiClickListener listener, @Nullable final OnEmojiLongClickListener longListener) {
    super(context, 0, asListWithoutDuplicates(emojis));

    this.variantManager = variantManager;
    this.listener = listener;
    this.longListener = longListener;
  }

  @Override @NonNull public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
    EmojiImageView image = (EmojiImageView) convertView;

    final Context context = getContext();

    if (image == null) {
      image = (EmojiImageView) LayoutInflater.from(context).inflate(R.layout.emoji_adapter_item, parent, false);

      image.setOnEmojiClickListener(listener);
      image.setOnEmojiLongClickListener(longListener);
    }

    final Emoji emoji = checkNotNull(getItem(position), "emoji == null");
    final Emoji variantToUse = variantManager == null ? emoji : variantManager.getVariant(emoji);
    image.setContentDescription(emoji.getUnicode());
    image.setEmoji(variantToUse);

    return image;
  }

  void updateEmojis(final Collection<Emoji> emojis) {
    clear();
    addAll(emojis);
    notifyDataSetChanged();
  }
}
