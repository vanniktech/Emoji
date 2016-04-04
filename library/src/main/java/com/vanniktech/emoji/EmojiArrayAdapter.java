package com.vanniktech.emoji;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;

final class EmojiArrayAdapter extends ArrayAdapter<Emoji> {
    /** we need this because Arrays.asList does not support {@link Collection#clear()} */
    @SuppressWarnings("PMD.UseVarargs")
    private static List<Emoji> toList(final Emoji[] data) {
        final List<Emoji> list = new ArrayList<>(data.length);
        Collections.addAll(list, data);
        return list;
    }

    @Nullable private OnEmojiClickedListener onEmojiClickedListener;

    @SuppressWarnings("PMD.UseVarargs")
    EmojiArrayAdapter(final Context context, final Emoji[] data) {
        super(context, R.layout.emoji_text_view, toList(data));
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.emoji_text_view, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.icon = (TextView) view.findViewById(R.id.emoji_icon);
            view.setTag(holder);
        }

        final Emoji emoji = getItem(position);
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.emoji = emoji;
        holder.icon.setText(emoji.getEmoji());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onEmojiClickedListener != null) {
                    final ViewHolder tag = (ViewHolder) v.getTag();
                    onEmojiClickedListener.onEmojiClicked(tag.emoji);
                }
            }
        });

        return view;
    }

    public void updateEmojis(final Collection<Emoji> emojis) {
        clear();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            addAll(emojis);
        } else {
            for (Emoji e : emojis.toArray(new Emoji[emojis.size()])) {
                add(e);
            }
        }

        notifyDataSetChanged();
    }

    public void setOnEmojiClickedListener(@Nullable final OnEmojiClickedListener onEmojiClickedListener) {
        this.onEmojiClickedListener = onEmojiClickedListener;
    }

    static class ViewHolder {
        Emoji emoji;
        TextView icon;
    }
}
