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

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Immutable color configuration for [EmojiPicker] and [EmojiGrid].
 *
 * @param containerColor Background color for the picker container.
 * @param tabSelectedColor Highlight color applied to the selected category tab icon.
 * @param tabUnselectedColor Color applied to unselected category tab icons.
 * @param dividerColor Color applied to the horizontal divider line below category tabs.
 * @param variantIndicatorColor Color for the triangle indicator drawn on emojis with skin-tone variants.
 * @param variantPopupContainerColor Background surface color of the floating [EmojiPickerVariantPopup] popup.
 */
@Immutable
data class EmojiPickerColors(
  val containerColor: Color,
  val tabSelectedColor: Color,
  val tabUnselectedColor: Color,
  val dividerColor: Color,
  val variantIndicatorColor: Color,
  val variantPopupContainerColor: Color,
)

/**
 * Default color factory for [EmojiPicker].
 */
object EmojiPickerDefaults {
  /**
   * Creates an [EmojiPickerColors] instance using Material Theme defaults.
   *
   * @param containerColor Background color for the picker container.
   * @param tabSelectedColor Color applied to selected category tab indicator.
   * @param tabUnselectedColor Color applied to unselected category tabs.
   * @param dividerColor Color applied to horizontal divider below tab bar.
   * @param variantIndicatorColor Color for the triangle variant indicator.
   * @param variantPopupContainerColor Background color of the floating variant popup dialog.
   */
  @Composable
  fun colors(
    containerColor: Color = MaterialTheme.colorScheme.background,
    tabSelectedColor: Color = MaterialTheme.colorScheme.primary,
    tabUnselectedColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    dividerColor: Color = MaterialTheme.colorScheme.outlineVariant,
    variantIndicatorColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
    variantPopupContainerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
  ): EmojiPickerColors {
    return EmojiPickerColors(
      containerColor = containerColor,
      tabSelectedColor = tabSelectedColor,
      tabUnselectedColor = tabUnselectedColor,
      dividerColor = dividerColor,
      variantIndicatorColor = variantIndicatorColor,
      variantPopupContainerColor = variantPopupContainerColor,
    )
  }
}
