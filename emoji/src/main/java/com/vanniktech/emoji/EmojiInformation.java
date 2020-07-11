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

import androidx.annotation.NonNull;
import java.util.List;

public final class EmojiInformation {
  public final boolean isOnlyEmojis;
  @NonNull public final List<EmojiRange> emojis;

  EmojiInformation(final boolean isOnlyEmojis, @NonNull final List<EmojiRange> emojis) {
    this.isOnlyEmojis = isOnlyEmojis;
    this.emojis = emojis;
  }

  @Override public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final EmojiInformation that = (EmojiInformation) o;
    return isOnlyEmojis == that.isOnlyEmojis && emojis.equals(that.emojis);
  }

  @Override public int hashCode() {
    int result = isOnlyEmojis ? 1 : 0;
    result = 31 * result + emojis.hashCode();
    return result;
  }
}
