package com.vanniktech.emoji.material;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.Nullable;
import com.vanniktech.emoji.EmojiLayoutFactory;

/** Layout Factory that substitutes certain Views to add automatic Emoji support. */
public class MaterialEmojiLayoutFactory extends EmojiLayoutFactory {
  public MaterialEmojiLayoutFactory() {
    super();
  }

  public MaterialEmojiLayoutFactory(@Nullable final LayoutInflater.Factory2 delegate) {
    super(delegate);
  }

  @Override public View onCreateView(@Nullable final View parent, final String name, final Context context, final AttributeSet attrs) {
    if ("Button".equals(name)) {
      return new EmojiMaterialButton(context, attrs);
    } else if ("CheckBox".equals(name)) {
      return new EmojiMaterialCheckBox(context, attrs);
    } else if ("RadioButton".equals(name)) {
      return new EmojiMaterialRadioButton(context, attrs);
    } else {
      return super.onCreateView(parent, name, context, attrs);
    }
  }
}
