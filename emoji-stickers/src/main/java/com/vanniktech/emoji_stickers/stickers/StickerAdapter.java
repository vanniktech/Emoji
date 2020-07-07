package com.vanniktech.emoji_stickers.stickers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vanniktech.emoji_stickers.R;

import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.StickerHolder> {
    private List<Sticker> stickers;

    public StickerAdapter(List<Sticker> stickers) {
        this.stickers = stickers;
    }

    private StickerListener stickerListener;

    public void setStickerListener(StickerListener stickerListener) {
        this.stickerListener = stickerListener;
    }

    @NonNull
    @Override
    public StickerAdapter.StickerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticker, parent, false);
        return new StickerHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerAdapter.StickerHolder holder, int position) {
        holder.bind(stickers.get(position));
    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }

    class StickerHolder extends RecyclerView.ViewHolder {
        ImageView imgSticker;

        public StickerHolder(@NonNull View itemView) {
            super(itemView);

            imgSticker = itemView.findViewById(R.id.img_row);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stickerListener != null)
                        stickerListener.onClick(stickers.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (stickerListener != null)
                        stickerListener.onLongClick(stickers.get(getAdapterPosition()));
                    return true;
                }
            });
        }

        public void bind(Sticker sticker) {

            if (sticker.getPath() != null) {
                Glide.with(itemView.getContext()).load(sticker.getPath()).into(imgSticker);
            } else {
                imgSticker.setImageResource(sticker.getDrawableRes());
            }

        }

    }
}
