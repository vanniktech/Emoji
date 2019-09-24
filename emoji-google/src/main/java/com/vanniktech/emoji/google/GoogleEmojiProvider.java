package com.vanniktech.emoji.google;

import androidx.annotation.NonNull;
import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.google.category.ActivitiesCategory;
import com.vanniktech.emoji.google.category.AnimalsAndNatureCategory;
import com.vanniktech.emoji.google.category.FlagsCategory;
import com.vanniktech.emoji.google.category.FoodAndDrinkCategory;
import com.vanniktech.emoji.google.category.ObjectsCategory;
import com.vanniktech.emoji.google.category.SmileysAndPeopleCategory;
import com.vanniktech.emoji.google.category.SymbolsCategory;
import com.vanniktech.emoji.google.category.TravelAndPlacesCategory;

public final class GoogleEmojiProvider implements EmojiProvider {
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
