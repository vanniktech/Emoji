package com.vanniktech.emoji;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UtilsTest {
    @Rule public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void constructorShouldBePrivate() {
        PrivateConstructorChecker.forClass(Utils.class).expectedTypeOfException(AssertionError.class).expectedExceptionMessage("No instances.").check();
    }

    @Test
    public void checkNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("null is null");

        Utils.checkNotNull(null, "null is null");
    }

    @Test
    public void checkNotNull() {
        Utils.checkNotNull("valid", "null is null");
    }
}
