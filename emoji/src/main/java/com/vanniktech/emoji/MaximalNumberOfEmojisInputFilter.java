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

import android.text.InputFilter;
import android.text.Spanned;

/** Input Filter that accepts only a certain number of Emojis. */
public final class MaximalNumberOfEmojisInputFilter implements InputFilter {
  private final int maxCount;

  public MaximalNumberOfEmojisInputFilter(final int maxCount) {
    this.maxCount = maxCount;
  }

  @Override public CharSequence filter(final CharSequence source, final int start, final int end, final Spanned dest, final int dstart, final int dend) {
    final EmojiInformation emojiInformation = EmojiUtils.emojiInformation(dest.subSequence(0, dest.length()));

    if (emojiInformation.emojis.size() >= maxCount) {
      return ""; // Reject.
    }

    return null;
  }
}
