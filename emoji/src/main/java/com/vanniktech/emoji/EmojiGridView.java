/*
 * Copyright (C) 2016 - Niklas Baudy, Ruben Gees, Mario Đanić and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.vanniktech.emoji;

import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.GridView;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.listeners.OnEmojiClickListener;
import com.vanniktech.emoji.listeners.OnEmojiLongClickListener;

class EmojiGridView extends GridView {
  protected EmojiArrayAdapter emojiArrayAdapter;

  EmojiGridView(final Context context) {
    super(context);

    final Resources resources = getResources();
    final int width = resources.getDimensionPixelSize(R.dimen.emoji_grid_view_column_width);
    final int spacing = resources.getDimensionPixelSize(R.dimen.emoji_grid_view_spacing);

    setColumnWidth(width);
    setHorizontalSpacing(spacing);
    setVerticalSpacing(spacing);
    setPadding(spacing, spacing, spacing, spacing);
    setNumColumns(AUTO_FIT);
    setClipToPadding(false);
    setVerticalScrollBarEnabled(false);
  }

  public EmojiGridView init(@Nullable final OnEmojiClickListener onEmojiClickListener,
      @Nullable final OnEmojiLongClickListener onEmojiLongClickListener,
      @NonNull final EmojiCategory category, @NonNull final VariantEmoji variantManager,
      @NonNull final EmojiTheming theming) {
    emojiArrayAdapter = new EmojiArrayAdapter(getContext(), category.getEmojis(), variantManager,
            onEmojiClickListener, onEmojiLongClickListener, theming);

    setAdapter(emojiArrayAdapter);

    return this;
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    emojiArrayAdapter.destroy();
  }
}
