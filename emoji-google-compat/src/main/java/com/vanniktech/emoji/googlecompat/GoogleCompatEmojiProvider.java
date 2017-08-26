package com.vanniktech.emoji.googlecompat;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.googlecompat.category.ActivityCategory;
import com.vanniktech.emoji.googlecompat.category.FlagsCategory;
import com.vanniktech.emoji.googlecompat.category.FoodCategory;
import com.vanniktech.emoji.googlecompat.category.NatureCategory;
import com.vanniktech.emoji.googlecompat.category.ObjectsCategory;
import com.vanniktech.emoji.googlecompat.category.PeopleCategory;
import com.vanniktech.emoji.googlecompat.category.SymbolsCategory;
import com.vanniktech.emoji.googlecompat.category.TravelCategory;

public final class GoogleCompatEmojiProvider implements EmojiProvider {

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
