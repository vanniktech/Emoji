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

package com.vanniktech.emoji.material;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.Nullable;
import com.vanniktech.emoji.EmojiLayoutFactory;

/** Layout Factory that substitutes certain Views to add automatic Emoji support. */
public class MaterialEmojiLayoutFactory extends EmojiLayoutFactory {
  public MaterialEmojiLayoutFactory() {
    super();
  }

  public MaterialEmojiLayoutFactory(@Nullable final LayoutInflater.Factory2 delegate) {
    super(delegate);
  }

  @Override public View onCreateView(@Nullable final View parent, final String name, final Context context, final AttributeSet attrs) {
    if ("Button".equals(name)) {
      return new EmojiMaterialButton(context, attrs);
    } else if ("CheckBox".equals(name)) {
      return new EmojiMaterialCheckBox(context, attrs);
    } else if ("RadioButton".equals(name)) {
      return new EmojiMaterialRadioButton(context, attrs);
    } else if ("com.google.android.material.textfield.TextInputEditText".equals(name)) {
      return new EmojiTextInputEditText(context, attrs);
    } else {
      return super.onCreateView(parent, name, context, attrs);
    }
  }
}
