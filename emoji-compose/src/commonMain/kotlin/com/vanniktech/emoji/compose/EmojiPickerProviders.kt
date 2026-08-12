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

package com.vanniktech.emoji.compose

import androidx.compose.runtime.Composable
import com.vanniktech.emoji.EmojiCategory
import com.vanniktech.emoji.EmojiProvider
import com.vanniktech.emoji.recent.RecentEmoji
import com.vanniktech.emoji.variant.VariantEmoji

/**
 * Returns the default [RecentEmoji] implementation for the current platform target.
 */
@Composable expect fun rememberDefaultRecentEmoji(): RecentEmoji

/**
 * Returns the default [VariantEmoji] implementation for the current platform target.
 */
@Composable expect fun rememberDefaultVariantEmoji(): VariantEmoji

/**
 * Renders the category tab icon for the given category or recent tab.
 */
@Composable
expect fun CategoryTabIcon(
  category: EmojiCategory?,
  isRecent: Boolean,
  isSelected: Boolean,
  colors: EmojiPickerColors,
  fallbackEmoji: String,
  provider: EmojiProvider? = null,
)
