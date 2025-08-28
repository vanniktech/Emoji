@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.vanniktech.emoji

import platform.Foundation.NSLock

internal actual class Lock {
  private val lock = NSLock()

  actual fun lock() = lock.lock()
  actual fun unlock() = lock.unlock()
}
