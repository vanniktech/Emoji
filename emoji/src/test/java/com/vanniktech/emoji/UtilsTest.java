package com.vanniktech.emoji;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;
import com.vanniktech.emoji.emoji.Emoji;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {
  @Rule public final ExpectedException expectedException = ExpectedException.none();

  @Test public void constructorShouldBePrivate() {
    PrivateConstructorChecker.forClass(Utils.class)
        .expectedTypeOfException(AssertionError.class)
        .expectedExceptionMessage("No instances.")
        .check();
  }

  @Test public void checkNull() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("param is null");

    Utils.checkNotNull(null, "param is null");
  }

  @Test public void checkNotNull() {
    Utils.checkNotNull("valid", "null is null");
  }

  @Test public void asListFilter() {
    final Emoji[] emojis = new Emoji[] {
      new Emoji("\u1234".codePointAt(0), R.drawable.emoji_backspace, false),
      new Emoji("\u1234".codePointAt(0), R.drawable.emoji_backspace, true),
    };

    final List<Emoji> filtered = Utils.asListWithoutDuplicates(emojis);

    assertThat(filtered).containsExactly(emojis[0]);
  }
}
