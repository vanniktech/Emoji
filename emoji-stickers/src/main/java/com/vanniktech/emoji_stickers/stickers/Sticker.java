package com.vanniktech.emoji_stickers.stickers;

import androidx.annotation.DrawableRes;

public class Sticker {
    /*
    if the path is null it will load the drawableRes
    */
    private String path;
    @DrawableRes
    private int drawableRes;


    public Sticker(String path) {
        this.path = path;
    }

    public Sticker(int drawableRes) {
        this.drawableRes = drawableRes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
    }

    @Override
    public String toString() {
        return "Sticker{" +
                "path='" + path + '\'' +
                ", drawableRes=" + drawableRes +
                '}';
    }
}
