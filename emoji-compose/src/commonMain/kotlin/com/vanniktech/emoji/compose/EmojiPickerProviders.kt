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

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import emoji.emoji_compose.generated.resources.Res
import emoji.emoji_compose.generated.resources.directions_car_24px
import emoji.emoji_compose.generated.resources.emoji_food_beverage_24px
import emoji.emoji_compose.generated.resources.emoji_nature_24px
import emoji.emoji_compose.generated.resources.emoji_objects_24px
import emoji.emoji_compose.generated.resources.emoji_symbols_24px
import emoji.emoji_compose.generated.resources.flag_24px
import emoji.emoji_compose.generated.resources.schedule_24px
import emoji.emoji_compose.generated.resources.sentiment_very_satisfied_24px
import emoji.emoji_compose.generated.resources.sports_soccer_24px
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Returns the default [RecentEmoji] implementation for the current platform target.
 */
@Composable expect fun rememberDefaultRecentEmoji(): RecentEmoji

/**
 * Returns the default [VariantEmoji] implementation for the current platform target.
 */
@Composable expect fun rememberDefaultVariantEmoji(): VariantEmoji

/**
 * Renders the category tab icon for the given category or recent tab using multiplatform vector resources.
 */
@Composable
fun CategoryTabIcon(
  category: EmojiCategory?,
  isRecent: Boolean,
  isSelected: Boolean,
  colors: EmojiPickerColors,
  fallbackEmoji: String,
  provider: EmojiProvider? = null,
) {
  val tint = if (isSelected) colors.tabSelectedColor else colors.tabUnselectedColor
  val res: DrawableResource? = when {
    isRecent -> Res.drawable.schedule_24px
    category == null -> null
    else -> when (category::class.simpleName) {
      "SmileysAndPeopleCategory" -> Res.drawable.sentiment_very_satisfied_24px
      "AnimalsAndNatureCategory" -> Res.drawable.emoji_nature_24px
      "FoodAndDrinkCategory" -> Res.drawable.emoji_food_beverage_24px
      "ActivitiesCategory" -> Res.drawable.sports_soccer_24px
      "TravelAndPlacesCategory" -> Res.drawable.directions_car_24px
      "ObjectsCategory" -> Res.drawable.emoji_objects_24px
      "SymbolsCategory" -> Res.drawable.emoji_symbols_24px
      "FlagsCategory" -> Res.drawable.flag_24px
      else -> null
    }
  }

  if (res != null) {
    Icon(
      painter = painterResource(res),
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
