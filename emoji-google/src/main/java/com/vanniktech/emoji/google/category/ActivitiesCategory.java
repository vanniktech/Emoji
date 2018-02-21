package com.vanniktech.emoji.google.category;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.google.R;
import com.vanniktech.emoji.google.GoogleEmoji;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class ActivitiesCategory implements EmojiCategory {
  private static final GoogleEmoji[] DATA = new GoogleEmoji[] {
    new GoogleEmoji(0x1F383, 8, 17),
    new GoogleEmoji(0x1F384, 8, 18),
    new GoogleEmoji(0x1F386, 8, 25),
    new GoogleEmoji(0x1F387, 8, 26),
    new GoogleEmoji(0x2728, 49, 48),
    new GoogleEmoji(0x1F388, 8, 27),
    new GoogleEmoji(0x1F389, 8, 28),
    new GoogleEmoji(0x1F38A, 8, 29),
    new GoogleEmoji(0x1F38B, 8, 30),
    new GoogleEmoji(0x1F38D, 8, 32),
    new GoogleEmoji(0x1F38E, 8, 33),
    new GoogleEmoji(0x1F38F, 8, 34),
    new GoogleEmoji(0x1F390, 8, 35),
    new GoogleEmoji(0x1F391, 8, 36),
    new GoogleEmoji(0x1F380, 8, 14),
    new GoogleEmoji(0x1F381, 8, 15),
    new GoogleEmoji(new int[] { 0x1F397, 0xFE0F }, 8, 40),
    new GoogleEmoji(new int[] { 0x1F39F, 0xFE0F }, 8, 45),
    new GoogleEmoji(0x1F3AB, 9, 5),
    new GoogleEmoji(new int[] { 0x1F396, 0xFE0F }, 8, 39),
    new GoogleEmoji(0x1F3C6, 10, 19),
    new GoogleEmoji(0x1F3C5, 10, 18),
    new GoogleEmoji(0x1F947, 41, 42),
    new GoogleEmoji(0x1F948, 41, 43),
    new GoogleEmoji(0x1F949, 41, 44),
    new GoogleEmoji(0x26BD, 48, 26),
    new GoogleEmoji(0x26BE, 48, 27),
    new GoogleEmoji(0x1F3C0, 9, 26),
    new GoogleEmoji(0x1F3D0, 11, 33),
    new GoogleEmoji(0x1F3C8, 10, 26),
    new GoogleEmoji(0x1F3C9, 10, 27),
    new GoogleEmoji(0x1F3BE, 9, 24),
    new GoogleEmoji(0x1F3B1, 9, 11),
    new GoogleEmoji(0x1F3B3, 9, 13),
    new GoogleEmoji(0x1F3CF, 11, 32),
    new GoogleEmoji(0x1F3D1, 11, 34),
    new GoogleEmoji(0x1F3D2, 11, 35),
    new GoogleEmoji(0x1F3D3, 11, 36),
    new GoogleEmoji(0x1F3F8, 12, 22),
    new GoogleEmoji(0x1F94A, 41, 45),
    new GoogleEmoji(0x1F94B, 41, 46),
    new GoogleEmoji(0x1F945, 41, 41),
    new GoogleEmoji(0x1F3AF, 9, 9),
    new GoogleEmoji(0x26F3, 48, 41),
    new GoogleEmoji(new int[] { 0x26F8, 0xFE0F }, 48, 45),
    new GoogleEmoji(0x1F3A3, 8, 49),
    new GoogleEmoji(0x1F3BD, 9, 23),
    new GoogleEmoji(0x1F3BF, 9, 25),
    new GoogleEmoji(0x1F6F7, 37, 22),
    new GoogleEmoji(0x1F94C, 41, 47),
    new GoogleEmoji(0x1F3AE, 9, 8),
    new GoogleEmoji(new int[] { 0x1F579, 0xFE0F }, 29, 20),
    new GoogleEmoji(0x1F3B2, 9, 12),
    new GoogleEmoji(new int[] { 0x2660, 0xFE0F }, 48, 4),
    new GoogleEmoji(new int[] { 0x2665, 0xFE0F }, 48, 6),
    new GoogleEmoji(new int[] { 0x2666, 0xFE0F }, 48, 7),
    new GoogleEmoji(new int[] { 0x2663, 0xFE0F }, 48, 5),
    new GoogleEmoji(0x1F0CF, 0, 15),
    new GoogleEmoji(0x1F004, 0, 14),
    new GoogleEmoji(0x1F3B4, 9, 14)
  };

  @Override @NonNull public GoogleEmoji[] getEmojis() {
    return DATA;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_google_category_activities;
  }
}
