package com.vanniktech.emoji.one;

import android.support.annotation.NonNull;
import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.one.category.ActivityCategory;
import com.vanniktech.emoji.one.category.FlagsCategory;
import com.vanniktech.emoji.one.category.FoodsCategory;
import com.vanniktech.emoji.one.category.NatureCategory;
import com.vanniktech.emoji.one.category.ObjectsCategory;
import com.vanniktech.emoji.one.category.PeopleCategory;
import com.vanniktech.emoji.one.category.PlacesCategory;
import com.vanniktech.emoji.one.category.SymbolsCategory;
import java.util.LinkedHashMap;
import java.util.Map;

public final class EmojiOneProvider implements EmojiProvider {
  @Override @NonNull public Map<String, EmojiCategory> getCategories() {
    final Map<String, EmojiCategory> result = new LinkedHashMap<>();

    result.put("People", new PeopleCategory());
    result.put("Nature", new NatureCategory());
    result.put("Activity", new ActivityCategory());
    result.put("Places", new PlacesCategory());
    result.put("Symbols", new SymbolsCategory());
    result.put("Objects", new ObjectsCategory());
    result.put("Foods", new FoodsCategory());
    result.put("Flags", new FlagsCategory());

    return result;
  }
}
