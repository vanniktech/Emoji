package com.vanniktech.emoji;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class EmojiResultReceiver extends ResultReceiver {
  public interface Receiver {
    public void onReceiveResult(int resultCode, Bundle data);
  }

  private Receiver receiver;

  /**
   * Create a new EmojiResultReceiver to receive results.  Your
   * {@link #onReceiveResult} method will be called from the thread running
   * <var>handler</var> if given, or from an arbitrary thread if null.
   */
  public EmojiResultReceiver(Handler handler) {
    super(handler);
  }

  public void setReceiver(Receiver receiver) {
    this.receiver = receiver;
  }

  @Override
  protected void onReceiveResult(int resultCode, Bundle resultData) {
    if (receiver != null) {
      receiver.onReceiveResult(resultCode, resultData);
    }
  }
}
