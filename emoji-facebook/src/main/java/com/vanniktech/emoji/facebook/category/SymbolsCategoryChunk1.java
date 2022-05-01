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

package com.vanniktech.emoji.facebook.category;

import com.vanniktech.emoji.facebook.FacebookEmoji;

final class SymbolsCategoryChunk1 {
  @SuppressWarnings("PMD.ExcessiveMethodLength") static FacebookEmoji[] get() {
    return new FacebookEmoji[] {
      new FacebookEmoji(0x1F53A, new String[]{"small_red_triangle"}, 30, 22, false),
      new FacebookEmoji(0x1F53B, new String[]{"small_red_triangle_down"}, 30, 23, false),
      new FacebookEmoji(0x1F4A0, new String[]{"diamond_shape_with_a_dot_inside"}, 27, 47, false),
      new FacebookEmoji(0x1F518, new String[]{"radio_button"}, 29, 49, false),
      new FacebookEmoji(0x1F533, new String[]{"white_square_button"}, 30, 15, false),
      new FacebookEmoji(0x1F532, new String[]{"black_square_button"}, 30, 14, false)
    };
  }

  private SymbolsCategoryChunk1() {
    // No instances.
  }
}
