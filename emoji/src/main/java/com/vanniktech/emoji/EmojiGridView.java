package com.vanniktech.emoji;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.GridView;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;

class EmojiGridView extends GridView {
  protected EmojiArrayAdapter emojiArrayAdapter;

  EmojiGridView(final Context context) {
    super(context);

    final int width = getResources().getDimensionPixelSize(R.dimen.emoji_grid_view_column_width);
    final int spacing = getResources().getDimensionPixelSize(R.dimen.emoji_grid_view_spacing);

    setColumnWidth(width);
    setHorizontalSpacing(spacing);
    setVerticalSpacing(spacing);
    setPadding(spacing, spacing, spacing, spacing);
    setNumColumns(AUTO_FIT);
    setClipToPadding(false);
    setVerticalScrollBarEnabled(false);
  }

  public EmojiGridView init(@Nullable final OnEmojiClickedListener onEmojiClickedListener,
                            @Nullable final OnEmojiLongClickedListener onEmojiLongClickedListener,
                            @NonNull final EmojiCategory category) {
    emojiArrayAdapter = new EmojiArrayAdapter(getContext(), category.getEmojis(), onEmojiClickedListener, onEmojiLongClickedListener);
    setAdapter(emojiArrayAdapter);

    return this;
  }
}
