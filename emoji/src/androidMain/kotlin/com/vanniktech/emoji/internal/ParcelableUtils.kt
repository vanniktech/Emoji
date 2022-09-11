package com.vanniktech.emoji.internal

import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
  // Does not work yet, https://issuetracker.google.com/issues/240585930
  // SDK_INT >= 33 -> getParcelable(key, T::class.java)
  else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}
