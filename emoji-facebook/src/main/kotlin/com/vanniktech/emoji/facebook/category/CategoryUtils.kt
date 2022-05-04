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

package com.vanniktech.emoji.facebook.category

import com.vanniktech.emoji.facebook.FacebookEmoji
import java.util.Arrays

internal object CategoryUtils {
  internal fun concatAll(first: Array<FacebookEmoji>, vararg rest: Array<FacebookEmoji>): Array<FacebookEmoji> {
    var totalLength = first.size
    for (array in rest) {
      totalLength += array.size
    }
    @Suppress("ReplaceJavaStaticMethodWithKotlinAnalog")
    val result = Arrays.copyOf(first, totalLength)
    var offset = first.size
    for (array in rest) {
      System.arraycopy(array, 0, result, offset, array.size)
      offset += array.size
    }
    return result
  }
}
