package com.vanniktech.emoji.sample.screenshots;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.view.View;
import android.widget.EditText;
import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.core.AllOf.allOf;

/** @deprecated replace with https://github.com/vanniktech/espresso-utils once it's released. */
@Deprecated final class AppendTextAction implements ViewAction {
  static ViewAction append(final String text) {
    return ViewActions.actionWithAssertions(new AppendTextAction(text));
  }

  private final String text;

  private AppendTextAction(final String text) {
    this.text = text;
  }

  @SuppressWarnings("unchecked") @Override public Matcher<View> getConstraints() {
    return allOf(isDisplayed(), isAssignableFrom(EditText.class));
  }

  @Override public void perform(final UiController uiController, final View view) {
    ((EditText) view).append(text);
  }

  @Override public String getDescription() {
    return "append text";
  }
}
