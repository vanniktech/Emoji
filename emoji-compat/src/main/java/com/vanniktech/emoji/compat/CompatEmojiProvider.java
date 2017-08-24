package com.vanniktech.emoji.compat;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.compat.category.ActivityCategory;
import com.vanniktech.emoji.compat.category.FlagsCategory;
import com.vanniktech.emoji.compat.category.FoodCategory;
import com.vanniktech.emoji.compat.category.NatureCategory;
import com.vanniktech.emoji.compat.category.ObjectsCategory;
import com.vanniktech.emoji.compat.category.PeopleCategory;
import com.vanniktech.emoji.compat.category.SymbolsCategory;
import com.vanniktech.emoji.compat.category.TravelCategory;

public final class CompatEmojiProvider implements EmojiProvider {

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
