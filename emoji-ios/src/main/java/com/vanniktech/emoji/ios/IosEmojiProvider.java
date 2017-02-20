package com.vanniktech.emoji.ios;

import android.support.annotation.NonNull;

import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.ios.category.ActivityCategory;
import com.vanniktech.emoji.ios.category.FlagsCategory;
import com.vanniktech.emoji.ios.category.FoodsCategory;
import com.vanniktech.emoji.ios.category.NatureCategory;
import com.vanniktech.emoji.ios.category.ObjectsCategory;
import com.vanniktech.emoji.ios.category.PeopleCategory;
import com.vanniktech.emoji.ios.category.PlacesCategory;
import com.vanniktech.emoji.ios.category.SymbolsCategory;

public final class IosEmojiProvider implements EmojiProvider {
  @Override @NonNull public EmojiCategory[] getCategories() {
    return new EmojiCategory[] {
      new PeopleCategory(),
      new NatureCategory(),
      new FoodsCategory(),
      new ActivityCategory(),
      new PlacesCategory(),
      new ObjectsCategory(),
      new SymbolsCategory(),
      new FlagsCategory()
    };
  }
}
