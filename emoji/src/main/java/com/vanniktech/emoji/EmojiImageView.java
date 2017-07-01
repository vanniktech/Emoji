package com.vanniktech.emoji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiClickListener;
import com.vanniktech.emoji.listeners.OnEmojiLongClickListener;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY) public final class EmojiImageView extends AppCompatImageView {
  private static final int VARIANT_INDICATOR_PART_AMOUNT = 6;
  private static final int VARIANT_INDICATOR_PART = 5;

  Emoji currentEmoji;

  OnEmojiClickListener clickListener;
  OnEmojiLongClickListener longClickListener;

  private final Paint variantIndicatorPaint = new Paint();
  private final Path variantIndicatorPath = new Path();

  private final Point variantIndicatorTop = new Point();
  private final Point variantIndicatorBottomRight = new Point();
  private final Point variantIndicatorBottomLeft = new Point();

  private ImageLoadingTask imageLoadingTask;

  private boolean hasVariants;

  public EmojiImageView(final Context context, final AttributeSet attrs) {
    super(context, attrs);

    variantIndicatorPaint.setColor(ContextCompat.getColor(context, R.color.emoji_divider));
    variantIndicatorPaint.setStyle(Paint.Style.FILL);
    variantIndicatorPaint.setAntiAlias(true);
  }

  @Override public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    final int measuredWidth = getMeasuredWidth();
    //noinspection SuspiciousNameCombination
    setMeasuredDimension(measuredWidth, measuredWidth);
  }

  @Override protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    variantIndicatorTop.x = w;
    variantIndicatorTop.y = h / VARIANT_INDICATOR_PART_AMOUNT * VARIANT_INDICATOR_PART;
    variantIndicatorBottomRight.x = w;
    variantIndicatorBottomRight.y = h;
    variantIndicatorBottomLeft.x = w / VARIANT_INDICATOR_PART_AMOUNT * VARIANT_INDICATOR_PART;
    variantIndicatorBottomLeft.y = h;

    variantIndicatorPath.rewind();
    variantIndicatorPath.moveTo(variantIndicatorTop.x, variantIndicatorTop.y);
    variantIndicatorPath.lineTo(variantIndicatorBottomRight.x, variantIndicatorBottomRight.y);
    variantIndicatorPath.lineTo(variantIndicatorBottomLeft.x, variantIndicatorBottomLeft.y);
    variantIndicatorPath.close();
  }

  @Override protected void onDraw(final Canvas canvas) {
    super.onDraw(canvas);

    if (hasVariants) {
      canvas.drawPath(variantIndicatorPath, variantIndicatorPaint);
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();

    if (imageLoadingTask != null) {
      imageLoadingTask.cancel(true);
      imageLoadingTask = null;
    }
  }

  public void setEmoji(@NonNull final Emoji emoji) {
    if (!emoji.equals(currentEmoji)) {
      setImageDrawable(null);

      currentEmoji = emoji;
      hasVariants = emoji.getBase().hasVariants();

      if (imageLoadingTask != null) {
        imageLoadingTask.cancel(true);
      }

      setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(final View view) {
          if (clickListener != null) {
            clickListener.onEmojiClick(EmojiImageView.this, currentEmoji);
          }
        }
      });

      setOnLongClickListener(hasVariants ? new OnLongClickListener() {
        @Override
        public boolean onLongClick(final View view) {
          longClickListener.onEmojiLongClick(EmojiImageView.this, currentEmoji);

          return true;
        }
      } : null);

      imageLoadingTask = new ImageLoadingTask(this);
      imageLoadingTask.execute(emoji.getResource());
    }
  }

  public void setOnEmojiClickListener(@Nullable final OnEmojiClickListener listener) {
    this.clickListener = listener;
  }

  public void setOnEmojiLongClickListener(@Nullable final OnEmojiLongClickListener listener) {
    this.longClickListener = listener;
  }
}
