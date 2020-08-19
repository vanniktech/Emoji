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
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.DimenRes;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.appcompat.widget.AppCompatEditText;
import com.vanniktech.emoji.emoji.Emoji;

/** Reference implementation for an EditText with emoji support. */
public class EmojiEditText extends AppCompatEditText implements EmojiEditable {
  private float emojiSize;
  private boolean disableKeyboardInput;

  public EmojiEditText(final Context context) {
    this(context, null);
  }

  public EmojiEditText(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    emojiSize = Utils.initTextView(this, attrs);
  }

  public EmojiEditText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    emojiSize = Utils.initTextView(this, attrs);
  }

  @Override @CallSuper protected void onTextChanged(final CharSequence text, final int start, final int lengthBefore, final int lengthAfter) {
    if (isInEditMode()) {
      return;
    }

    final Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
    final float defaultEmojiSize = fontMetrics.descent - fontMetrics.ascent;
    EmojiManager.getInstance().replaceWithImages(getContext(), getText(), emojiSize != 0 ? emojiSize : defaultEmojiSize);
  }

  @Override @CallSuper public void backspace() {
    Utils.backspace(this);
  }

  @Override @CallSuper public void input(final Emoji emoji) {
    Utils.input(this, emoji);
  }

  @Override public final float getEmojiSize() {
    return emojiSize;
  }

  @Override public final void setEmojiSize(@Px final int pixels) {
    setEmojiSize(pixels, true);
  }

  @Override public final void setEmojiSize(@Px final int pixels, final boolean shouldInvalidate) {
    emojiSize = pixels;

    if (shouldInvalidate) {
      setText(getText());
    }
  }

  @Override public final void setEmojiSizeRes(@DimenRes final int res) {
    setEmojiSizeRes(res, true);
  }

  @Override public final void setEmojiSizeRes(@DimenRes final int res, final boolean shouldInvalidate) {
    setEmojiSize(getResources().getDimensionPixelSize(res), shouldInvalidate);
  }

  @Override public void setOnFocusChangeListener(final OnFocusChangeListener l) {
    final OnFocusChangeListener onFocusChangeListener = getOnFocusChangeListener();

    if (onFocusChangeListener instanceof ForceEmojisOnlyFocusChangeListener) {
      final ForceEmojisOnlyFocusChangeListener cast = (ForceEmojisOnlyFocusChangeListener) onFocusChangeListener;
      super.setOnFocusChangeListener(new ForceEmojisOnlyFocusChangeListener(l, cast.emojiPopup));
    } else {
      super.setOnFocusChangeListener(l);
    }
  }

  public boolean isKeyboardInputDisabled() {
    return disableKeyboardInput;
  }

  /** Disables the keyboard input using a focus change listener and delegating to the previous focus change listener. */
  public void disableKeyboardInput(final EmojiPopup emojiPopup) {
    disableKeyboardInput = true;
    super.setOnFocusChangeListener(new ForceEmojisOnlyFocusChangeListener(getOnFocusChangeListener(), emojiPopup));
  }

  /** Enables the keyboard input. If it has been disabled before using {@link #disableKeyboardInput(EmojiPopup)} the OnFocusChangeListener will be preserved. */
  public void enableKeyboardInput() {
    disableKeyboardInput = false;
    final OnFocusChangeListener onFocusChangeListener = getOnFocusChangeListener();

    if (onFocusChangeListener instanceof ForceEmojisOnlyFocusChangeListener) {
      final ForceEmojisOnlyFocusChangeListener cast = (ForceEmojisOnlyFocusChangeListener) onFocusChangeListener;
      super.setOnFocusChangeListener(cast.onFocusChangeListener);
    }
  }

  /** Forces this EditText to contain only one Emoji. */
  public void forceSingleEmoji() {
    SingleEmojiTrait.install(this);
  }

  static class ForceEmojisOnlyFocusChangeListener implements OnFocusChangeListener {
    final EmojiPopup emojiPopup;
    @Nullable final OnFocusChangeListener onFocusChangeListener;

    ForceEmojisOnlyFocusChangeListener(@Nullable final OnFocusChangeListener onFocusChangeListener, final EmojiPopup emojiPopup) {
      this.emojiPopup = emojiPopup;
      this.onFocusChangeListener = onFocusChangeListener;
    }

    @Override public void onFocusChange(final View view, final boolean hasFocus) {
      if (hasFocus) {
        emojiPopup.start();
        emojiPopup.show();
      } else {
        emojiPopup.dismiss();
      }

      if (onFocusChangeListener != null) {
        onFocusChangeListener.onFocusChange(view, hasFocus);
      }
    }
  }
}
