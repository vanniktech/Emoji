package com.vanniktech.emoji.<%= package %>;

import android.support.annotation.NonNull;

import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
<%= imports %>

import java.util.Arrays;

public final class <%= name %>Provider implements EmojiProvider {
  @Override @NonNull public Iterable<EmojiCategory> getCategories() {
    return Arrays.asList(
        <%= categoryMapping %>
    );
  }
}
