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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.vanniktech.emoji.Emoji
import com.vanniktech.emoji.EmojiProvider
import com.vanniktech.emoji.filterMeaningfulVariants

private class EmojiPickerVariantPopupPositionProvider(
  private val targetBounds: Rect,
) : PopupPositionProvider {
  override fun calculatePosition(
    anchorBounds: IntRect,
    windowSize: IntSize,
    layoutDirection: LayoutDirection,
    popupContentSize: IntSize,
  ): IntOffset {
    val targetCenterX = (targetBounds.left + targetBounds.width / 2f).toInt()
    var x = targetCenterX - popupContentSize.width / 2
    x = x.coerceIn(16, (windowSize.width - popupContentSize.width - 16).coerceAtLeast(16))

    val spaceAbove = targetBounds.top
    val popupHeight = popupContentSize.height.toFloat()

    val y = if (spaceAbove >= popupHeight + 16f) {
      (targetBounds.top - popupHeight - 12f).toInt()
    } else {
      (targetBounds.bottom + 12f).toInt()
    }

    return IntOffset(x, y)
  }
}

/**
 * Floating popup for selecting variants of an emoji.
 *
 * @param baseEmoji The base [Emoji] whose variants will be displayed.
 * @param onEmojiPicked Callback invoked when a specific emoji variant is chosen.
 * @param onDismissRequest Callback invoked when the user dismisses the variant popup.
 * @param modifier Modifier to be applied to the popup surface layout.
 * @param targetBounds The window bounds of the anchor cell, used to position the popup dynamically.
 * @param colors Color scheme configuration for the variant popup.
 * @param provider Optional [EmojiProvider] supplying custom image graphics for variants.
 */
@Composable
fun EmojiPickerVariantPopup(
  baseEmoji: Emoji,
  onEmojiPicked: (Emoji) -> Unit,
  onDismissRequest: () -> Unit,
  modifier: Modifier = Modifier,
  targetBounds: Rect? = null,
  colors: EmojiPickerColors = EmojiPickerDefaults.colors(),
  provider: EmojiProvider? = null,
) {
  val allVariants = remember(baseEmoji) {
    val rootBase = baseEmoji.base
    val meaningful = filterMeaningfulVariants(rootBase)
    listOf(rootBase) + meaningful
  }

  val popupPositionProvider = remember(targetBounds) {
    targetBounds?.let { EmojiPickerVariantPopupPositionProvider(it) }
  }

  val popupProperties = remember {
    PopupProperties(
      focusable = true,
      dismissOnBackPress = true,
      dismissOnClickOutside = true,
      clippingEnabled = false,
    )
  }

  val variantRows = remember(allVariants) {
    if (allVariants.size > 6) {
      allVariants.chunked(6)
    } else {
      listOf(allVariants)
    }
  }

  val shape = if (variantRows.size > 1) RoundedCornerShape(24.dp) else CircleShape

  val content = @Composable {
    Surface(
      shape = shape,
      color = colors.variantPopupContainerColor,
      shadowElevation = 8.dp,
      tonalElevation = 6.dp,
      modifier = modifier,
    ) {
      Column(
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        variantRows.forEach { rowVariants ->
          Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            rowVariants.forEach { variant ->
              Box(
                modifier = Modifier
                  .size(40.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .clickable {
                    onEmojiPicked(variant)
                    onDismissRequest()
                  },
                contentAlignment = Alignment.Center,
              ) {
                EmojiImage(
                  emoji = variant,
                  provider = provider,
                  modifier = Modifier.fillMaxSize(0.80f),
                )
              }
            }
          }
        }
      }
    }
  }

  if (popupPositionProvider != null) {
    Popup(
      popupPositionProvider = popupPositionProvider,
      onDismissRequest = onDismissRequest,
      properties = popupProperties,
      content = content,
    )
  } else {
    Popup(
      alignment = Alignment.Center,
      onDismissRequest = onDismissRequest,
      properties = popupProperties,
      content = content,
    )
  }
}
