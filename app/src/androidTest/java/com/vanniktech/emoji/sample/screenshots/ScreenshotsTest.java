package com.vanniktech.emoji.sample.screenshots;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.vanniktech.emoji.sample.MainActivity;
import com.vanniktech.emoji.sample.R;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import tools.fastlane.screengrab.Screengrab;
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy;
import tools.fastlane.screengrab.locale.LocaleTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.vanniktech.emoji.sample.screenshots.AppendTextAction.append;
import static java.util.Locale.US;

@RunWith(AndroidJUnit4.class) public final class ScreenshotsTest {
  @ClassRule public static final LocaleTestRule LOCALE_TEST_RULE = new LocaleTestRule();

  @Rule public final ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

  @Before public void setUp() {
    Screengrab.setDefaultScreenshotStrategy(new UiAutomatorScreenshotStrategy());
  }

  @Test public void takeScreenShotsIos() throws InterruptedException {
    start(Variant.IOS);
  }

  @Test public void takeScreenShotsEmojiOne() throws InterruptedException {
    start(Variant.EMOJI_ONE);
  }

  @Test public void takeScreenShotsEmojiGoogle() throws InterruptedException {
    start(Variant.GOOGLE);
  }

  @Test public void takeScreenShotsEmojiTwitter() throws InterruptedException {
    start(Variant.TWITTER);
  }

  private void start(final Variant variant) throws InterruptedException {
    final String name = variant.name().toLowerCase(US);

    // Select the right variant.
    onView(withId(R.id.menu_search_option)).perform(click());
    onView(withText(variant.title)).perform(click());

    // First text.
    onView(withId(R.id.main_activity_emoji)).perform(click());

    final int[] firstEmojis = new int[] { 0x1f913, 0x1F60E, 0x1F921, 0x1F920, 0x1F60F, 0x1F3BE };
    onView(withId(R.id.main_activity_chat_bottom_message_edittext)).perform(append("Hello what's up? " + new String(firstEmojis, 0, firstEmojis.length)));

    Thread.sleep(500); // Espresso does not synchronize it right away.
    Screengrab.screenshot(name + "_1");

    onView(withId(R.id.main_activity_send)).perform(click());

    // Second text.
    final int[] beerEmojis = new int[] { 0x1F37A, 0x1F37A, 0x1F37A };
    onView(withId(R.id.main_activity_chat_bottom_message_edittext)).perform(append(new String(beerEmojis, 0, beerEmojis.length)));
    onView(withId(R.id.main_activity_send)).perform(click());

    final int[] clinkingBeerEmoji = new int[] { 0x1F37B };
    onView(withId(R.id.main_activity_chat_bottom_message_edittext)).perform(append(new String(clinkingBeerEmoji, 0, clinkingBeerEmoji.length)));
    onView(withId(R.id.main_activity_send)).perform(click());

    Thread.sleep(500); // Espresso does not synchronize it right away.
    Screengrab.screenshot(name + "_2");

    // Third text.
    onView(withId(R.id.main_activity_send)).perform(click());

    final int[] secondEmojis = new int[] { 0x1F98B, 0x1F41E, 0x1F41D, 0x1F422, 0x1F432, 0x1F683, 0x1F37B, 0x1F943 };
    onView(withId(R.id.main_activity_chat_bottom_message_edittext)).perform(append("I don't know " + new String(secondEmojis, 0, secondEmojis.length)));

    Thread.sleep(500); // Espresso does not synchronize it right away.
    Screengrab.screenshot(name + "_3");
  }

  enum Variant {
    GOOGLE("Google"),
    IOS("Ios"),
    EMOJI_ONE("EmojiOne"),
    TWITTER("Twitter");

    final String title;

    Variant(final String title) {
      this.title = title;
    }
  }
}
