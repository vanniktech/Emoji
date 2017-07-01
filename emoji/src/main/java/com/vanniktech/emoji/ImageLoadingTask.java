package com.vanniktech.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

final class ImageLoadingTask extends AsyncTask<Integer, Void, Drawable> {
  private final WeakReference<ImageView> imageViewReference;
  private final WeakReference<Context> contextReference;

  ImageLoadingTask(final ImageView imageView) {
    imageViewReference = new WeakReference<>(imageView);
    contextReference = new WeakReference<>(imageView.getContext());
  }

  @Override protected Drawable doInBackground(final Integer... resource) {
    final Context context = contextReference.get();

    if (context != null && !isCancelled()) {
      return AppCompatResources.getDrawable(context, resource[0]);
    }

    return null;
  }

  @Override protected void onPostExecute(@Nullable final Drawable drawable) {
    if (!isCancelled() && drawable != null) {
      final ImageView imageView = imageViewReference.get();

      if (imageView != null) {
        imageView.setImageDrawable(drawable);
      }
    }
  }
}
