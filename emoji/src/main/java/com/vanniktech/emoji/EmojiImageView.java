package com.vanniktech.emoji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import com.vanniktech.emoji.emoji.Emoji;
import com.vanniktech.emoji.listeners.OnEmojiClickedListener;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY) public final class EmojiImageView extends AppCompatImageView {
  private static final int VARIANT_INDICATOR_PART_AMOUNT = 6;
  private static final int VARIANT_INDICATOR_PART = 5;

  private final Paint variantIndicatorPaint = new Paint();
  private final Path variantIndicatorPath = new Path();

  private final Point variantIndicatorTop = new Point();
  private final Point variantIndicatorBottomRight = new Point();
  private final Point variantIndicatorBottomLeft = new Point();

  private boolean hasVariants;

  private RecentEmojiVariantManager.Disposable disposable;

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

  public void setEmoji(final Emoji emoji, @Nullable final OnEmojiClickedListener clickListener, @Nullable final OnEmojiLongClickedListener longClickListener) {
    setImageDrawable(null);

    setOnClickListener(new EmojiOnClickListener(clickListener, emoji));

    final Emoji baseEmoji = emoji.getBase();

    hasVariants = baseEmoji.hasVariants();
    setOnLongClickListener(hasVariants ? new EmojiOnLongClickListener(longClickListener, emoji) : null);

    ImageLoadingTask task = (ImageLoadingTask) getTag();

    if (task != null) {
      task.cancel(true);
    }

    task = new ImageLoadingTask(this);
    setTag(task);
    task.execute(emoji.getResource());

    disposable = RecentEmojiVariantManager.getInstance()
        .addListener(baseEmoji, new RecentEmojiVariantManager.EmojiVariantListener() {
          @Override public void onChanged(final Emoji newEmoji) {
            setImageResource(newEmoji.getResource()); // We don't need to load it in an async manner since it's only one Image and we know that for a fact.

            setOnClickListener(new EmojiOnClickListener(clickListener, emoji));
          }
        });
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();

    if (disposable != null) {
      disposable.dispose();
      disposable = null; // Force GC.
    }
  }

  static class EmojiOnClickListener implements OnClickListener {
    @Nullable private final OnEmojiClickedListener clickListener;
    private final Emoji emoji;

    EmojiOnClickListener(@Nullable final OnEmojiClickedListener clickListener, final Emoji emoji) {
      this.clickListener = clickListener;
      this.emoji = emoji;
    }

    @Override public void onClick(final View v) {
      if (clickListener != null) {
        clickListener.onEmojiClicked(emoji);
      }
    }
  }

  static class EmojiOnLongClickListener implements OnLongClickListener {
    @Nullable private final OnEmojiLongClickedListener longClickListener;
    private final Emoji emoji;

    EmojiOnLongClickListener(final OnEmojiLongClickedListener longClickListener, final Emoji emoji) {
      this.longClickListener = longClickListener;
      this.emoji = emoji;
    }

    @Override public boolean onLongClick(final View v) {
      if (longClickListener != null) {
        longClickListener.onEmojiLongClicked(v, emoji);

        return true;
      }

      return false;
    }
  }
}
