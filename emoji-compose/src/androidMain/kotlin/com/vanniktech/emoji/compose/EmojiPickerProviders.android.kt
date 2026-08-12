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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanniktech.emoji.EmojiAndroidProvider
import com.vanniktech.emoji.EmojiCategory
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.EmojiProvider
import com.vanniktech.emoji.recent.RecentEmoji
import com.vanniktech.emoji.recent.RecentEmojiManager
import com.vanniktech.emoji.variant.VariantEmoji
import com.vanniktech.emoji.variant.VariantEmojiManager

/**
 * Returns the default [RecentEmojiManager] for Android using [LocalContext].
 */
@Composable actual fun rememberDefaultRecentEmoji(): RecentEmoji {
  val context = LocalContext.current
  return remember(context) { RecentEmojiManager(context) }
}

/**
 * Returns the default [VariantEmojiManager] for Android using [LocalContext].
 */
@Composable actual fun rememberDefaultVariantEmoji(): VariantEmoji {
  val context = LocalContext.current
  return remember(context) { VariantEmojiManager(context) }
}

/**
 * Renders the category tab icon using Android vector resources or a text emoji fallback.
 */
@Composable actual fun CategoryTabIcon(
  category: EmojiCategory?,
  isRecent: Boolean,
  isSelected: Boolean,
  colors: EmojiPickerColors,
  fallbackEmoji: String,
  provider: EmojiProvider?,
) {
  val tint = if (isSelected) colors.tabSelectedColor else colors.tabUnselectedColor
  val iconRes = remember(category, isRecent, provider) {
    if (isRecent) {
      com.vanniktech.emoji.R.drawable.emoji_recent
    } else if (category != null && provider is EmojiAndroidProvider) {
      try { provider.getIcon(category) } catch (_: Exception) { null }
    } else null
  }

  if (iconRes != null && iconRes != 0) {
    Icon(
      painter = painterResource(iconRes),
      contentDescription = null,
      tint = tint,
      modifier = Modifier.size(22.dp),
    )
  } else if (fallbackEmoji.isNotEmpty()) {
    Text(
      text = fallbackEmoji,
      fontSize = 18.sp,
    )
  }
}
