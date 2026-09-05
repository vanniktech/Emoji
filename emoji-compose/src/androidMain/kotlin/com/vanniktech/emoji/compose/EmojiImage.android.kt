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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.vanniktech.emoji.Emoji
import com.vanniktech.emoji.EmojiAndroidProvider
import com.vanniktech.emoji.EmojiProvider
import kotlin.math.roundToInt

/**
 * Android actual implementation of [EmojiImage].
 * Renders the provider drawable via Canvas, falling back to unicode text font.
 */
@Composable actual fun EmojiImage(
  emoji: Emoji,
  provider: EmojiProvider?,
  modifier: Modifier,
) {
  val context = LocalContext.current
  val drawable = remember(emoji, provider, context) {
    if (provider is EmojiAndroidProvider) {
      try {
        provider.getDrawable(emoji, context)
      } catch (_: Exception) { null }
    } else null
  }

  if (drawable != null) {
    Canvas(modifier = modifier) {
      drawIntoCanvas { canvas ->
        drawable.setBounds(0, 0, size.width.roundToInt(), size.height.roundToInt())
        drawable.draw(canvas.nativeCanvas)
      }
    }
  } else {
    BoxWithConstraints(
      modifier = modifier,
      contentAlignment = Alignment.Center,
    ) {
      val fontSize = (minOf(maxWidth, maxHeight).value * 0.75f).sp
      Text(
        text = emoji.unicode,
        fontSize = fontSize,
        textAlign = TextAlign.Center,
      )
    }
  }
}
