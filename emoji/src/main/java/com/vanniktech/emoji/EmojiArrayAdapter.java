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
  @Nullable final OnEmojiClickedListener listener;
  @Nullable final OnEmojiLongClickedListener longListener;

  EmojiArrayAdapter(@NonNull final Context context, @NonNull final Emoji[] emojis,
      @Nullable final OnEmojiClickedListener listener, @Nullable final OnEmojiLongClickedListener longListener) {
    super(context, 0, new ArrayList<>(Arrays.asList(emojis)));

    this.listener = listener;
    this.longListener = longListener;
  }

  @NonNull @Override public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
    EmojiImageView image = (EmojiImageView) convertView;

    if (image == null) {
      image = (EmojiImageView) LayoutInflater.from(getContext()).inflate(R.layout.emoji_item, parent, false);
    }

    final Emoji emoji = RecentVariantManager.getInstance().getMostRecentVariant(checkNotNull(getItem(position), "emoji == null"), getContext());


    image.setImageDrawable(null);
    image.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {
        if (listener != null) {
          listener.onEmojiClicked(emoji);
        }
      }
    });

    if (emoji.getBase().hasVariants()) {
      image.setHasVariants(true);
      image.setOnLongClickListener(new View.OnLongClickListener() {
          @Override public boolean onLongClick(final View v) {
            if (longListener != null) {
              longListener.onEmojiLongClicked(v, emoji);

              return true;
            }

            return false;
          }
      });
    } else {
      image.setHasVariants(false);
      image.setOnLongClickListener(null);
    }

    ImageLoadingTask task = (ImageLoadingTask) image.getTag();

    if (task != null) {
      task.cancel(true);
    }

    task = new ImageLoadingTask(image);
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
