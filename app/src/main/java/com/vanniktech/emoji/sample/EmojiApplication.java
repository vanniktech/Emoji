package com.vanniktech.emoji.sample;

import android.app.Application;
import android.os.StrictMode;
import android.support.v7.app.AppCompatDelegate;
import com.squareup.leakcanary.LeakCanary;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_AUTO;

public class EmojiApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();

    if (LeakCanary.isInAnalyzerProcess(this)) {
      return;
    }

    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_AUTO);

    EmojiManager.install(new IosEmojiProvider());

    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());
    }

    LeakCanary.install(this);
  }
}
