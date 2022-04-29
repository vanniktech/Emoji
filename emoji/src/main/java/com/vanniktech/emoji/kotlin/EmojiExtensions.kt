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

package com.vanniktech.emoji.kotlin

import com.vanniktech.emoji.isOnlyEmojis
import com.vanniktech.emoji.emojisCount
import com.vanniktech.emoji.emojis
import com.vanniktech.emoji.emojiInformation

@Deprecated(message = "Moved to another package", replaceWith = ReplaceWith("this.isOnlyEmojis()", imports = arrayOf("com.vanniktech.emoji.isOnlyEmojis")))
fun CharSequence.isOnlyEmojis() = isOnlyEmojis()

@Deprecated(message = "Moved to another package", replaceWith = ReplaceWith("this.emojis()", imports = arrayOf("com.vanniktech.emoji.emojis")))
fun CharSequence.emojis() = emojis()

@Deprecated(message = "Moved to another package", replaceWith = ReplaceWith("this.emojisCount()", imports = arrayOf("com.vanniktech.emoji.emojisCount")))
fun CharSequence.emojisCount() = emojisCount()

@Deprecated(message = "Moved to another package", replaceWith = ReplaceWith("this.emojiInformation()", imports = arrayOf("com.vanniktech.emoji.emojiInformation")))
fun CharSequence.emojiInformation() = emojiInformation()
