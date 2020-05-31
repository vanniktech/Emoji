package com.vanniktech.emoji;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Forces the {@link EditText} to only contain one Emoji, while also being able to replace the previous one.
 */
public final class SingleEmojiTrait implements TextWatcher {
  final EditText editText;

  public static void install(final EditText editText) {
    new SingleEmojiTrait(editText);
  }

  private SingleEmojiTrait(final EditText editText) {
    this.editText = editText;

    final List<InputFilter> filters = new ArrayList<>(Arrays.asList(editText.getFilters()));
    filters.add(new OnlyEmojisInputFilter());
    editText.setFilters(filters.toArray(new InputFilter[0]));
    editText.addTextChangedListener(this);
  }

  @Override public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
    // No-op.
  }

  @Override public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
    editText.removeTextChangedListener(this);

    final CharSequence emoji = s.subSequence(start, start + count);
    editText.setText(null);
    editText.append(emoji);

    editText.addTextChangedListener(this);
  }

  @Override public void afterTextChanged(final Editable s) {
    // No-op.
  }
}
