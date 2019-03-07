package com.vanniktech.emoji.google.category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.google.R;
import com.vanniktech.emoji.google.GoogleEmoji;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class ActivitiesCategory implements EmojiCategory {
  private static final GoogleEmoji[] DATA = new GoogleEmoji[] {
    new GoogleEmoji(0x1F383, 8, 17, false),
    new GoogleEmoji(0x1F384, 8, 18, false),
    new GoogleEmoji(0x1F386, 8, 25, false),
    new GoogleEmoji(0x1F387, 8, 26, false),
    new GoogleEmoji(0x2728, 49, 48, false),
    new GoogleEmoji(0x1F388, 8, 27, false),
    new GoogleEmoji(0x1F389, 8, 28, false),
    new GoogleEmoji(0x1F38A, 8, 29, false),
    new GoogleEmoji(0x1F38B, 8, 30, false),
    new GoogleEmoji(0x1F38D, 8, 32, false),
    new GoogleEmoji(0x1F38E, 8, 33, false),
    new GoogleEmoji(0x1F38F, 8, 34, false),
    new GoogleEmoji(0x1F390, 8, 35, false),
    new GoogleEmoji(0x1F391, 8, 36, false),
    new GoogleEmoji(0x1F380, 8, 14, false),
    new GoogleEmoji(0x1F381, 8, 15, false),
    new GoogleEmoji(new int[] { 0x1F397, 0xFE0F }, 8, 40, false),
    new GoogleEmoji(new int[] { 0x1F39F, 0xFE0F }, 8, 45, false),
    new GoogleEmoji(0x1F3AB, 9, 5, false),
    new GoogleEmoji(new int[] { 0x1F396, 0xFE0F }, 8, 39, false),
    new GoogleEmoji(0x1F3C6, 10, 19, false),
    new GoogleEmoji(0x1F3C5, 10, 18, false),
    new GoogleEmoji(0x1F947, 41, 42, false),
    new GoogleEmoji(0x1F948, 41, 43, false),
    new GoogleEmoji(0x1F949, 41, 44, false),
    new GoogleEmoji(0x26BD, 48, 26, false),
    new GoogleEmoji(0x26BE, 48, 27, false),
    new GoogleEmoji(0x1F3C0, 9, 26, false),
    new GoogleEmoji(0x1F3D0, 11, 33, false),
    new GoogleEmoji(0x1F3C8, 10, 26, false),
    new GoogleEmoji(0x1F3C9, 10, 27, false),
    new GoogleEmoji(0x1F3BE, 9, 24, false),
    new GoogleEmoji(0x1F3B1, 9, 11, false),
    new GoogleEmoji(0x1F3B3, 9, 13, false),
    new GoogleEmoji(0x1F3CF, 11, 32, false),
    new GoogleEmoji(0x1F3D1, 11, 34, false),
    new GoogleEmoji(0x1F3D2, 11, 35, false),
    new GoogleEmoji(0x1F3D3, 11, 36, false),
    new GoogleEmoji(0x1F3F8, 12, 22, false),
    new GoogleEmoji(0x1F94A, 41, 45, false),
    new GoogleEmoji(0x1F94B, 41, 46, false),
    new GoogleEmoji(0x1F945, 41, 41, false),
    new GoogleEmoji(0x1F3AF, 9, 9, false),
    new GoogleEmoji(0x26F3, 48, 41, false),
    new GoogleEmoji(new int[] { 0x26F8, 0xFE0F }, 48, 45, false),
    new GoogleEmoji(0x1F3A3, 8, 49, false),
    new GoogleEmoji(0x1F3BD, 9, 23, false),
    new GoogleEmoji(0x1F3BF, 9, 25, false),
    new GoogleEmoji(0x1F6F7, 37, 22, false),
    new GoogleEmoji(0x1F94C, 41, 47, false),
    new GoogleEmoji(0x1F3AE, 9, 8, false),
    new GoogleEmoji(new int[] { 0x1F579, 0xFE0F }, 29, 20, false),
    new GoogleEmoji(0x1F3B2, 9, 12, false),
    new GoogleEmoji(new int[] { 0x2660, 0xFE0F }, 48, 4, false),
    new GoogleEmoji(new int[] { 0x2665, 0xFE0F }, 48, 6, false),
    new GoogleEmoji(new int[] { 0x2666, 0xFE0F }, 48, 7, false),
    new GoogleEmoji(new int[] { 0x2663, 0xFE0F }, 48, 5, false),
    new GoogleEmoji(0x1F0CF, 0, 15, false),
    new GoogleEmoji(0x1F004, 0, 14, false),
    new GoogleEmoji(0x1F3B4, 9, 14, false)
  };

  @Override @NonNull public GoogleEmoji[] getEmojis() {
    return DATA;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_google_category_activities;
  }

  @Override @StringRes public int getCategoryName() {
    return R.string.emoji_google_category_activities;
  }
}
