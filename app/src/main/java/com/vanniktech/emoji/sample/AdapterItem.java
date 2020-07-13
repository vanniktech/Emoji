package com.vanniktech.emoji.sample;

import com.vanniktech.emoji_stickers.stickers.Sticker;

public class AdapterItem {
    private Sticker sticker;
    private String emoji;

    public Sticker getSticker() {
        return sticker;
    }

    public void setSticker(Sticker sticker) {
        this.sticker = sticker;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public AdapterItem(Sticker sticker, String emoji) {
        this.sticker = sticker;
        this.emoji = emoji;
    }
}
