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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.vanniktech.emoji.Emoji
import com.vanniktech.emoji.EmojiProvider
import com.vanniktech.emoji.variant.VariantEmoji

/**
 * Modular building block displaying a grid of emojis.
 *
 * @param emojis List of [Emoji] objects to display in the grid.
 * @param columns Number of columns in the grid.
 * @param onEmojiClick Callback invoked when an emoji cell is tapped.
 * @param onEmojiLongClick Callback invoked when an emoji cell is long-pressed.
 * @param modifier Modifier to be applied to the grid layout.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmojiGrid(
  emojis: List<Emoji>,
  columns: Int,
  onEmojiClick: (Emoji) -> Unit,
  onEmojiLongClick: (Emoji) -> Unit,
  modifier: Modifier = Modifier,
  provider: EmojiProvider? = null,
  variantEmoji: VariantEmoji? = null,
) {
  EmojiGrid(
    emojis = emojis,
    columns = columns,
    onEmojiClick = onEmojiClick,
    onEmojiLongClick = { emoji, _ -> onEmojiLongClick(emoji) },
    modifier = modifier,
    provider = provider,
    variantEmoji = variantEmoji,
  )
}

/**
 * Modular building block displaying a grid of emojis with bounds awareness, row capping, and theming.
 *
 * @param emojis List of [Emoji] objects to display in the grid.
 * @param columns Number of columns in the grid.
 * @param onEmojiClick Callback invoked when an emoji cell is tapped.
 * @param onEmojiLongClick Callback invoked with the emoji and window bounds when long-pressed.
 * @param modifier Modifier to be applied to the grid layout.
 * @param emojiGridRows Optional row capping factor for fixed-height grids.
 * @param colors Color scheme configuration for the grid and variant indicators.
 * @param showVariants Whether to display variant indicators on emojis that have variants.
 * @param provider Optional [EmojiProvider] supplying custom image graphics for emojis.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmojiGrid(
  emojis: List<Emoji>,
  columns: Int,
  onEmojiClick: (Emoji) -> Unit,
  onEmojiLongClick: (Emoji, Rect) -> Unit,
  modifier: Modifier = Modifier,
  emojiGridRows: Float? = null,
  colors: EmojiPickerColors = EmojiPickerDefaults.colors(),
  showVariants: Boolean = true,
  provider: EmojiProvider? = null,
  variantEmoji: VariantEmoji? = null,
) {
  val gridModifier = if (emojiGridRows != null) {
    modifier.height((emojiGridRows * 44 + 12).dp)
  } else {
    modifier.fillMaxSize()
  }

  LazyVerticalGrid(
    columns = GridCells.Fixed(columns),
    contentPadding = PaddingValues(0.dp),
    modifier = gridModifier,
  ) {
    items(
      items = emojis,
      key = { it.unicode },
      contentType = { "emoji_cell" },
    ) { emoji ->
      EmojiCell(
        emoji = emoji,
        onEmojiClick = onEmojiClick,
        onEmojiLongClick = onEmojiLongClick,
        colors = colors,
        showVariants = showVariants,
        provider = provider,
        variantEmoji = variantEmoji,
      )
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EmojiCell(
  emoji: Emoji,
  onEmojiClick: (Emoji) -> Unit,
  onEmojiLongClick: (Emoji, Rect) -> Unit,
  modifier: Modifier = Modifier,
  colors: EmojiPickerColors = EmojiPickerDefaults.colors(),
  showVariants: Boolean = true,
  provider: EmojiProvider? = null,
  variantEmoji: VariantEmoji? = null,
) {
  val hasVariants = remember(emoji, variantEmoji) {
    val variants = variantEmoji?.getVariants(emoji.base) ?: emoji.base.variants
    variants.isNotEmpty()
  }
  var cellBounds by remember { mutableStateOf<Rect?>(null) }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .aspectRatio(1f)
      .onGloballyPositioned { coordinates ->
        cellBounds = coordinates.boundsInWindow()
      },
    contentAlignment = Alignment.Center,
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(10.dp))
        .combinedClickable(
          onClick = { onEmojiClick(emoji) },
          onLongClick = if (hasVariants) {
            { onEmojiLongClick(emoji, cellBounds ?: Rect.Zero) }
          } else null,
        ),
      contentAlignment = Alignment.Center,
    ) {
      EmojiImage(
        emoji = emoji,
        provider = provider,
        modifier = Modifier.fillMaxSize(0.80f),
      )
    }

    if (showVariants && hasVariants) {
      val indicatorColor = colors.variantIndicatorColor
      Canvas(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(bottom = 2.dp, end = 2.dp)
          .size(5.dp),
      ) {
        val trianglePath = Path().apply {
          moveTo(size.width, 0f)
          lineTo(size.width, size.height)
          lineTo(0f, size.height)
          close()
        }
        drawPath(
          path = trianglePath,
          color = indicatorColor,
        )
      }
    }
  }
}
