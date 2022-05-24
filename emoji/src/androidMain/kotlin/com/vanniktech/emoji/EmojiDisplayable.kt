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

import androidx.annotation.DimenRes
import androidx.annotation.Px

interface EmojiDisplayable {
  /** Returns the emoji size */
  fun getEmojiSize(): Float

  /** Sets the emoji size in pixels and automatically invalidates the text and renders it with the new size. */
  fun setEmojiSize(@Px pixels: Int)

  /** Sets the emoji size in pixels and automatically invalidates the text and renders it with the new size when `shouldInvalidate` is true. */
  fun setEmojiSize(@Px pixels: Int, shouldInvalidate: Boolean)

  /** Sets the emoji size in pixels with the provided resource and automatically invalidates the text and renders it with the new size. */
  fun setEmojiSizeRes(@DimenRes res: Int)

  /** Sets the emoji size in pixels with the provided resource and invalidates the text and renders it with the new size when `shouldInvalidate` is true. */
  fun setEmojiSizeRes(@DimenRes res: Int, shouldInvalidate: Boolean)
}
