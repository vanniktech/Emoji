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
import com.vanniktech.emoji.emoji.EmojiCategory;

final class TestEmojiProvider implements EmojiProvider {
  static EmojiProvider from(final Emoji... emojis) {
    return new TestEmojiProvider(emojis);
  }

  static EmojiProvider emptyCategories() {
    return new EmojiProvider() {
      @Override @NonNull public EmojiCategory[] getCategories() {
        return new EmojiCategory[0];
      }
    };
  }

  static EmojiProvider emptyEmojis() {
    return new EmojiProvider() {
      @Override @NonNull public EmojiCategory[] getCategories() {
        return new EmojiCategory[] {
          new EmojiCategory() {
            @Override @NonNull public Emoji[] getEmojis() {
              return new Emoji[0];
            }

            @Override public int getIcon() {
              return 0;
            }

            @Override public int getCategoryName() {
                return 0;
            }
          }
        };
      }
    };
  }

  final Emoji[] emojis;

  private TestEmojiProvider(final Emoji[] emojis) { // NOPMD
    this.emojis = emojis;
  }

  @Override @NonNull public EmojiCategory[] getCategories() {
    return new EmojiCategory[] {
      new EmojiCategory() {
        @Override @NonNull public Emoji[] getEmojis() {
          return emojis; // NOPMD
        }

        @Override public int getIcon() {
          return R.drawable.emoji_recent;
        }

          @Override public int getCategoryName() {
            return R.string.emoji_category_recent;
        }
      }
    };
  }
}
