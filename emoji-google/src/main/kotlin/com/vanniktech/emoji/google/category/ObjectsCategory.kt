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
 */

package com.vanniktech.emoji.google.category

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.vanniktech.emoji.emoji.EmojiCategory
import com.vanniktech.emoji.google.GoogleEmoji

internal class ObjectsCategory : EmojiCategory {
  @get:DrawableRes override val icon: Int
    get() = com.vanniktech.emoji.google.R.drawable.emoji_google_category_objects

  @get:StringRes override val categoryName: Int
    get() = com.vanniktech.emoji.google.R.string.emoji_google_category_objects

  override val emojis = ALL_EMOJIS

  private companion object {
    val ALL_EMOJIS: Array<GoogleEmoji> = CategoryUtils.concatAll(ObjectsCategoryChunk0.EMOJIS, ObjectsCategoryChunk1.EMOJIS)
  }
}
