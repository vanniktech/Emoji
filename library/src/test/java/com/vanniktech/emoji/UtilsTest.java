package com.vanniktech.emoji;


import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;

import org.junit.Test;

public class UtilsTest {
    @Test
    public void constructorShouldBePrivate() {
        PrivateConstructorChecker.forClass(Utils.class).expectedTypeOfException(AssertionError.class).expectedExceptionMessage("No instances.").check();
    }
}
