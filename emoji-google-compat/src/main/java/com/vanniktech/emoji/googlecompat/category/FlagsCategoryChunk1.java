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

package com.vanniktech.emoji.googlecompat.category;

import com.vanniktech.emoji.googlecompat.GoogleCompatEmoji;

final class FlagsCategoryChunk1 {
  @SuppressWarnings("PMD.ExcessiveMethodLength") static GoogleCompatEmoji[] get() {
    return new GoogleCompatEmoji[] {
      new GoogleCompatEmoji(new int[] { 0x1F1FB, 0x1F1E6 }, new String[]{"flag-va"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FB, 0x1F1E8 }, new String[]{"flag-vc"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FB, 0x1F1EA }, new String[]{"flag-ve"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FB, 0x1F1EC }, new String[]{"flag-vg"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FB, 0x1F1EE }, new String[]{"flag-vi"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FB, 0x1F1F3 }, new String[]{"flag-vn"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FB, 0x1F1FA }, new String[]{"flag-vu"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FC, 0x1F1EB }, new String[]{"flag-wf"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FC, 0x1F1F8 }, new String[]{"flag-ws"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FD, 0x1F1F0 }, new String[]{"flag-xk"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FE, 0x1F1EA }, new String[]{"flag-ye"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FE, 0x1F1F9 }, new String[]{"flag-yt"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FF, 0x1F1E6 }, new String[]{"flag-za"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FF, 0x1F1F2 }, new String[]{"flag-zm"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F1FF, 0x1F1FC }, new String[]{"flag-zw"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F3F4, 0xE0067, 0xE0062, 0xE0065, 0xE006E, 0xE0067, 0xE007F }, new String[]{"flag-england"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F3F4, 0xE0067, 0xE0062, 0xE0073, 0xE0063, 0xE0074, 0xE007F }, new String[]{"flag-scotland"}, false),
      new GoogleCompatEmoji(new int[] { 0x1F3F4, 0xE0067, 0xE0062, 0xE0077, 0xE006C, 0xE0073, 0xE007F }, new String[]{"flag-wales"}, false)
    };
  }

  private FlagsCategoryChunk1() {
    // No instances.
  }
}
