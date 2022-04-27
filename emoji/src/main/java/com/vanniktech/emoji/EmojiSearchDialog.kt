package com.vanniktech.emoji

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vanniktech.emoji.emoji.Emoji
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

internal interface EmojiSearchDialogDelegate {
  fun onClicked(emoji: Emoji)
}

internal class EmojiSearchDialog : DialogFragment() {
  private var delegate: EmojiSearchDialogDelegate? = null
  private var recentEmoji: RecentEmoji? = null
  private var searchEmoji: SearchEmoji? = null

  private val handler = Handler(Looper.getMainLooper())

  private var future: ScheduledFuture<*>? = null
  private val executorService = Executors.newSingleThreadScheduledExecutor()

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val activity = requireActivity()
    val dialog = AlertDialog.Builder(activity, theme)
      .setView(R.layout.emoji_dialog_search)
      .show()

    val root = dialog.findViewById<View>(R.id.root)
    val arguments = requireArguments()
    val theming = arguments.getParcelable<EmojiTheming>(ARG_THEMING)!!
    root?.setBackgroundColor(theming.backgroundColor(activity))

    val editText = dialog.findViewById<EditText>(R.id.editText)!!
    editText.setTextColor(theming.textColor(activity))
    editText.highlightColor = theming.secondaryColor(activity)
    ViewCompat.setBackgroundTintList(editText, ColorStateList.valueOf(theming.secondaryColor(activity)))

    val recyclerView = dialog.findViewById<RecyclerView>(R.id.recyclerView)
    val adapter = EmojiAdapter(
      theming = theming,
      recentEmoji = recentEmoji,
      emojiSearchDialogDelegate = delegate,
    )
    recyclerView?.adapter = adapter

    editText.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
      override fun afterTextChanged(s: Editable) {
        val query = s.toString()

        future?.cancel(true)
        future = executorService.schedule({
          val emojis = searchEmoji?.search(query).orEmpty()
          handler.post {
            adapter.update(emojis)
          }
        }, 300, TimeUnit.MILLISECONDS)
      }
    })

    editText.postDelayed({
      editText.requestFocus()
      (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(editText, 0)
    }, 300L)

    return dialog
  }

  override fun onDismiss(dialog: DialogInterface) {
    super.onDismiss(dialog)
    future?.cancel(true)
    executorService?.shutdownNow()
    handler.removeCallbacksAndMessages(null)
    delegate = null
    recentEmoji = null
  }

  internal companion object {
    private const val TAG = "EmojiSearchDialog"
    private const val ARG_THEMING = "arg-theming"

    @JvmStatic fun show(
      context: Context,
      delegate: EmojiSearchDialogDelegate,
      searchEmoji: SearchEmoji,
      recentEmoji: RecentEmoji,
      theming: EmojiTheming,
    ) {
      EmojiSearchDialog().apply {
        arguments = Bundle(1).apply {
          putParcelable(ARG_THEMING, theming)
        }
        this.delegate = delegate
        this.searchEmoji = searchEmoji
        this.recentEmoji = recentEmoji
        show((Utils.asActivity(context) as FragmentActivity).supportFragmentManager, TAG)
      }
    }
  }
}

private class EmojiAdapter(
  private val theming: EmojiTheming,
  private val emojiSearchDialogDelegate: EmojiSearchDialogDelegate?,
  private val recentEmoji: RecentEmoji?,
) : RecyclerView.Adapter<EmojiViewHolder>() {
  private var items = emptyList<Emoji>()

  init {
    setHasStableIds(true)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EmojiViewHolder(parent)

  override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
    val context = holder.textView.context
    val emoji = items[position]
    holder.textView.text = emoji.unicode

    val shortCodes = emoji.shortcodes.orEmpty().joinToString(separator = ", ")
    holder.shortCodes.text = shortCodes
    holder.shortCodes.visibility = if (shortCodes.isBlank()) View.GONE else View.VISIBLE
    holder.shortCodes.setTextColor(theming.textSecondaryColor(context))

    holder.itemView.setOnClickListener {
      recentEmoji?.addEmoji(emoji)
      emojiSearchDialogDelegate?.onClicked(emoji)
    }
  }

  override fun getItemCount() = items.size

  fun update(new: List<Emoji>) {
    val old = ArrayList(items)
    items = new

    DiffUtil.calculateDiff(DiffUtilHelper(old, items) { it.hashCode() })
      .dispatchUpdatesTo(this)
  }
}

private class EmojiViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.emoji_adapter_item_emoji_search, parent, false)) {
  val textView: EmojiTextView by lazy(LazyThreadSafetyMode.NONE) { itemView.findViewById(R.id.textView) }
  val shortCodes: TextView by lazy(LazyThreadSafetyMode.NONE) { itemView.findViewById(R.id.shortCodes) }
}

class DiffUtilHelper<T>(
  private val old: List<T>,
  private val new: List<T>,
  private val id: (T) -> Int
) : DiffUtil.Callback() {
  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
    id(old[oldItemPosition]) == id(new[newItemPosition])

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
    old[oldItemPosition] == new[newItemPosition]

  override fun getOldListSize() = old.size

  override fun getNewListSize() = new.size
}
