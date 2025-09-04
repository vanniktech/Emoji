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

package com.vanniktech.emoji

import kotlin.jvm.JvmField

data class EmojiInformation internal constructor(
  /**
   * The visual length with respect to the found [emojiRanges].
   * One [Emoji] can be made up of a bunch of characters but still displayed as a single one.
   */
  @JvmField val visualLength: Int,
  @JvmField val isOnlyEmojis: Boolean,
  @JvmField val emojiRanges: List<EmojiRange>,
) {
  val emojiCount = emojiRanges.size
}
