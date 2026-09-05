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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.dp
import com.vanniktech.emoji.Emoji
import com.vanniktech.emoji.EmojiCategory
import com.vanniktech.emoji.EmojiProvider
import com.vanniktech.emoji.recent.RecentEmoji
import com.vanniktech.emoji.variant.VariantEmoji

/**
 * A customizable Jetpack Compose Emoji Picker component displaying category tabs, recents,
 * and emoji grids with skin-tone variant support.
 *
 * @param emojiProvider The [EmojiProvider] supplying categories and emojis (e.g. GoogleEmojiProvider()).
 * @param onEmojiPicked Callback invoked when an emoji is clicked or selected from the variant picker.
 * @param modifier Modifier to be applied to the layout container.
 * @param recentEmoji Provider for recents persistence; defaults to platform implementation.
 * @param variantEmoji Provider for skin-tone variant persistence; defaults to platform implementation.
 * @param autoAddRecentEmoji Whether clicking an emoji should automatically add it to [recentEmoji]. Set to `false` for confirmation-based selection dialogs.
 * @param columns Number of columns in the emoji grid.
 * @param emojiGridRows Optional row capping factor for fixed-height grids.
 * @param colors Color scheme configuration for the picker.
 * @param onCategoryIcon Optional custom callback mapping an [EmojiCategory] to a fallback Unicode string.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmojiPicker(
  onEmojiPicked: (Emoji) -> Unit,
  modifier: Modifier = Modifier,
  emojiProvider: EmojiProvider? = null,
  recentEmoji: RecentEmoji = rememberDefaultRecentEmoji(),
  variantEmoji: VariantEmoji = rememberDefaultVariantEmoji(),
  autoAddRecentEmoji: Boolean = true,
  columns: Int = 7,
  emojiGridRows: Float? = null,
  colors: EmojiPickerColors = EmojiPickerDefaults.colors(),
  onCategoryIcon: ((EmojiCategory) -> String)? = null,
) {
  var selectedTabIndex by remember { mutableIntStateOf(0) }
  var emojiForVariantPicker by remember { mutableStateOf<Pair<Emoji, Rect?>?>(null) }

  val categories = remember(emojiProvider) {
    emojiProvider?.categories ?: emptyArray()
  }

  val recentEmojis = remember(recentEmoji) {
    recentEmoji.getRecentEmojis().toList()
  }

  val hasRecentsTab = recentEmojis.isNotEmpty()
  val totalTabs = (if (hasRecentsTab) 1 else 0) + categories.size

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(colors.containerColor),
  ) {
    if (totalTabs > 0) {
      EmojiCategoryTabBar(
        categories = categories,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { selectedTabIndex = it },
        hasRecentsTab = hasRecentsTab,
        recentEmojis = recentEmojis,
        colors = colors,
        emojiProvider = emojiProvider,
        onCategoryIcon = onCategoryIcon,
      )
      Spacer(modifier = Modifier.height(4.dp))
    }

    val isRecentTab = hasRecentsTab && selectedTabIndex == 0

    val activeEmojis = remember(selectedTabIndex, hasRecentsTab, categories, recentEmojis, variantEmoji) {
      if (isRecentTab) {
        recentEmojis
      } else {
        val categoryIndex = if (hasRecentsTab) selectedTabIndex - 1 else selectedTabIndex
        categories.getOrNull(categoryIndex)?.emojis?.map { variantEmoji.getVariant(it) }.orEmpty()
      }
    }

    EmojiGrid(
      emojis = activeEmojis,
      columns = columns,
      onEmojiClick = { emoji ->
        if (autoAddRecentEmoji) {
          recentEmoji.addEmoji(emoji)
          recentEmoji.persist()
        }
        onEmojiPicked(emoji)
      },
      onEmojiLongClick = { emoji, bounds ->
        if (!isRecentTab && variantEmoji.getVariants(emoji.base).isNotEmpty()) {
          emojiForVariantPicker = emoji to bounds
        }
      },
      modifier = Modifier.weight(1f),
      emojiGridRows = emojiGridRows,
      colors = colors,
      showVariants = !isRecentTab,
      provider = emojiProvider,
      variantEmoji = variantEmoji,
    )
  }

  emojiForVariantPicker?.let { (baseEmoji, bounds) ->
    EmojiPickerVariantPopup(
      baseEmoji = baseEmoji,
      onEmojiPicked = { variant ->
        if (autoAddRecentEmoji) {
          recentEmoji.addEmoji(variant)
          recentEmoji.persist()
        }
        variantEmoji.addVariant(variant)
        variantEmoji.persist()
        onEmojiPicked(variant)
      },
      onDismissRequest = { emojiForVariantPicker = null },
      targetBounds = bounds,
      colors = colors,
      provider = emojiProvider,
      variantEmoji = variantEmoji,
    )
  }
}
