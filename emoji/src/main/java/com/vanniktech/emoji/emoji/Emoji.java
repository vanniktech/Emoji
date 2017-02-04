package com.vanniktech.emoji.emoji;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import java.io.Serializable;

public final class Emoji implements Serializable {
  private static final long serialVersionUID = 2L;

  @NonNull private final String unicode;
  @DrawableRes private final int resource;
  private final boolean skinToned;

  public Emoji(@NonNull final int[] codePoints, @DrawableRes final int resource) {
    this.unicode = new String(codePoints, 0, codePoints.length);
    this.resource = resource;
    this.skinToned = false;
  }

  public Emoji(final int codePoint, @DrawableRes final int resource) {
    this.unicode = new String(new int[] { codePoint }, 0, 1);
    this.resource = resource;
    this.skinToned = false;
  }

  public Emoji(@NonNull final int[] codePoints, @DrawableRes final int resource, final boolean skinToned) {
    this.unicode = new String(codePoints, 0, codePoints.length);
    this.resource = resource;
    this.skinToned = skinToned;
  }

  public Emoji(final int codePoint, @DrawableRes final int resource, final boolean skinToned) {
    this.unicode = new String(new int[] { codePoint }, 0, 1);
    this.resource = resource;
    this.skinToned = skinToned;
  }

  @NonNull public String getUnicode() {
    return unicode;
  }

  @DrawableRes public int getResource() {
    return resource;
  }

  public int getLength() {
    return unicode.length();
  }

  public boolean isSkinToned(){
    return skinToned;
  }

  @Override public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Emoji emoji = (Emoji) o;
    return resource == emoji.resource && unicode.equals(emoji.unicode);
  }

  @Override public int hashCode() {
    int result = unicode.hashCode();
    result = 31 * result + resource;
    return result;
  }
}
