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
 *
 */

package com.vanniktech.emoji;

import android.content.Context;
import android.text.Spannable;

/**
 * EmojiProviders can implement this interface to perform text emoji image replacement in a more efficient way.
 * For instance, the GooogleCompatEmojiProvider calls the corresponding AppCompat Emoji
 * Support library replace method directly for emoji in the default size.
 *
 * @since 6.0.0
 */
public interface EmojiReplacer {
  void replaceWithImages(Context context, Spannable text, float emojiSize, EmojiReplacer fallback);
}
