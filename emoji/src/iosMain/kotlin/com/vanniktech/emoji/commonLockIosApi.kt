package com.vanniktech.emoji

import platform.Foundation.NSLock

internal actual class Lock {
  private val lock = NSLock()

  actual fun lock() = lock.lock()
  actual fun unlock() = lock.unlock()
}
