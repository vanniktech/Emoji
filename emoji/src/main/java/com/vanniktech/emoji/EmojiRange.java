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
import com.vanniktech.emoji.emoji.Emoji;

public final class EmojiRange {
  public final int start;
  public final int end;
  public final Emoji emoji;

  EmojiRange(final int start, final int end, @NonNull final Emoji emoji) {
    this.start = start;
    this.end = end;
    this.emoji = emoji;
  }

  @Override public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final EmojiRange that = (EmojiRange) o;

    return start == that.start && end == that.end && emoji.equals(that.emoji);
  }

  @Override public int hashCode() {
    int result = start;
    result = 31 * result + end;
    result = 31 * result + emoji.hashCode();
    return result;
  }
}
