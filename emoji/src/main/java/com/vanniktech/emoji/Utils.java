package com.vanniktech.emoji;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

final class Utils {
  @TargetApi(JELLY_BEAN) static void removeOnGlobalLayoutListener(final View v, final ViewTreeObserver.OnGlobalLayoutListener listener) {
    if (SDK_INT < JELLY_BEAN) {
      //noinspection deprecation
      v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
    } else {
      v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }
  }

  @NonNull static <T> T checkNotNull(@Nullable final T reference, final String message) {
    if (reference == null) {
      throw new IllegalArgumentException(message);
    }

    return reference;
  }

  private Utils() {
    throw new AssertionError("No instances.");
  }
}
