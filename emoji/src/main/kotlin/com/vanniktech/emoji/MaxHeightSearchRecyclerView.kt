package com.vanniktech.emoji

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

internal class MaxHeightSearchRecyclerView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
) : RecyclerView(context, attrs) {
  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(
      widthMeasureSpec,
      MeasureSpec.makeMeasureSpec(context.resources.getDimensionPixelSize(R.dimen.emoji_search_max_height), MeasureSpec.AT_MOST),
    )
  }
}
