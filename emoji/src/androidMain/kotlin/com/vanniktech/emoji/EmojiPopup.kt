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

package com.vanniktech.emoji

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.WindowInsets
import android.view.autofill.AutofillManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupWindow
import androidx.annotation.StyleRes
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import com.vanniktech.emoji.EmojiManager.verifyInstalled
import com.vanniktech.emoji.internal.EmojiResultReceiver
import com.vanniktech.emoji.internal.Utils
import com.vanniktech.emoji.internal.inputMethodManager
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener
import com.vanniktech.emoji.listeners.OnEmojiClickListener
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener
import com.vanniktech.emoji.listeners.OnSoftKeyboardOpenListener
import com.vanniktech.emoji.recent.RecentEmoji
import com.vanniktech.emoji.recent.RecentEmojiManager
import com.vanniktech.emoji.search.SearchEmoji
import com.vanniktech.emoji.search.SearchEmojiManager
import com.vanniktech.emoji.variant.VariantEmoji
import com.vanniktech.emoji.variant.VariantEmojiManager
import java.lang.ref.WeakReference

class EmojiPopup @JvmOverloads constructor(
  /** The root View of your layout.xml which will be used for calculating the height of the keyboard. */
  rootView: View,
  private val editText: EditText,
  internal val theming: EmojiTheming = EmojiTheming.from(rootView.context),
  /** Option to customize with your own implementation of recent emojis. To hide use [com.vanniktech.emoji.recent.NoRecentEmoji]. */
  recentEmoji: RecentEmoji = RecentEmojiManager(rootView.context),
  /** Option to customize with your own implementation of searching emojis. To hide use [com.vanniktech.emoji.search.NoSearchEmoji]. */
  internal val searchEmoji: SearchEmoji = SearchEmojiManager(),
  /** Option to customize with your own implementation of variant emojis. To hide use [com.vanniktech.emoji.variant.NoVariantEmoji]. */
  variantEmoji: VariantEmoji = VariantEmojiManager(rootView.context),
  pageTransformer: ViewPager.PageTransformer? = null,
  @StyleRes keyboardAnimationStyle: Int = 0,
  /**
   * If height is not 0 then this value will be used later on. If it is 0 then the keyboard height will
   * be dynamically calculated and set as [PopupWindow] height.
   */
  private var popupWindowHeight: Int = 0,
  private val onEmojiPopupShownListener: OnEmojiPopupShownListener? = null,
  private val onSoftKeyboardCloseListener: OnSoftKeyboardCloseListener? = null,
  private val onSoftKeyboardOpenListener: OnSoftKeyboardOpenListener? = null,
  onEmojiBackspaceClickListener: OnEmojiBackspaceClickListener? = null,
  onEmojiClickListener: OnEmojiClickListener? = null,
  private val onEmojiPopupDismissListener: OnEmojiPopupDismissListener? = null,
) {
  internal val rootView: View = rootView.rootView
  internal val context: Activity = Utils.asActivity(rootView.context)
  private val emojiView: EmojiView = EmojiView(context)
  private val popupWindow: PopupWindow = PopupWindow(context)
  private var isPendingOpen = false
  private var isKeyboardOpen = false
  private var globalKeyboardHeight = 0
  private var delay = 0
  private var originalImeOptions = -1
  private val emojiResultReceiver = EmojiResultReceiver(Handler(Looper.getMainLooper()))
  private val onDismissListener = PopupWindow.OnDismissListener {
    onEmojiPopupDismissListener?.onEmojiPopupDismiss()
  }

  init {
    verifyInstalled()
    emojiView.setUp(
      rootView = rootView,
      onEmojiClickListener = onEmojiClickListener,
      onEmojiBackspaceClickListener = onEmojiBackspaceClickListener,
      editText = editText,
      theming = theming,
      recentEmoji = recentEmoji,
      searchEmoji = searchEmoji,
      variantEmoji = variantEmoji,
      pageTransformer = pageTransformer,
    )
    popupWindow.contentView = emojiView
    popupWindow.inputMethodMode = PopupWindow.INPUT_METHOD_NOT_NEEDED
    popupWindow.setBackgroundDrawable(BitmapDrawable(context.resources, null as Bitmap?)) // To avoid borders and overdraw.
    popupWindow.setOnDismissListener(onDismissListener)
    if (keyboardAnimationStyle != 0) {
      popupWindow.animationStyle = keyboardAnimationStyle
    }

    // Root view might already be laid out in which case we need to manually call start()
    if (rootView.parent != null) {
      start()
    }

    rootView.addOnAttachStateChangeListener(EmojiPopUpOnAttachStateChangeListener(this))
  }

  internal fun start() {
    context.window.decorView.setOnApplyWindowInsetsListener(EmojiPopUpOnApplyWindowInsetsListener(this))
  }

  internal fun stop() {
    dismiss()
    context.window.decorView.setOnApplyWindowInsetsListener(null)
    popupWindow.setOnDismissListener(null)
  }

  internal fun updateKeyboardStateOpened(keyboardHeight: Int) {
    if (popupWindowHeight > 0 && popupWindow.height != popupWindowHeight) {
      popupWindow.height = popupWindowHeight
    } else if (popupWindowHeight == 0 && popupWindow.height != keyboardHeight) {
      popupWindow.height = keyboardHeight
    }
    delay = if (globalKeyboardHeight != keyboardHeight) {
      globalKeyboardHeight = keyboardHeight
      APPLY_WINDOW_INSETS_DURATION
    } else {
      0
    }

    val properWidth = Utils.getProperWidth(context)
    if (popupWindow.width != properWidth) {
      popupWindow.width = properWidth
    }

    if (!isKeyboardOpen) {
      isKeyboardOpen = true
      onSoftKeyboardOpenListener?.onKeyboardOpen(keyboardHeight)
    }

    if (isPendingOpen) {
      showAtBottom()
    }
  }

  internal fun updateKeyboardStateClosed() {
    isKeyboardOpen = false
    onSoftKeyboardCloseListener?.onKeyboardClose()

    if (isShowing) {
      dismiss()
    }
  }

  fun toggle() {
    if (!popupWindow.isShowing) {
      // this is needed because something might have cleared the insets listener
      start()
      ViewCompat.requestApplyInsets(context.window.decorView)
      show()
    } else {
      dismiss()
    }
  }

  fun show() {
    if (Utils.shouldOverrideRegularCondition(context, editText) && originalImeOptions == -1) {
      originalImeOptions = editText.imeOptions
    }
    editText.isFocusableInTouchMode = true
    editText.requestFocus()
    showAtBottomPending()
  }

  private fun showAtBottomPending() {
    isPendingOpen = true
    val inputMethodManager = context.inputMethodManager
    if (Utils.shouldOverrideRegularCondition(context, editText)) {
      editText.imeOptions = editText.imeOptions or EditorInfo.IME_FLAG_NO_EXTRACT_UI
      inputMethodManager.restartInput(editText)
    }

    emojiResultReceiver.setReceiver { resultCode, _ ->
      if (resultCode == 0 || resultCode == 1) {
        showAtBottom()
      }
    }
    inputMethodManager.showSoftInput(editText, InputMethodManager.RESULT_UNCHANGED_SHOWN, emojiResultReceiver)
  }

  val isShowing: Boolean
    get() = popupWindow.isShowing

  fun dismiss() {
    popupWindow.dismiss()
    emojiView.tearDown()
    emojiResultReceiver.setReceiver(null)
    if (originalImeOptions != -1) {
      editText.imeOptions = originalImeOptions
      val inputMethodManager = context.inputMethodManager
      inputMethodManager.restartInput(editText)

      if (VERSION.SDK_INT >= VERSION_CODES.O) {
        val autofillManager = context.getSystemService(AutofillManager::class.java)
        autofillManager?.cancel()
      }
    }
  }

  private fun showAtBottom() {
    isPendingOpen = false
    editText.postDelayed({
      popupWindow.showAtLocation(
        rootView, Gravity.NO_GRAVITY, 0,
        Utils.getProperHeight(context) + popupWindowHeight,
      )
    }, delay.toLong(),)
    onEmojiPopupShownListener?.onEmojiPopupShown()
  }

  internal class EmojiPopUpOnAttachStateChangeListener(emojiPopup: EmojiPopup) : View.OnAttachStateChangeListener {
    private val emojiPopup: WeakReference<EmojiPopup> = WeakReference(emojiPopup)

    override fun onViewAttachedToWindow(v: View) {
      val popup = emojiPopup.get()
      popup?.start()
    }

    override fun onViewDetachedFromWindow(v: View) {
      val popup = emojiPopup.get()
      popup?.stop()
      emojiPopup.clear()
      v.removeOnAttachStateChangeListener(this)
    }
  }

  internal class EmojiPopUpOnApplyWindowInsetsListener(emojiPopup: EmojiPopup) : View.OnApplyWindowInsetsListener {
    private val emojiPopup: WeakReference<EmojiPopup> = WeakReference(emojiPopup)
    private var previousOffset = 0

    override fun onApplyWindowInsets(v: View, insets: WindowInsets): WindowInsets {
      val popup = emojiPopup.get()

      if (popup != null) {
        val systemWindowInsetBottom = if (VERSION.SDK_INT >= VERSION_CODES.R) {
          insets.getInsets(WindowInsets.Type.ime()).bottom
        } else {
          @Suppress("DEPRECATION")
          insets.systemWindowInsetBottom
        }

        val stableInsetBottom = if (VERSION.SDK_INT >= VERSION_CODES.R) {
          insets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom
        } else {
          @Suppress("DEPRECATION")
          insets.stableInsetBottom
        }

        val offset = if (systemWindowInsetBottom < stableInsetBottom) {
          systemWindowInsetBottom
        } else {
          systemWindowInsetBottom - stableInsetBottom
        }

        if (offset != previousOffset || offset == 0) {
          previousOffset = offset
          if (offset > Utils.dpToPx(popup.context, MIN_KEYBOARD_HEIGHT.toFloat())) {
            popup.updateKeyboardStateOpened(offset)
          } else {
            popup.updateKeyboardStateClosed()
          }
        }

        return popup.context.window.decorView.onApplyWindowInsets(insets)
      }

      return insets
    }
  }

  private companion object {
    private const val MIN_KEYBOARD_HEIGHT = 50
    private const val APPLY_WINDOW_INSETS_DURATION = 250
  }
}
