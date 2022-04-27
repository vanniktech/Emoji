/*
 * Copyright (C) 2016 - Niklas Baudy, Ruben Gees, Mario Đanić and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.vanniktech.emoji;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import androidx.annotation.Nullable;

final class EmojiResultReceiver extends ResultReceiver {
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

  interface Receiver {
    void onReceiveResult(int resultCode, Bundle data);
  }
}
