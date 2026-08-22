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
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
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


