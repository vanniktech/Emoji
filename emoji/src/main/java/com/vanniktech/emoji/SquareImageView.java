package com.vanniktech.emoji;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY) public final class SquareImageView extends AppCompatImageView {
  public SquareImageView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    final int measuredWidth = getMeasuredWidth();
    //noinspection SuspiciousNameCombination
    setMeasuredDimension(measuredWidth, measuredWidth);
  }
}
