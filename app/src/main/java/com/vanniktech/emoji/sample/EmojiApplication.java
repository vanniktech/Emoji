package com.vanniktech.emoji.sample;

import android.app.Application;
import android.os.StrictMode;
import com.squareup.leakcanary.LeakCanary;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

public class EmojiApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();

    EmojiManager.install(new IosEmojiProvider());

    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());
    }

    LeakCanary.install(this);
  }
}
