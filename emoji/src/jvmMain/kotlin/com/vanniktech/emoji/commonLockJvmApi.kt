package com.vanniktech.emoji

import java.util.concurrent.locks.ReentrantLock

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class Lock {
  private val mutex = ReentrantLock()

  actual fun lock() = mutex.lock()
  actual fun unlock() = mutex.unlock()
}
