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

import java.util.Arrays;

public final class EmojiOneProvider implements EmojiProvider {
    @Override @NonNull public Iterable<EmojiCategory> getCategories() {
        return Arrays.asList(
                new PeopleCategory(),
                new NatureCategory(),
                new FoodsCategory(),
                new ActivityCategory(),
                new PlacesCategory(),
                new ObjectsCategory(),
                new SymbolsCategory(),
                new FlagsCategory()
        );
    }
}
