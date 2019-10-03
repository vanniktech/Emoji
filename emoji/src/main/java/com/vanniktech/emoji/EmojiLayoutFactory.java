package com.vanniktech.emoji;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.Nullable;

/** Layout Factory that substitutes certain Views to add automatic Emoji support. */
public class EmojiLayoutFactory implements LayoutInflater.Factory2 {
  @Nullable private final LayoutInflater.Factory2 delegate;

  public EmojiLayoutFactory() {
    delegate = null;
  }

  public EmojiLayoutFactory(@Nullable final LayoutInflater.Factory2 delegate) {
    this.delegate = delegate;
  }

  @Override public View onCreateView(@Nullable final View parent, final String name, final Context context, final AttributeSet attrs) {
    if ("TextView".equals(name)) {
      return new EmojiTextView(context, attrs);
    } else if ("EditText".equals(name)) {
      return new EmojiEditText(context, attrs);
    } else if ("Button".equals(name)) {
      return new EmojiButton(context, attrs);
    } else if ("AutoCompleteTextView".equals(name)) {
      return new EmojiAutoCompleteTextView(context, attrs);
    } else if ("MultiAutoCompleteTextView".equals(name)) {
      return new EmojiMultiAutoCompleteTextView(context, attrs);
    } else if (delegate != null) {
      return delegate.onCreateView(parent, name, context, attrs);
    }

    return null;
  }

  @Override public View onCreateView(final String name, final Context context, final AttributeSet attrs) {
    return onCreateView(null, name, context, attrs);
  }
}
