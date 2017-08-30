package com.vanniktech.emoji.googlecompat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.text.emoji.EmojiCompat;
import android.text.Spannable;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.EmojiReplacer;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.googlecompat.category.ActivityCategory;
import com.vanniktech.emoji.googlecompat.category.FlagsCategory;
import com.vanniktech.emoji.googlecompat.category.FoodCategory;
import com.vanniktech.emoji.googlecompat.category.NatureCategory;
import com.vanniktech.emoji.googlecompat.category.ObjectsCategory;
import com.vanniktech.emoji.googlecompat.category.PeopleCategory;
import com.vanniktech.emoji.googlecompat.category.SymbolsCategory;
import com.vanniktech.emoji.googlecompat.category.TravelCategory;

public final class GoogleCompatEmojiProvider implements EmojiProvider, EmojiReplacer {
  public GoogleCompatEmojiProvider(@NonNull final EmojiCompat emojiCompat) {
    if (emojiCompat == null) {
      throw new NullPointerException();
    }
  }

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

  @Override
  public void replaceWithImages(Context context, Spannable text, float emojiSize, float defaultEmojiSize) {
    if (EmojiCompat.get().getLoadState() != EmojiCompat.LOAD_STATE_SUCCEEDED
            || emojiSize != defaultEmojiSize
            || EmojiCompat.get().process(text, 0, text.length()) != text) {
      EmojiManager.replaceWithImagesImpl(context, text, emojiSize);
    }
  }
}
