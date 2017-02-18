package com.vanniktech.emoji;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.vanniktech.emoji.Utils.checkNotNull;

final class EmojiArrayAdapter extends ArrayAdapter<Emoji> {
  @Nullable final OnEmojiClickedListener listener;
  @Nullable final OnEmojiLongClickedListener longListener;

  EmojiArrayAdapter(@NonNull final Context context, @NonNull final Emoji[] emojis,
                    @Nullable final OnEmojiClickedListener listener,
                    @Nullable final OnEmojiLongClickedListener longListener,
                    final boolean filterSkinTonedEmojis) {
    super(context, 0, filter(emojis, filterSkinTonedEmojis));

    this.listener = listener;
    this.longListener = longListener;
  }

  private static List<Emoji> filter(final Emoji[] emojis, final boolean filterSkinTonedEmojis) { // NOPMD
    final List<Emoji> result = new ArrayList<>(emojis.length);

    for (final Emoji emoji : emojis) {
      if (!(filterSkinTonedEmojis && emoji.isSkinToned())) {
        result.add(emoji);
      }
    }

    return result;
  }

  @NonNull @Override
  public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
    ImageView image = (ImageView) convertView;

    if (image == null) {
      image = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.emoji_item, parent, false);
    }

    final Emoji emoji = checkNotNull(getItem(position), "emoji == null");

    image.setImageDrawable(null);
    image.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {
        if (listener != null) {
          listener.onEmojiClicked(getItem(position));
        }
      }
    });

    final EmojiManager emojiManager = EmojiManager.getInstance();

    if (!emojiManager.findSkinTonedEmojis(emoji.isSkinToned() ? emojiManager.findNonSkinTonedEmoji(emoji) : emoji).isEmpty()) {
      image.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(final View v) {
            if (longListener != null) {
              longListener.onEmojiLongClicked(v, emoji);

              return true;
            }

            return false;
          }
      });
    } else {
      image.setOnLongClickListener(null);
    }

    ImageDownloaderTask task = (ImageDownloaderTask) image.getTag();

    if (task != null) {
      task.cancel(true);
    }

    task = new ImageDownloaderTask(image);
    image.setTag(task);
    task.execute(emoji.getResource());

    return image;
  }

  void updateEmojis(final Collection<Emoji> emojis) {
    clear();
    addAll(emojis);
    notifyDataSetChanged();
  }
}
