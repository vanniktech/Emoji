package com.vanniktech.emoji.googlecompat;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.emoji.text.EmojiCompat;
import android.text.Spannable;
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

  @Override public void replaceWithImages(final Context context, final Spannable text, final float emojiSize, final float defaultEmojiSize, final EmojiReplacer fallback) {
    if (EmojiCompat.get().getLoadState() != EmojiCompat.LOAD_STATE_SUCCEEDED
            || emojiSize != defaultEmojiSize
            || EmojiCompat.get().process(text, 0, text.length()) != text) {
      fallback.replaceWithImages(context, text, emojiSize, defaultEmojiSize, null);
    }
  }
}
