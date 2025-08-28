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

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater.Factory2
import android.view.View

/** Layout Factory that substitutes certain Views to add automatic Emoji support.  */
open class EmojiLayoutFactory(
  private val delegate: Factory2? = null,
) : Factory2 {
  override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet) = when {
    name == "TextView" -> EmojiTextView(context, attrs)
    name == "EditText" -> EmojiEditText(context, attrs)
    name == "Button" -> EmojiButton(context, attrs)
    name == "Checkbox" -> EmojiCheckbox(context, attrs)
    name == "AutoCompleteTextView" -> EmojiAutoCompleteTextView(context, attrs)
    name == "MultiAutoCompleteTextView" -> EmojiMultiAutoCompleteTextView(context, attrs)
    delegate != null -> delegate.onCreateView(parent, name, context, attrs)
    else -> null
  }

  override fun onCreateView(name: String, context: Context, attrs: AttributeSet) = onCreateView(null, name, context, attrs)
}
