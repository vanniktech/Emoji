package com.vanniktech.emoji;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.CallSuper;
import android.support.annotation.DimenRes;
import android.support.annotation.Px;
import android.support.v7.widget.AppCompatEditText;
import android.widget.EditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import com.vanniktech.emoji.emoji.Emoji;

/**
 * Interface used to allow custom EmojiEditText objects on another project.
 */
public interface EmojiEditTextInterface {

  @CallSuper public void backspace();

  @CallSuper public void input(final Emoji emoji);

  public float getEmojiSize();
  public void setEmojiSize(@Px final int pixels);
  public void setEmojiSize(@Px final int pixels, final boolean shouldInvalidate);
  public void setEmojiSizeRes(@DimenRes final int res);
  public void setEmojiSizeRes(@DimenRes final int res, final boolean shouldInvalidate);
}
