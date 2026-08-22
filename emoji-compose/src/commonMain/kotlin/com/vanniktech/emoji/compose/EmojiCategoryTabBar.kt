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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vanniktech.emoji.Emoji
import com.vanniktech.emoji.EmojiCategory
import com.vanniktech.emoji.EmojiProvider

/**
 * Tab bar displaying category icons (and optional recents tab icon) for [EmojiPicker].
 */
@Composable
fun EmojiCategoryTabBar(
  categories: Array<EmojiCategory>,
  selectedTabIndex: Int,
  onTabSelected: (Int) -> Unit,
  modifier: Modifier = Modifier,
  hasRecentsTab: Boolean = false,
  recentEmojis: List<Emoji> = emptyList(),
  colors: EmojiPickerColors = EmojiPickerDefaults.colors(),
  emojiProvider: EmojiProvider? = null,
  onCategoryIcon: ((EmojiCategory) -> String)? = null,
) {
  Column(modifier = modifier) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(44.dp)
        .padding(horizontal = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      if (hasRecentsTab) {
        val isSelected = selectedTabIndex == 0
        val recentFallbackEmoji = recentEmojis.firstOrNull()?.unicode ?: ""
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
              color = if (isSelected) colors.tabSelectedColor.copy(alpha = 0.12f) else Color.Transparent,
            )
            .clickable { onTabSelected(0) },
          contentAlignment = Alignment.Center,
        ) {
          CategoryTabIcon(
            category = null,
            isRecent = true,
            isSelected = isSelected,
            colors = colors,
            fallbackEmoji = recentFallbackEmoji,
            provider = emojiProvider,
          )
        }
      }

      categories.forEachIndexed { index, category ->
        val tabIndex = if (hasRecentsTab) index + 1 else index
        val isSelected = selectedTabIndex == tabIndex
        val iconEmoji = onCategoryIcon?.invoke(category) ?: category.emojis.firstOrNull()?.unicode ?: ""
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
              color = if (isSelected) colors.tabSelectedColor.copy(alpha = 0.12f) else Color.Transparent,
            )
            .clickable { onTabSelected(tabIndex) },
          contentAlignment = Alignment.Center,
        ) {
          CategoryTabIcon(
            category = category,
            isRecent = false,
            isSelected = isSelected,
            colors = colors,
            fallbackEmoji = iconEmoji,
            provider = emojiProvider,
          )
        }
      }
    }

    HorizontalDivider(
      color = colors.dividerColor,
      thickness = 1.dp,
    )
  }
}
