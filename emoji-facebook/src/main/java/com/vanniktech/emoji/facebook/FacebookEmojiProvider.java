package com.vanniktech.emoji.facebook;

import androidx.annotation.NonNull;
import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.facebook.category.ActivitiesCategory;
import com.vanniktech.emoji.facebook.category.AnimalsAndNatureCategory;
import com.vanniktech.emoji.facebook.category.FlagsCategory;
import com.vanniktech.emoji.facebook.category.FoodAndDrinkCategory;
import com.vanniktech.emoji.facebook.category.ObjectsCategory;
import com.vanniktech.emoji.facebook.category.SmileysAndPeopleCategory;
import com.vanniktech.emoji.facebook.category.SymbolsCategory;
import com.vanniktech.emoji.facebook.category.TravelAndPlacesCategory;

public final class FacebookEmojiProvider implements EmojiProvider {
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
