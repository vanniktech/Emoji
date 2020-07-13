package com.vanniktech.emoji.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vanniktech.emoji.EmojiInformation;
import com.vanniktech.emoji.EmojiTextView;
import com.vanniktech.emoji.EmojiUtils;
import com.vanniktech.emoji_stickers.stickers.Sticker;

import java.util.ArrayList;
import java.util.List;

final class ChatAdapterStickers extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<AdapterItem> items = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.item_adapter_emoji) {
            return new EmojiChatHolder(layoutInflater.inflate(viewType, parent, false));
        }
        return new StickerChatHolder(layoutInflater.inflate(viewType, parent, false));
    }


    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getSticker() != null) {
            return R.layout.item_adapter_sticker;
        }
        return R.layout.item_adapter_emoji;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        AdapterItem adapterItem = items.get(position);
        if (adapterItem.getSticker() != null) {
            StickerChatHolder stickerChatHolder = (StickerChatHolder) viewHolder;
            Sticker sticker = adapterItem.getSticker();
            if (sticker.getPath() != null)
                Glide.with(stickerChatHolder.imageView.getContext()).load(sticker.getPath()).into(stickerChatHolder.imageView);
            else
                stickerChatHolder.imageView.setImageResource(sticker.getDrawableRes());
        } else {
            EmojiChatHolder emojiChatHolder = (EmojiChatHolder) viewHolder;

            final String text = adapterItem.getEmoji();

            final EmojiInformation emojiInformation = EmojiUtils.emojiInformation(text);
            final int res;

            if (emojiInformation.isOnlyEmojis && emojiInformation.emojis.size() == 1) {
                res = R.dimen.emoji_size_single_emoji;
            } else if (emojiInformation.isOnlyEmojis && emojiInformation.emojis.size() > 1) {
                res = R.dimen.emoji_size_only_emojis;
            } else {
                res = R.dimen.emoji_size_default;
            }

            emojiChatHolder.textView.setEmojiSizeRes(res, false);
            emojiChatHolder.textView.setText(text);
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(AdapterItem adapterItem) {
        items.add(adapterItem);
        notifyDataSetChanged();
    }

    static class EmojiChatHolder extends RecyclerView.ViewHolder {
        final EmojiTextView textView;

        EmojiChatHolder(final View view) {
            super(view);

            textView = view.findViewById(R.id.itemAdapterChatTextView);
        }
    }

    static class StickerChatHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;

        StickerChatHolder(final View view) {
            super(view);

            imageView = view.findViewById(R.id.itemAdapterChatImageView);
        }
    }

}



