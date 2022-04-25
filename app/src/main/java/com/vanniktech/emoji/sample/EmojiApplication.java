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

package com.vanniktech.emoji.sample;

import android.app.Application;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatDelegate;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY;

public class EmojiApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();

    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_AUTO_BATTERY);

    EmojiManager.install(new IosEmojiProvider());

    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());
  }
}
