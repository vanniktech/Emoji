/*
 * Copyright (C) 2016 - Niklas Baudy, Ruben Gees, Mario Đanić and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import com.vanniktech.emoji.emoji.Emoji;

final class EmojiSpan extends DynamicDrawableSpan {
  private final float size;
  private final Context context;
  private final Emoji emoji;
  private Drawable deferredDrawable;

  EmojiSpan(final Context context, final Emoji emoji, final float size) {
    this.context = context;
    this.emoji = emoji;
    this.size = size;
  }

  @Override public Drawable getDrawable() {
    if (deferredDrawable == null) {
      deferredDrawable = emoji.getDrawable(context);
      deferredDrawable.setBounds(0, 0, (int) size, (int) size);
    }
    return deferredDrawable;
  }

  @Override public int getSize(final Paint paint, final CharSequence text, final int start,
      final int end, final Paint.FontMetricsInt fontMetrics) {
    if (fontMetrics != null) {
      final Paint.FontMetrics paintFontMetrics = paint.getFontMetrics();
      final int ascent = (int) paintFontMetrics.ascent;
      final int descent = (int) paintFontMetrics.descent;
      final int roundEmojiSize = Math.round(size);

      // If equal in size use, default font metrics.
      if (roundEmojiSize == (Math.abs(ascent) + Math.abs(descent))) {
        fontMetrics.ascent = ascent;
        fontMetrics.descent = descent;
        fontMetrics.top = (int) paintFontMetrics.top;
        fontMetrics.bottom = (int) paintFontMetrics.bottom;
      } else {
        final float fontHeight = paintFontMetrics.descent - paintFontMetrics.ascent;
        final float centerY = paintFontMetrics.ascent + fontHeight / 2;

        fontMetrics.ascent = (int) (centerY - size / 2);
        fontMetrics.top = fontMetrics.ascent;
        fontMetrics.bottom = (int) (centerY + size / 2);
        fontMetrics.descent = fontMetrics.bottom;
      }
    }

    return (int) size;
  }

  @Override public void draw(final Canvas canvas, final CharSequence text, final int start,
      final int end, final float x, final int top, final int y,
      final int bottom, final Paint paint) {
    final Drawable drawable = getDrawable();
    final Paint.FontMetrics paintFontMetrics = paint.getFontMetrics();
    final float fontHeight = paintFontMetrics.descent - paintFontMetrics.ascent;
    final float centerY = y + paintFontMetrics.descent - fontHeight / 2;
    final float transitionY = centerY - size / 2;

    canvas.save();
    canvas.translate(x, transitionY);
    drawable.draw(canvas);
    canvas.restore();
  }
}
