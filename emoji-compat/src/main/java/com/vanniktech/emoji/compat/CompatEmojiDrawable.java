package com.vanniktech.emoji.compat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.EmojiSpan;
import android.text.Spanned;
import android.text.TextPaint;
import com.vanniktech.emoji.emoji.Emoji;

public class CompatEmojiDrawable extends Drawable {

    EmojiSpan emojiSpan;
    CharSequence processed;
    TextPaint textPaint = new TextPaint();

    public CompatEmojiDrawable(Context context, String unicode) {
        processed = EmojiCompat.get().process(unicode);
        System.out.println("Unicode: " + unicode + " spanned? " + (processed instanceof Spanned) + " cls: " + processed.getClass());
        if (processed instanceof Spanned) {
            Object[] spans = ((Spanned) processed).getSpans(0, unicode.length(), EmojiSpan.class);
            System.out.println("Spans len: " + spans.length);
            if (spans.length > 0) {
                emojiSpan = (EmojiSpan) spans[0];
            }
        }
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(0x0ffffffff);
    }

    @Override
    public void draw(Canvas canvas) {
        if (emojiSpan == null) {
            canvas.drawRect(0, 0, 100, 100, textPaint);
        } else {
            emojiSpan.draw(canvas, processed, 0, processed.length(), 0, 100, 0, 10, textPaint);
        }
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
