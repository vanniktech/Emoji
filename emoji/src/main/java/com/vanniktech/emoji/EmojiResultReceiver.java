package com.vanniktech.emoji;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import androidx.annotation.Nullable;

public final class EmojiResultReceiver extends ResultReceiver {
  @Nullable private Receiver receiver;

  /**
   * Create a new EmojiResultReceiver to receive results.  Your
   * {@link #onReceiveResult} method will be called from the thread running
   * <var>handler</var> if given, or from an arbitrary thread if null.
   */
  EmojiResultReceiver(final Handler handler) {
    super(handler);
  }

  void setReceiver(final Receiver receiver) {
    this.receiver = receiver;
  }

  @Override protected void onReceiveResult(final int resultCode, final Bundle resultData) {
    if (receiver != null) {
      receiver.onReceiveResult(resultCode, resultData);
    }
  }

  public interface Receiver {
    void onReceiveResult(int resultCode, Bundle data);
  }
}
