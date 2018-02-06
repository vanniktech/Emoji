package com.vanniktech.emoji.one;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.one.category.ActivitiesCategory;
import com.vanniktech.emoji.one.category.AnimalsAndNatureCategory;
import com.vanniktech.emoji.one.category.FlagsCategory;
import com.vanniktech.emoji.one.category.FoodAndDrinkCategory;
import com.vanniktech.emoji.one.category.ObjectsCategory;
import com.vanniktech.emoji.one.category.SmileysAndPeopleCategory;
import com.vanniktech.emoji.one.category.SymbolsCategory;
import com.vanniktech.emoji.one.category.TravelAndPlacesCategory;

public final class EmojiOneProvider implements EmojiProvider {
  @Override @NonNull public EmojiCategory[] getCategories() {
    return new EmojiCategory[] {
      new SmileysAndPeopleCategory(),
      new AnimalsAndNatureCategory(),
      new FoodAndDrinkCategory(),
      new ActivitiesCategory(),
      new TravelAndPlacesCategory(),
      new ObjectsCategory(),
      new SymbolsCategory(),
      new FlagsCategory()
    };
  }
}
