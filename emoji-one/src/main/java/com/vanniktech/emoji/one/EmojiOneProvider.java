package com.vanniktech.emoji.one;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.one.category.ActivityCategory;
import com.vanniktech.emoji.one.category.FlagsCategory;
import com.vanniktech.emoji.one.category.FoodCategory;
import com.vanniktech.emoji.one.category.NatureCategory;
import com.vanniktech.emoji.one.category.ObjectsCategory;
import com.vanniktech.emoji.one.category.PeopleCategory;
import com.vanniktech.emoji.one.category.SymbolsCategory;
import com.vanniktech.emoji.one.category.TravelCategory;

public final class EmojiOneProvider implements EmojiProvider {
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
