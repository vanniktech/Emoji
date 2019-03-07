package com.vanniktech.emoji.google.category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.google.R;
import com.vanniktech.emoji.google.GoogleEmoji;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class FoodAndDrinkCategory implements EmojiCategory {
  private static final GoogleEmoji[] DATA = new GoogleEmoji[] {
    new GoogleEmoji(0x1F347, 7, 9, false),
    new GoogleEmoji(0x1F348, 7, 10, false),
    new GoogleEmoji(0x1F349, 7, 11, false),
    new GoogleEmoji(0x1F34A, 7, 12, false),
    new GoogleEmoji(0x1F34B, 7, 13, false),
    new GoogleEmoji(0x1F34C, 7, 14, false),
    new GoogleEmoji(0x1F34D, 7, 15, false),
    new GoogleEmoji(0x1F34E, 7, 16, false),
    new GoogleEmoji(0x1F34F, 7, 17, false),
    new GoogleEmoji(0x1F350, 7, 18, false),
    new GoogleEmoji(0x1F351, 7, 19, false),
    new GoogleEmoji(0x1F352, 7, 20, false),
    new GoogleEmoji(0x1F353, 7, 21, false),
    new GoogleEmoji(0x1F95D, 42, 9, false),
    new GoogleEmoji(0x1F345, 7, 7, false),
    new GoogleEmoji(0x1F965, 42, 17, false),
    new GoogleEmoji(0x1F951, 41, 49, false),
    new GoogleEmoji(0x1F346, 7, 8, false),
    new GoogleEmoji(0x1F954, 42, 0, false),
    new GoogleEmoji(0x1F955, 42, 1, false),
    new GoogleEmoji(0x1F33D, 6, 51, false),
    new GoogleEmoji(new int[] { 0x1F336, 0xFE0F }, 6, 44, false),
    new GoogleEmoji(0x1F952, 41, 50, false),
    new GoogleEmoji(0x1F966, 42, 18, false),
    new GoogleEmoji(0x1F344, 7, 6, false),
    new GoogleEmoji(0x1F95C, 42, 8, false),
    new GoogleEmoji(0x1F330, 6, 38, false),
    new GoogleEmoji(0x1F35E, 7, 32, false),
    new GoogleEmoji(0x1F950, 41, 48, false),
    new GoogleEmoji(0x1F956, 42, 2, false),
    new GoogleEmoji(0x1F968, 42, 20, false),
    new GoogleEmoji(0x1F95E, 42, 10, false),
    new GoogleEmoji(0x1F9C0, 42, 48, false),
    new GoogleEmoji(0x1F356, 7, 24, false),
    new GoogleEmoji(0x1F357, 7, 25, false),
    new GoogleEmoji(0x1F969, 42, 21, false),
    new GoogleEmoji(0x1F953, 41, 51, false),
    new GoogleEmoji(0x1F354, 7, 22, false),
    new GoogleEmoji(0x1F35F, 7, 33, false),
    new GoogleEmoji(0x1F355, 7, 23, false),
    new GoogleEmoji(0x1F32D, 6, 35, false),
    new GoogleEmoji(0x1F96A, 42, 22, false),
    new GoogleEmoji(0x1F32E, 6, 36, false),
    new GoogleEmoji(0x1F32F, 6, 37, false),
    new GoogleEmoji(0x1F959, 42, 5, false),
    new GoogleEmoji(0x1F95A, 42, 6, false),
    new GoogleEmoji(0x1F373, 8, 1, false),
    new GoogleEmoji(0x1F958, 42, 4, false),
    new GoogleEmoji(0x1F372, 8, 0, false),
    new GoogleEmoji(0x1F963, 42, 15, false),
    new GoogleEmoji(0x1F957, 42, 3, false),
    new GoogleEmoji(0x1F37F, 8, 13, false),
    new GoogleEmoji(0x1F96B, 42, 23, false),
    new GoogleEmoji(0x1F371, 7, 51, false),
    new GoogleEmoji(0x1F358, 7, 26, false),
    new GoogleEmoji(0x1F359, 7, 27, false),
    new GoogleEmoji(0x1F35A, 7, 28, false),
    new GoogleEmoji(0x1F35B, 7, 29, false),
    new GoogleEmoji(0x1F35C, 7, 30, false),
    new GoogleEmoji(0x1F35D, 7, 31, false),
    new GoogleEmoji(0x1F360, 7, 34, false),
    new GoogleEmoji(0x1F362, 7, 36, false),
    new GoogleEmoji(0x1F363, 7, 37, false),
    new GoogleEmoji(0x1F364, 7, 38, false),
    new GoogleEmoji(0x1F365, 7, 39, false),
    new GoogleEmoji(0x1F361, 7, 35, false),
    new GoogleEmoji(0x1F95F, 42, 11, false),
    new GoogleEmoji(0x1F960, 42, 12, false),
    new GoogleEmoji(0x1F961, 42, 13, false),
    new GoogleEmoji(0x1F366, 7, 40, false),
    new GoogleEmoji(0x1F367, 7, 41, false),
    new GoogleEmoji(0x1F368, 7, 42, false),
    new GoogleEmoji(0x1F369, 7, 43, false),
    new GoogleEmoji(0x1F36A, 7, 44, false),
    new GoogleEmoji(0x1F382, 8, 16, false),
    new GoogleEmoji(0x1F370, 7, 50, false),
    new GoogleEmoji(0x1F967, 42, 19, false),
    new GoogleEmoji(0x1F36B, 7, 45, false),
    new GoogleEmoji(0x1F36C, 7, 46, false),
    new GoogleEmoji(0x1F36D, 7, 47, false),
    new GoogleEmoji(0x1F36E, 7, 48, false),
    new GoogleEmoji(0x1F36F, 7, 49, false),
    new GoogleEmoji(0x1F37C, 8, 10, false),
    new GoogleEmoji(0x1F95B, 42, 7, false),
    new GoogleEmoji(0x2615, 47, 24, false),
    new GoogleEmoji(0x1F375, 8, 3, false),
    new GoogleEmoji(0x1F376, 8, 4, false),
    new GoogleEmoji(0x1F37E, 8, 12, false),
    new GoogleEmoji(0x1F377, 8, 5, false),
    new GoogleEmoji(0x1F378, 8, 6, false),
    new GoogleEmoji(0x1F379, 8, 7, false),
    new GoogleEmoji(0x1F37A, 8, 8, false),
    new GoogleEmoji(0x1F37B, 8, 9, false),
    new GoogleEmoji(0x1F942, 41, 38, false),
    new GoogleEmoji(0x1F943, 41, 39, false),
    new GoogleEmoji(0x1F964, 42, 16, false),
    new GoogleEmoji(0x1F962, 42, 14, false),
    new GoogleEmoji(new int[] { 0x1F37D, 0xFE0F }, 8, 11, false),
    new GoogleEmoji(0x1F374, 8, 2, false),
    new GoogleEmoji(0x1F944, 41, 40, false),
    new GoogleEmoji(0x1F52A, 27, 44, false),
    new GoogleEmoji(0x1F3FA, 12, 24, false)
  };

  @Override @NonNull public GoogleEmoji[] getEmojis() {
    return DATA;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_google_category_foodanddrink;
  }

  @Override @StringRes public int getCategoryName() {
    return R.string.emoji_google_category_foodanddrink;
  }
}
