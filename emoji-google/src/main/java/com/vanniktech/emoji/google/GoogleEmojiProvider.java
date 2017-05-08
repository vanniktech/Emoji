package com.vanniktech.emoji.google;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.google.category.ActivityCategory;
import com.vanniktech.emoji.google.category.FlagsCategory;
import com.vanniktech.emoji.google.category.FoodCategory;
import com.vanniktech.emoji.google.category.NatureCategory;
import com.vanniktech.emoji.google.category.ObjectsCategory;
import com.vanniktech.emoji.google.category.PeopleCategory;
import com.vanniktech.emoji.google.category.SymbolsCategory;
import com.vanniktech.emoji.google.category.TravelCategory;

public final class GoogleEmojiProvider implements EmojiProvider {
  @Override @NonNull public EmojiCategory[] getCategories() {
    return new EmojiCategory[] {
      new PeopleCategory(),
      new NatureCategory(),
      new FoodCategory(),
      new ActivityCategory(),
      new TravelCategory(),
      new ObjectsCategory(),
      new SymbolsCategory(),
      new FlagsCategory()
    };
  }
}
