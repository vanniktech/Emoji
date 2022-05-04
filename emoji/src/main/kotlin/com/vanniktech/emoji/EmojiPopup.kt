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
 *
 */
package com.vanniktech.emoji

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
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
import androidx.annotation.CheckResult
import androidx.annotation.StyleRes
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import com.vanniktech.emoji.EmojiManager.verifyInstalled
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener
import com.vanniktech.emoji.listeners.OnEmojiClickListener
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener
import com.vanniktech.emoji.listeners.OnSoftKeyboardOpenListener
import java.lang.ref.WeakReference
import kotlin.math.max

class EmojiPopup(
  rootView: View,
  private val editText: EditText,
  internal val theming: EmojiTheming = EmojiTheming(),
  recentEmoji: RecentEmoji = RecentEmojiManager(rootView.context),
  internal val searchEmoji: SearchEmoji = SearchEmojiManager(),
  variantEmoji: VariantEmoji = VariantEmojiManager(rootView.context),
  pageTransformer: ViewPager.PageTransformer? = null,
  @StyleRes keyboardAnimationStyle: Int = 0,
  private var popupWindowHeight: Int = 0,
  private val onEmojiPopupShownListener: OnEmojiPopupShownListener? = null,
  private val onSoftKeyboardCloseListener: OnSoftKeyboardCloseListener? = null,
  private val onSoftKeyboardOpenListener: OnSoftKeyboardOpenListener? = null,
  private val onEmojiBackspaceClickListener: OnEmojiBackspaceClickListener? = null,
  private val onEmojiClickListener: OnEmojiClickListener? = null,
  private val onEmojiPopupDismissListener: OnEmojiPopupDismissListener? = null,
) : EmojiResultReceiver.Receiver {
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
      rootView,
      onEmojiClickListener,
      onEmojiBackspaceClickListener,
      editText,
      theming,
      recentEmoji,
      searchEmoji,
      variantEmoji,
      pageTransformer,
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
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    if (Utils.shouldOverrideRegularCondition(context, editText)) {
      editText.imeOptions = editText.imeOptions or EditorInfo.IME_FLAG_NO_EXTRACT_UI
      inputMethodManager?.restartInput(editText)
    }
    if (inputMethodManager != null) {
      emojiResultReceiver.setReceiver(this)
      inputMethodManager.showSoftInput(editText, InputMethodManager.RESULT_UNCHANGED_SHOWN, emojiResultReceiver)
    }
  }

  val isShowing: Boolean
    get() = popupWindow.isShowing

  fun dismiss() {
    popupWindow.dismiss()
    emojiView.tearDown()
    emojiResultReceiver.setReceiver(null)
    if (originalImeOptions != -1) {
      editText.imeOptions = originalImeOptions
      val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
      inputMethodManager?.restartInput(editText)
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
        Utils.getProperHeight(context) + popupWindowHeight
      )
    }, delay.toLong())
    onEmojiPopupShownListener?.onEmojiPopupShown()
  }

  override fun onReceiveResult(resultCode: Int, data: Bundle?) {
    if (resultCode == 0 || resultCode == 1) {
      showAtBottom()
    }
  }

  @Suppress("DEPRECATION")
  @Deprecated("Please use EmojiPopup constructor directly")
  class Builder private constructor(val rootView: View) {
    @StyleRes private var keyboardAnimationStyle = 0
    private var theming = EmojiTheming()
    private var pageTransformer: ViewPager.PageTransformer? = null
    private var onEmojiPopupShownListener: OnEmojiPopupShownListener? = null
    private var onSoftKeyboardCloseListener: OnSoftKeyboardCloseListener? = null
    private var onSoftKeyboardOpenListener: OnSoftKeyboardOpenListener? = null
    private var onEmojiBackspaceClickListener: OnEmojiBackspaceClickListener? = null
    private var onEmojiClickListener: OnEmojiClickListener? = null
    private var onEmojiPopupDismissListener: OnEmojiPopupDismissListener? = null
    private var recentEmoji: RecentEmoji
    private var searchEmoji: SearchEmoji
    private var variantEmoji: VariantEmoji
    private var popupWindowHeight = 0

    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setOnSoftKeyboardCloseListener(listener: OnSoftKeyboardCloseListener?): Builder {
      onSoftKeyboardCloseListener = listener
      return this
    }

    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setOnEmojiClickListener(listener: OnEmojiClickListener?): Builder {
      onEmojiClickListener = listener
      return this
    }

    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setOnSoftKeyboardOpenListener(listener: OnSoftKeyboardOpenListener?): Builder {
      onSoftKeyboardOpenListener = listener
      return this
    }

    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setOnEmojiPopupShownListener(listener: OnEmojiPopupShownListener?): Builder {
      onEmojiPopupShownListener = listener
      return this
    }

    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setOnEmojiPopupDismissListener(listener: OnEmojiPopupDismissListener?): Builder {
      onEmojiPopupDismissListener = listener
      return this
    }

    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setOnEmojiBackspaceClickListener(listener: OnEmojiBackspaceClickListener?): Builder {
      onEmojiBackspaceClickListener = listener
      return this
    }

    /**
     * Set PopUpWindow's height.
     * If height is not 0 then this value will be used later on. If it is 0 then the keyboard height will
     * be dynamically calculated and set as [PopupWindow] height.
     * @param windowHeight - the height of [PopupWindow]
     *
     * @since 0.7.0
     */
    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setPopupWindowHeight(windowHeight: Int): Builder {
      popupWindowHeight = max(windowHeight, 0)
      return this
    }

    /**
     * Allows you to pass your own implementation of recent emojis. If not provided the default one
     * [RecentEmojiManager] will be used.
     *
     * @since 0.2.0
     */
    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setRecentEmoji(recent: RecentEmoji): Builder {
      recentEmoji = recent
      return this
    }

    /**
     * Allows you to pass your own implementation of searching emojis. If not provided the default one
     * [SearchEmojiManager] will be used.
     *
     * @since 0.10.0
     */
    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setSearchEmoji(search: SearchEmoji): Builder {
      searchEmoji = search
      return this
    }

    /**
     * Allows you to pass your own implementation of variant emojis. If not provided the default one
     * [VariantEmojiManager] will be used.
     *
     * @since 0.5.0
     */
    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setVariantEmoji(variant: VariantEmoji): Builder {
      variantEmoji = variant
      return this
    }

    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setTheming(theming: EmojiTheming): Builder {
      this.theming = theming
      return this
    }

    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setKeyboardAnimationStyle(@StyleRes animation: Int): Builder {
      keyboardAnimationStyle = animation
      return this
    }

    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun setPageTransformer(transformer: ViewPager.PageTransformer?): Builder {
      pageTransformer = transformer
      return this
    }

    @CheckResult
    @Deprecated("Please use EmojiPopup constructor directly")
    fun build(editText: EditText): EmojiPopup = EmojiPopup(
      rootView = rootView,
      editText = editText,
      theming = theming,
      recentEmoji = recentEmoji,
      searchEmoji = searchEmoji,
      variantEmoji = variantEmoji,
      pageTransformer = pageTransformer,
      keyboardAnimationStyle = keyboardAnimationStyle,
      onEmojiPopupShownListener = onEmojiPopupShownListener,
      onSoftKeyboardCloseListener = onSoftKeyboardCloseListener,
      onSoftKeyboardOpenListener = onSoftKeyboardOpenListener,
      onEmojiBackspaceClickListener = onEmojiBackspaceClickListener,
      onEmojiClickListener = onEmojiClickListener,
      onEmojiPopupDismissListener = onEmojiPopupDismissListener,
      popupWindowHeight = max(popupWindowHeight, 0),
    )

    @Deprecated("Please use EmojiPopup constructor directly")
    companion object {
      /**
       * @param rootView The root View of your layout.xml which will be used for calculating the height
       * of the keyboard.
       * @return builder For building the [EmojiPopup].
       */
      @CheckResult
      @Deprecated("Please use EmojiPopup constructor directly")
      fun fromRootView(rootView: View): Builder {
        return Builder(rootView)
      }
    }

    init {
      recentEmoji = RecentEmojiManager(rootView.context)
      searchEmoji = SearchEmojiManager()
      variantEmoji = VariantEmojiManager(rootView.context)
    }
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
    var previousOffset = 0
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

  internal companion object {
    const val MIN_KEYBOARD_HEIGHT = 50
    const val APPLY_WINDOW_INSETS_DURATION = 250
  }
}
