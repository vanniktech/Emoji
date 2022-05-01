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

package com.vanniktech.emoji.twitter.category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.twitter.R;
import com.vanniktech.emoji.twitter.TwitterEmoji;

@SuppressWarnings("PMD.MethodReturnsInternalArray") public final class SmileysAndPeopleCategory implements EmojiCategory {
  private static final TwitterEmoji[] EMOJIS = CategoryUtils.concatAll(SmileysAndPeopleCategoryChunk0.get(), SmileysAndPeopleCategoryChunk1.get(), SmileysAndPeopleCategoryChunk2.get());

  @Override @NonNull public TwitterEmoji[] getEmojis() {
    return EMOJIS;
  }

  @Override @DrawableRes public int getIcon() {
    return R.drawable.emoji_twitter_category_smileysandpeople;
  }

  @Override @StringRes public int getCategoryName() {
    return R.string.emoji_twitter_category_smileysandpeople;
  }
}
