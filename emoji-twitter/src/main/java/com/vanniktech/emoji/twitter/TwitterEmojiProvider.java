package com.vanniktech.emoji.twitter;

import androidx.annotation.NonNull;
import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.twitter.category.ActivitiesCategory;
import com.vanniktech.emoji.twitter.category.AnimalsAndNatureCategory;
import com.vanniktech.emoji.twitter.category.FlagsCategory;
import com.vanniktech.emoji.twitter.category.FoodAndDrinkCategory;
import com.vanniktech.emoji.twitter.category.ObjectsCategory;
import com.vanniktech.emoji.twitter.category.SmileysAndPeopleCategory;
import com.vanniktech.emoji.twitter.category.SymbolsCategory;
import com.vanniktech.emoji.twitter.category.TravelAndPlacesCategory;

public final class TwitterEmojiProvider implements EmojiProvider {
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
