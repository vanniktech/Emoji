package com.vanniktech.emoji.emoji;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Emoji implements Serializable {
  private static final long serialVersionUID = 3L;

  @NonNull private final String unicode;
  @DrawableRes private final int resource;
  @NonNull private List<Emoji> variants;
  @SuppressWarnings("PMD.ImmutableField") @Nullable private Emoji base;

  public Emoji(@NonNull final int[] codePoints, @DrawableRes final int resource) {
    this(codePoints, resource, new Emoji[0]);
  }

  public Emoji(final int codePoint, @DrawableRes final int resource) {
    this(codePoint, resource, new Emoji[0]);
  }

  public Emoji(@NonNull final int[] codePoints, @DrawableRes final int resource, final Emoji... variants) {
    this.unicode = new String(codePoints, 0, codePoints.length);
    this.resource = resource;
    this.variants = Arrays.asList(variants);

    for (final Emoji variant : variants) {
      variant.base = this;
    }
  }

  public Emoji(final int codePoint, @DrawableRes final int resource, final Emoji... variants) {
    this(new int[]{codePoint}, resource, variants);
  }

  @NonNull public String getUnicode() {
    return unicode;
  }

  @DrawableRes public int getResource() {
    return resource;
  }

  @NonNull public List<Emoji> getVariants() {
    return new ArrayList<>(variants);
  }

  @NonNull public Emoji getBase() {
    Emoji result = this;

    while (result.base != null) {
      result = result.base;
    }

    return result;
  }

  public int getLength() {
    return unicode.length();
  }

  public boolean hasVariants() {
    return !variants.isEmpty();
  }

  @Override public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Emoji emoji = (Emoji) o;

    return resource == emoji.resource
            && unicode.equals(emoji.unicode)
            && variants.equals(emoji.variants);
  }

  @Override public int hashCode() {
    int result = unicode.hashCode();
    result = 31 * result + resource;
    result = 31 * result + variants.hashCode();
    return result;
  }
}
