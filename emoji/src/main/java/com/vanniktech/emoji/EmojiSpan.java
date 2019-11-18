package com.vanniktech.emoji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.util.Log;

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
      if(emoji.isAnimated()) {
        Log.d("TAG", "is animated");
        deferredDrawable = emoji.getAnimatedDrawable(context);
      } else {
        Log.d("TAG", "is not animated");
        deferredDrawable = emoji.getDrawable(context);
      }
      deferredDrawable.setBounds(0, 0, (int) size, (int) size);
    }

    return deferredDrawable;
  }

  @Override public int getSize(final Paint paint, final CharSequence text, final int start,
                               final int end, final Paint.FontMetricsInt fontMetrics) {
    if (fontMetrics != null) {
      final Paint.FontMetrics paintFontMetrics = paint.getFontMetrics();
      final float fontHeight = paintFontMetrics.descent - paintFontMetrics.ascent;
      final float centerY = paintFontMetrics.ascent + fontHeight / 2;

      fontMetrics.ascent = (int) (centerY - size / 2);
      fontMetrics.top = fontMetrics.ascent;
      fontMetrics.bottom = (int) (centerY + size / 2);
      fontMetrics.descent = fontMetrics.bottom;
    }

    return (int) size;
  }

  @Override public void draw(final Canvas canvas, final CharSequence text, final int start,
                             final int end, final float x, final int top, final int y,
                             final int bottom, final Paint paint) {

    System.out.println("Starting to draw pic");

    final Drawable drawable = getDrawable();
    if (drawable instanceof AnimationDrawable) {
      drawAnimation(canvas, drawable, text, start, end, x, top, y, bottom, paint);
    } else {
      drawImage(canvas, drawable, text, start, end, x, top, y, bottom, paint);
    }

    System.out.println("Pic drawn show pic");
  }

  private void drawImage(final Canvas canvas, Drawable drawable, final CharSequence text, final int start,
                         final int end, final float x, final int top, final int y,
                         final int bottom, final Paint paint) {

    final Paint.FontMetrics paintFontMetrics = paint.getFontMetrics();
    final float fontHeight = paintFontMetrics.descent - paintFontMetrics.ascent;
    final float centerY = y + paintFontMetrics.descent - fontHeight / 2;
    final float transitionY = centerY - size / 2;

    canvas.save();
    canvas.translate(x, transitionY);
    drawable.draw(canvas);
    canvas.restore();
  }

  private void drawAnimation(final Canvas canvas, Drawable drawable, final CharSequence text, final int start,
                             final int end, final float x, final int top, final int y,
                             final int bottom, final Paint paint) {


    // TODO: Create working AnimationDrawable implementation

    AnimationDrawable animat = (AnimationDrawable) drawable;
    Drawable drawableAnim = animat.getFrame(0);  // TODO: This does work
    Drawable drawableAnim2 = animat.getFrame(0);  // TODO: This does not work, getFrame returns empty even though Frames saved is 52
    drawImage(canvas, drawableAnim, text, start, end, x, top, y, bottom, paint);


    // drawImage(canvas, drawable, text, start, end, x, top, y, bottom, paint);

    /*
    if(drawable instanceof AnimationDrawable) {
      AnimationDrawable animation = (AnimationDrawable) drawable;
      animation.getFrame(5).draw(canvas);
      // animation.start();
      System.out.println("Frames: " + animation.getNumberOfFrames());
      System.out.println("Frames: " + animation.isOneShot());
      System.out.println("Frames: " + animation.isRunning());

      // AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
      // for(int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
        //  drawImage(canvas, animationDrawable.getFrame(i), text, start, end, x, top, y, bottom, paint);

      // }

     */


    Log.d("TAG", "Animation in EmojiSpan");
  }
}
