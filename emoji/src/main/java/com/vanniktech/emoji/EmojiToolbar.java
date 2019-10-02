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

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.MaterialToolbar;

public class EmojiToolbar extends MaterialToolbar {
  private EmojiTextView emojiTitleTextView;

  public EmojiToolbar(@NonNull Context context) {
    super(context);
    initEmojiTextView(context, null);
  }

  public EmojiToolbar(@NonNull Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    initEmojiTextView(context, attrs);
  }

  public EmojiToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initEmojiTextView(context, attrs);
  }

  private void initEmojiTextView(Context context, @Nullable AttributeSet attrs) {
    if (emojiTitleTextView == null) {
      emojiTitleTextView = new EmojiTextView(context, attrs);
      addView(emojiTitleTextView);
    }
  }

  @Override
  public void setTitle(CharSequence title) {
    super.setTitle(title);
    emojiTitleTextView.setText(title);
  }

  @Override
  public void setTitle(int titleRes) {
    super.setTitle(titleRes);
    emojiTitleTextView.setText(titleRes);
  }
}
