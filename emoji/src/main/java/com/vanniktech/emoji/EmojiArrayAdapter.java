package com.vanniktech.emoji;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.vanniktech.emoji.Utils.checkNotNull;

final class EmojiArrayAdapter extends ArrayAdapter<Emoji> {
  @Nullable private final OnEmojiClickedListener listener;
  @Nullable private final OnEmojiLongClickedListener longListener;

  EmojiArrayAdapter(@NonNull final Context context, @NonNull final Emoji[] emojis,
      @Nullable final OnEmojiClickedListener listener, @Nullable final OnEmojiLongClickedListener longListener) {
    super(context, 0, new ArrayList<>(Arrays.asList(emojis)));

    this.listener = listener;
    this.longListener = longListener;
  }

  @NonNull @Override public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
    EmojiImageView image = (EmojiImageView) convertView;

    final Context context = getContext();

    if (image == null) {
      image = (EmojiImageView) LayoutInflater.from(context).inflate(R.layout.emoji_item, parent, false);
    }

    final Emoji emoji = checkNotNull(getItem(position), "emoji == null");
    final Emoji recentEmoji = RecentEmojiVariantManager.getInstance().getMostRecentVariant(emoji, context);
    image.setEmoji(recentEmoji, listener, longListener);

    return image;
  }

  void updateEmojis(final Collection<Emoji> emojis) {
    clear();
    addAll(emojis);
    notifyDataSetChanged();
  }
}
