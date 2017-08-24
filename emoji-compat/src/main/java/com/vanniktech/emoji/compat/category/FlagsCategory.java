package com.vanniktech.emoji.compat.category;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import com.vanniktech.emoji.compat.CompatEmoji;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.compat.R;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class FlagsCategory implements EmojiCategory {
  private static final Emoji[] DATA = new CompatEmoji[] {
    new CompatEmoji(0x1f3f3),
    new CompatEmoji(0x1f3f4),
    new CompatEmoji(0x1f3c1),
    new CompatEmoji(0x1f6a9),
    new CompatEmoji(new int[] { 0x1f3f3, 0xfe0f, 0x200d, 0x1f308 }),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1eb }),  //_1f1e6_1f1eb),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1fd }),  //_1f1e6_1f1fd),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1f1 }),  //_1f1e6_1f1f1),
    new CompatEmoji(new int[] { 0x1f1e9, 0x1f1ff }),  //_1f1e9_1f1ff),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1f8 }),  //_1f1e6_1f1f8),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1e9 }),  //_1f1e6_1f1e9),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1f4 }),  //_1f1e6_1f1f4),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1ee }),  //_1f1e6_1f1ee),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1f6 }),  //_1f1e6_1f1f6),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1ec }),  //_1f1e6_1f1ec),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1f7 }),  //_1f1e6_1f1f7),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1f2 }),  //_1f1e6_1f1f2),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1fc }),  //_1f1e6_1f1fc),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1f9 }),  //_1f1e6_1f1f9),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1ff }),  //_1f1e6_1f1ff),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1f8 }),  //_1f1e7_1f1f8),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1ed }),  //_1f1e7_1f1ed),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1e9 }),  //_1f1e7_1f1e9),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1e7 }),  //_1f1e7_1f1e7),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1fe }),  //_1f1e7_1f1fe),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1ea }),  //_1f1e7_1f1ea),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1ff }),  //_1f1e7_1f1ff),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1ef }),  //_1f1e7_1f1ef),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1f2 }),  //_1f1e7_1f1f2),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1f9 }),  //_1f1e7_1f1f9),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1f4 }),  //_1f1e7_1f1f4),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1e6 }),  //_1f1e7_1f1e6),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1fc }),  //_1f1e7_1f1fc),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1f7 }),  //_1f1e7_1f1f7),
    new CompatEmoji(new int[] { 0x1f1fb, 0x1f1ec }),  //_1f1fb_1f1ec),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1f3 }),  //_1f1e7_1f1f3),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1ec }),  //_1f1e7_1f1ec),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1eb }),  //_1f1e7_1f1eb),
    new CompatEmoji(new int[] { 0x1f1e7, 0x1f1ee }),  //_1f1e7_1f1ee),
    new CompatEmoji(new int[] { 0x1f1f0, 0x1f1ed }),  //_1f1f0_1f1ed),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1f2 }),  //_1f1e8_1f1f2),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1e6 }),  //_1f1e8_1f1e6),
    new CompatEmoji(new int[] { 0x1f1ee, 0x1f1e8 }),  //_1f1ee_1f1e8),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1fb }),  //_1f1e8_1f1fb),
    new CompatEmoji(new int[] { 0x1f1f0, 0x1f1fe }),  //_1f1f0_1f1fe),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1eb }),  //_1f1e8_1f1eb),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1e9 }),  //_1f1f9_1f1e9),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1f1 }),  //_1f1e8_1f1f1),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1f3 }),  //_1f1e8_1f1f3),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1fd }),  //_1f1e8_1f1fd),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1e8 }),  //_1f1e8_1f1e8),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1f4 }),  //_1f1e8_1f1f4),
    new CompatEmoji(new int[] { 0x1f1f0, 0x1f1f2 }),  //_1f1f0_1f1f2),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1ec }),  //_1f1e8_1f1ec),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1e9 }),  //_1f1e8_1f1e9),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1f0 }),  //_1f1e8_1f1f0),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1f7 }),  //_1f1e8_1f1f7),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1ee }),  //_1f1e8_1f1ee),
    new CompatEmoji(new int[] { 0x1f1ed, 0x1f1f7 }),  //_1f1ed_1f1f7),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1fa }),  //_1f1e8_1f1fa),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1fc }),  //_1f1e8_1f1fc),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1fe }),  //_1f1e8_1f1fe),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1ff }),  //_1f1e8_1f1ff),
    new CompatEmoji(new int[] { 0x1f1e9, 0x1f1f0 }),  //_1f1e9_1f1f0),
    new CompatEmoji(new int[] { 0x1f1e9, 0x1f1ef }),  //_1f1e9_1f1ef),
    new CompatEmoji(new int[] { 0x1f1e9, 0x1f1f2 }),  //_1f1e9_1f1f2),
    new CompatEmoji(new int[] { 0x1f1e9, 0x1f1f4 }),  //_1f1e9_1f1f4),
    new CompatEmoji(new int[] { 0x1f1ea, 0x1f1e8 }),  //_1f1ea_1f1e8),
    new CompatEmoji(new int[] { 0x1f1ea, 0x1f1ec }),  //_1f1ea_1f1ec),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1fb }),  //_1f1f8_1f1fb),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1f6 }),  //_1f1ec_1f1f6),
    new CompatEmoji(new int[] { 0x1f1ea, 0x1f1f7 }),  //_1f1ea_1f1f7),
    new CompatEmoji(new int[] { 0x1f1ea, 0x1f1ea }),  //_1f1ea_1f1ea),
    new CompatEmoji(new int[] { 0x1f1ea, 0x1f1f9 }),  //_1f1ea_1f1f9),
    new CompatEmoji(new int[] { 0x1f1ea, 0x1f1fa }),  //_1f1ea_1f1fa),
    new CompatEmoji(new int[] { 0x1f1eb, 0x1f1f4 }),  //_1f1eb_1f1f4),
    new CompatEmoji(new int[] { 0x1f1eb, 0x1f1ef }),  //_1f1eb_1f1ef),
    new CompatEmoji(new int[] { 0x1f1eb, 0x1f1ee }),  //_1f1eb_1f1ee),
    new CompatEmoji(new int[] { 0x1f1eb, 0x1f1f7 }),  //_1f1eb_1f1f7),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1eb }),  //_1f1f5_1f1eb),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1e6 }),  //_1f1ec_1f1e6),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1f2 }),  //_1f1ec_1f1f2),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1ea }),  //_1f1ec_1f1ea),
    new CompatEmoji(new int[] { 0x1f1e9, 0x1f1ea }),  //_1f1e9_1f1ea),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1ed }),  //_1f1ec_1f1ed),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1ee }),  //_1f1ec_1f1ee),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1f7 }),  //_1f1ec_1f1f7),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1f1 }),  //_1f1ec_1f1f1),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1e9 }),  //_1f1ec_1f1e9),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1fa }),  //_1f1ec_1f1fa),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1f9 }),  //_1f1ec_1f1f9),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1ec }),  //_1f1ec_1f1ec),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1f3 }),  //_1f1ec_1f1f3),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1fc }),  //_1f1ec_1f1fc),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1fe }),  //_1f1ec_1f1fe),
    new CompatEmoji(new int[] { 0x1f1ed, 0x1f1f9 }),  //_1f1ed_1f1f9),
    new CompatEmoji(new int[] { 0x1f1ed, 0x1f1f3 }),  //_1f1ed_1f1f3),
    new CompatEmoji(new int[] { 0x1f1ed, 0x1f1f0 }),  //_1f1ed_1f1f0),
    new CompatEmoji(new int[] { 0x1f1ed, 0x1f1fa }),  //_1f1ed_1f1fa),
    new CompatEmoji(new int[] { 0x1f1ee, 0x1f1f8 }),  //_1f1ee_1f1f8),
    new CompatEmoji(new int[] { 0x1f1ee, 0x1f1f3 }),  //_1f1ee_1f1f3),
    new CompatEmoji(new int[] { 0x1f1ee, 0x1f1e9 }),  //_1f1ee_1f1e9),
    new CompatEmoji(new int[] { 0x1f1ee, 0x1f1f7 }),  //_1f1ee_1f1f7),
    new CompatEmoji(new int[] { 0x1f1ee, 0x1f1f6 }),  //_1f1ee_1f1f6),
    new CompatEmoji(new int[] { 0x1f1ee, 0x1f1ea }),  //_1f1ee_1f1ea),
    new CompatEmoji(new int[] { 0x1f1ee, 0x1f1f2 }),  //_1f1ee_1f1f2),
    new CompatEmoji(new int[] { 0x1f1ee, 0x1f1f1 }),  //_1f1ee_1f1f1),
    new CompatEmoji(new int[] { 0x1f1ee, 0x1f1f9 }),  //_1f1ee_1f1f9),
    new CompatEmoji(new int[] { 0x1f1ef, 0x1f1f2 }),  //_1f1ef_1f1f2),
    new CompatEmoji(new int[] { 0x1f1ef, 0x1f1f5 }),  //_1f1ef_1f1f5),
    new CompatEmoji(0x1f38c),
    new CompatEmoji(new int[] { 0x1f1ef, 0x1f1ea }),  //_1f1ef_1f1ea),
    new CompatEmoji(new int[] { 0x1f1ef, 0x1f1f4 }),  //_1f1ef_1f1f4),
    new CompatEmoji(new int[] { 0x1f1f0, 0x1f1ff }),  //_1f1f0_1f1ff),
    new CompatEmoji(new int[] { 0x1f1f0, 0x1f1ea }),  //_1f1f0_1f1ea),
    new CompatEmoji(new int[] { 0x1f1f0, 0x1f1ee }),  //_1f1f0_1f1ee),
    new CompatEmoji(new int[] { 0x1f1f0, 0x1f1fc }),  //_1f1f0_1f1fc),
    new CompatEmoji(new int[] { 0x1f1f0, 0x1f1ec }),  //_1f1f0_1f1ec),
    new CompatEmoji(new int[] { 0x1f1f1, 0x1f1e6 }),  //_1f1f1_1f1e6),
    new CompatEmoji(new int[] { 0x1f1f1, 0x1f1fb }),  //_1f1f1_1f1fb),
    new CompatEmoji(new int[] { 0x1f1f1, 0x1f1e7 }),  //_1f1f1_1f1e7),
    new CompatEmoji(new int[] { 0x1f1f1, 0x1f1f8 }),  //_1f1f1_1f1f8),
    new CompatEmoji(new int[] { 0x1f1f1, 0x1f1f7 }),  //_1f1f1_1f1f7),
    new CompatEmoji(new int[] { 0x1f1f1, 0x1f1fe }),  //_1f1f1_1f1fe),
    new CompatEmoji(new int[] { 0x1f1f1, 0x1f1ee }),  //_1f1f1_1f1ee),
    new CompatEmoji(new int[] { 0x1f1f1, 0x1f1f9 }),  //_1f1f1_1f1f9),
    new CompatEmoji(new int[] { 0x1f1f1, 0x1f1fa }),  //_1f1f1_1f1fa),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1f4 }),  //_1f1f2_1f1f4),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1f0 }),  //_1f1f2_1f1f0),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1ec }),  //_1f1f2_1f1ec),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1fc }),  //_1f1f2_1f1fc),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1fe }),  //_1f1f2_1f1fe),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1fb }),  //_1f1f2_1f1fb),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1f1 }),  //_1f1f2_1f1f1),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1f9 }),  //_1f1f2_1f1f9),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1ed }),  //_1f1f2_1f1ed),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1f7 }),  //_1f1f2_1f1f7),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1fa }),  //_1f1f2_1f1fa),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1fd }),  //_1f1f2_1f1fd),
    new CompatEmoji(new int[] { 0x1f1eb, 0x1f1f2 }),  //_1f1eb_1f1f2),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1e9 }),  //_1f1f2_1f1e9),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1e8 }),  //_1f1f2_1f1e8),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1f3 }),  //_1f1f2_1f1f3),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1ea }),  //_1f1f2_1f1ea),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1f8 }),  //_1f1f2_1f1f8),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1e6 }),  //_1f1f2_1f1e6),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1ff }),  //_1f1f2_1f1ff),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1f2 }),  //_1f1f2_1f1f2),
    new CompatEmoji(new int[] { 0x1f1f3, 0x1f1e6 }),  //_1f1f3_1f1e6),
    new CompatEmoji(new int[] { 0x1f1f3, 0x1f1f7 }),  //_1f1f3_1f1f7),
    new CompatEmoji(new int[] { 0x1f1f3, 0x1f1f5 }),  //_1f1f3_1f1f5),
    new CompatEmoji(new int[] { 0x1f1f3, 0x1f1f1 }),  //_1f1f3_1f1f1),
    new CompatEmoji(new int[] { 0x1f1f3, 0x1f1ff }),  //_1f1f3_1f1ff),
    new CompatEmoji(new int[] { 0x1f1f3, 0x1f1ee }),  //_1f1f3_1f1ee),
    new CompatEmoji(new int[] { 0x1f1f3, 0x1f1ea }),  //_1f1f3_1f1ea),
    new CompatEmoji(new int[] { 0x1f1f3, 0x1f1ec }),  //_1f1f3_1f1ec),
    new CompatEmoji(new int[] { 0x1f1f3, 0x1f1fa }),  //_1f1f3_1f1fa),
    new CompatEmoji(new int[] { 0x1f1f3, 0x1f1eb }),  //_1f1f3_1f1eb),
    new CompatEmoji(new int[] { 0x1f1f0, 0x1f1f5 }),  //_1f1f0_1f1f5),
    new CompatEmoji(new int[] { 0x1f1f2, 0x1f1f5 }),  //_1f1f2_1f1f5),
    new CompatEmoji(new int[] { 0x1f1f4, 0x1f1f2 }),  //_1f1f4_1f1f2),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1f0 }),  //_1f1f5_1f1f0),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1fc }),  //_1f1f5_1f1fc),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1f8 }),  //_1f1f5_1f1f8),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1e6 }),  //_1f1f5_1f1e6),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1ec }),  //_1f1f5_1f1ec),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1fe }),  //_1f1f5_1f1fe),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1ea }),  //_1f1f5_1f1ea),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1ed }),  //_1f1f5_1f1ed),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1f3 }),  //_1f1f5_1f1f3),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1f1 }),  //_1f1f5_1f1f1),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1f9 }),  //_1f1f5_1f1f9),
    new CompatEmoji(new int[] { 0x1f1f5, 0x1f1f7 }),  //_1f1f5_1f1f7),
    new CompatEmoji(new int[] { 0x1f1f6, 0x1f1e6 }),  //_1f1f6_1f1e6),
    new CompatEmoji(new int[] { 0x1f1f7, 0x1f1f4 }),  //_1f1f7_1f1f4),
    new CompatEmoji(new int[] { 0x1f1f7, 0x1f1fa }),  //_1f1f7_1f1fa),
    new CompatEmoji(new int[] { 0x1f1f7, 0x1f1fc }),  //_1f1f7_1f1fc),
    new CompatEmoji(new int[] { 0x1f1fc, 0x1f1f8 }),  //_1f1fc_1f1f8),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1f2 }),  //_1f1f8_1f1f2),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1f9 }),  //_1f1f8_1f1f9),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1e6 }),  //_1f1f8_1f1e6),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1f3 }),  //_1f1f8_1f1f3),
    new CompatEmoji(new int[] { 0x1f1f7, 0x1f1f8 }),  //_1f1f7_1f1f8),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1e8 }),  //_1f1f8_1f1e8),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1f1 }),  //_1f1f8_1f1f1),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1ec }),  //_1f1f8_1f1ec),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1fd }),  //_1f1f8_1f1fd),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1f0 }),  //_1f1f8_1f1f0),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1ee }),  //_1f1f8_1f1ee),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1e7 }),  //_1f1f8_1f1e7),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1f4 }),  //_1f1f8_1f1f4),
    new CompatEmoji(new int[] { 0x1f1ff, 0x1f1e6 }),  //_1f1ff_1f1e6),
    new CompatEmoji(new int[] { 0x1f1f0, 0x1f1f7 }),  //_1f1f0_1f1f7),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1f8 }),  //_1f1f8_1f1f8),
    new CompatEmoji(new int[] { 0x1f1f1, 0x1f1f0 }),  //_1f1f1_1f1f0),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1ed }),  //_1f1f8_1f1ed),
    new CompatEmoji(new int[] { 0x1f1f0, 0x1f1f3 }),  //_1f1f0_1f1f3),
    new CompatEmoji(new int[] { 0x1f1f1, 0x1f1e8 }),  //_1f1f1_1f1e8),
    new CompatEmoji(new int[] { 0x1f1fb, 0x1f1e8 }),  //_1f1fb_1f1e8),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1e9 }),  //_1f1f8_1f1e9),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1f7 }),  //_1f1f8_1f1f7),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1ff }),  //_1f1f8_1f1ff),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1ea }),  //_1f1f8_1f1ea),
    new CompatEmoji(new int[] { 0x1f1e8, 0x1f1ed }),  //_1f1e8_1f1ed),
    new CompatEmoji(new int[] { 0x1f1f8, 0x1f1fe }),  //_1f1f8_1f1fe),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1fc }),  //_1f1f9_1f1fc),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1ef }),  //_1f1f9_1f1ef),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1ff }),  //_1f1f9_1f1ff),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1ed }),  //_1f1f9_1f1ed),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1f1 }),  //_1f1f9_1f1f1),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1ec }),  //_1f1f9_1f1ec),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1f0 }),  //_1f1f9_1f1f0),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1f4 }),  //_1f1f9_1f1f4),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1f9 }),  //_1f1f9_1f1f9),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1f3 }),  //_1f1f9_1f1f3),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1f7 }),  //_1f1f9_1f1f7),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1f2 }),  //_1f1f9_1f1f2),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1e8 }),  //_1f1f9_1f1e8),
    new CompatEmoji(new int[] { 0x1f1f9, 0x1f1fb }),  //_1f1f9_1f1fb),
    new CompatEmoji(new int[] { 0x1f1fb, 0x1f1ee }),  //_1f1fb_1f1ee),
    new CompatEmoji(new int[] { 0x1f1fa, 0x1f1ec }),  //_1f1fa_1f1ec),
    new CompatEmoji(new int[] { 0x1f1fa, 0x1f1e6 }),  //_1f1fa_1f1e6),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1ea }),  //_1f1e6_1f1ea),
    new CompatEmoji(new int[] { 0x1f1ec, 0x1f1e7 }),  //_1f1ec_1f1e7),
    new CompatEmoji(new int[] { 0x1f1fa, 0x1f1fe }),  //_1f1fa_1f1fe),
    new CompatEmoji(new int[] { 0x1f1fa, 0x1f1ff }),  //_1f1fa_1f1ff),
    new CompatEmoji(new int[] { 0x1f1fb, 0x1f1fa }),  //_1f1fb_1f1fa),
    new CompatEmoji(new int[] { 0x1f1fb, 0x1f1e6 }),  //_1f1fb_1f1e6),
    new CompatEmoji(new int[] { 0x1f1fb, 0x1f1ea }),  //_1f1fb_1f1ea),
    new CompatEmoji(new int[] { 0x1f1fb, 0x1f1f3 }),  //_1f1fb_1f1f3),
    new CompatEmoji(new int[] { 0x1f1fe, 0x1f1ea }),  //_1f1fe_1f1ea),
    new CompatEmoji(new int[] { 0x1f1ff, 0x1f1f2 }),  //_1f1ff_1f1f2),
    new CompatEmoji(new int[] { 0x1f1ff, 0x1f1fc }),  //_1f1ff_1f1fc),
    new CompatEmoji(new int[] { 0x1f1e6, 0x1f1e8 }),  //_1f1e6_1f1e8),
    new CompatEmoji(new int[] { 0x1f1fa, 0x1f1f3 }),  //_1f1fa_1f1f3)
  };

  @Override @NonNull public Emoji[] getEmojis() {
    return DATA;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_compat_category_flags;
  }
}
