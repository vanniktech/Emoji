package com.vanniktech.emoji_stickers.stickers;


import com.vanniktech.emoji_stickers.R;

import java.util.List;

public class StickerSettings {

    private final List<Sticker> stickers;
    private final int stickerTabIcon;
    private final int emojiTabIcon;
    private final StickerListener stickerListener;


    private StickerSettings(Builder builder) {
        this.stickers = builder.stickers;
        this.stickerTabIcon = builder.stickerTabIcon;
        this.emojiTabIcon = builder.emojiTabIcon;
        this.stickerListener = builder.stickerListener;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Sticker> getStickers() {
        return stickers;
    }

    public int getStickerTabIcon() {
        return stickerTabIcon;
    }

    public int getEmojiTabIcon() {
        return emojiTabIcon;
    }

    public StickerListener getStickerListener() {
        return stickerListener;
    }

    public static class Builder {
        private List<Sticker> stickers;
        private int stickerTabIcon = R.drawable.emoji_sticker_tab;
        private int emojiTabIcon = R.drawable.emoji_emoji_tab;
        private StickerListener stickerListener;

        public Builder stickers(List<Sticker> stickers) {
            this.stickers = stickers;
            return this;
        }

        public Builder stickerTabIcon(int stickerTabIcon) {
            this.stickerTabIcon = stickerTabIcon;
            return this;
        }

        public Builder emojiTabIcon(int emojiTabIcon) {
            this.emojiTabIcon = emojiTabIcon;
            return this;
        }

        public Builder listener(StickerListener stickerListener) {
            this.stickerListener = stickerListener;
            return this;
        }

        public StickerSettings build() {
            return new StickerSettings(this);
        }
    }

}
