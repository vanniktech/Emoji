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
 */

package com.vanniktech.emoji.sample.screenshots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vanniktech.emoji.sample.MainActivity
import com.vanniktech.emoji.sample.R
import com.vanniktech.espresso.core.utils.AppendTextAction.appendText
import com.vanniktech.junit4androidintegrationrules.DemoModeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.locale.LocaleTestRule

@RunWith(AndroidJUnit4::class) class ScreenshotsTest {
  @Rule @JvmField val demoModeRule = DemoModeRule()

  @get:Rule val activityRule = ActivityScenarioRule(MainActivity::class.java)

  @Rule @JvmField val localeTestRule = LocaleTestRule()

  @Test fun takeScreenShotsIos() {
    start(Variant.IOS)
  }

  @Test fun takeScreenShotsEmojiGoogle() {
    start(Variant.GOOGLE)
  }

  @Test fun takeScreenShotsEmojiTwitter() {
    start(Variant.TWITTER)
  }

  @Test fun takeScreenShotsEmojiFacebook() {
    start(Variant.FACEBOOK)
  }

  internal enum class Variant(
    val title: String,
  ) {
    GOOGLE("Google"),
    IOS("iOS"),
    TWITTER("Twitter"),
    FACEBOOK("Facebook"),
    ;
  }

  private fun start(variant: Variant) {
    val name = variant.name.lowercase()

    // Select the right variant.
    onView(withId(R.id.button)).perform(click())
    onView(withText(variant.title)).perform(click())

    // First text.
    onView(withId(R.id.chatEmoji)).perform(click())
    val firstEmojis = intArrayOf(0x1f913, 0x1F60E, 0x1F921, 0x1F920, 0x1F60F, 0x1F3BE)
    onView(withId(R.id.chatEditText)).perform(appendText("Hello what's up? " + String(firstEmojis, 0, firstEmojis.size)))
    Thread.sleep(500) // Espresso does not synchronize it right away.
    Screengrab.screenshot(name + "_1")
    onView(withId(R.id.chatSend)).perform(click())

    // Second text.
    val beerEmojis = intArrayOf(0x1F37A, 0x1F37A, 0x1F37A)
    onView(withId(R.id.chatEditText)).perform(appendText(String(beerEmojis, 0, beerEmojis.size)))
    onView(withId(R.id.chatSend)).perform(click())
    val clinkingBeerEmoji = intArrayOf(0x1F37B)
    onView(withId(R.id.chatEditText)).perform(appendText(String(clinkingBeerEmoji, 0, clinkingBeerEmoji.size)))
    onView(withId(R.id.chatSend)).perform(click())
    Thread.sleep(500) // Espresso does not synchronize it right away.
    Screengrab.screenshot(name + "_2")

    // Third text.
    onView(withId(R.id.chatSend)).perform(click())
    val secondEmojis = intArrayOf(0x1F98B, 0x1F41E, 0x1F41D, 0x1F422, 0x1F432, 0x1F683, 0x1F37B, 0x1F943)
    onView(withId(R.id.chatEditText)).perform(appendText("I don't know " + String(secondEmojis, 0, secondEmojis.size)))
    Thread.sleep(500) // Espresso does not synchronize it right away.
    Screengrab.screenshot(name + "_3")
  }
}
