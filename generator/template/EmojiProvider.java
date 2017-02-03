package com.vanniktech.emoji.<%= package %>;

import android.support.annotation.NonNull;

import com.vanniktech.emoji.EmojiProvider;
import com.vanniktech.emoji.emoji.EmojiCategory;
<%= imports %>

import java.util.LinkedHashMap;
import java.util.Map;

public class <%= name %>Provider implements EmojiProvider {
    @Override
    @NonNull
    public Map<String, EmojiCategory> getCategories() {
        final LinkedHashMap<String, EmojiCategory> result = new LinkedHashMap<>();

        <%= categoryMapping %>

        return result;
    }
}
