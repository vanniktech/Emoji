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

package com.vanniktech.emoji.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@SuppressWarnings("PMD.ArrayIsStoredDirectly") public class Emoji implements Serializable {
  private static final long serialVersionUID = 3L;
  private static final List<Emoji> EMPTY_EMOJI_LIST = emptyList();

  @NonNull private final String unicode;
  @NonNull private final String unicodeForPattern;
  @NonNull private final String[] shortcodes;
  @DrawableRes private final int resource;
  private final boolean isDuplicate;
  @NonNull private final List<Emoji> variants;
  @Nullable private Emoji base;

  public Emoji(@NonNull final int[] codePoints, @NonNull final String[] shortcodes,
               @DrawableRes final int resource, final boolean isDuplicate) {
    this(codePoints, shortcodes, resource, isDuplicate, new Emoji[0]);
  }

  public Emoji(final int codePoint, @NonNull final String[] shortcodes,
               @DrawableRes final int resource, final boolean isDuplicate) {
    this(codePoint, shortcodes, resource, isDuplicate, new Emoji[0]);
  }

  public Emoji(final int codePoint, @NonNull final String[] shortcodes,
               @DrawableRes final int resource, final boolean isDuplicate,
               final Emoji... variants) {
    this(new int[]{codePoint}, shortcodes, resource, isDuplicate, variants);
  }

  public Emoji(@NonNull final int[] codePoints, @NonNull final String[] shortcodes,
               @DrawableRes final int resource, final boolean isDuplicate,
               final Emoji... variants) {
    this.unicodeForPattern = new String(new int[]{codePoints[0]}, 0, 1);
    this.unicode = new String(codePoints, 0, codePoints.length);
    this.shortcodes = shortcodes;
    this.resource = resource;
    this.isDuplicate = isDuplicate;
    this.variants = variants.length == 0 ? EMPTY_EMOJI_LIST : asList(variants);
    for (final Emoji variant : variants) {
      variant.base = this;
    }
  }

  @NonNull public String getUnicode() {
    return unicode;
  }

  @NonNull public String getUnicodeForPattern() {
    return unicodeForPattern;
  }

  @Nullable public List<String> getShortcodes() {
    return asList(shortcodes);
  }

  /**
   * @deprecated Please migrate to getDrawable(). May return -1 in the future for providers that don't use
   * resources.
   */
  @Deprecated @DrawableRes public int getResource() {
    return resource;
  }

  @NonNull public Drawable getDrawable(final Context context) {
    return AppCompatResources.getDrawable(context, resource);
  }

  public boolean isDuplicate() {
    return isDuplicate;
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

  public void destroy() {
    // For inheritors to override.
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
            && unicodeForPattern.equals(emoji.unicodeForPattern)
            && Arrays.equals(shortcodes, emoji.shortcodes)
            && variants.equals(emoji.variants);
  }

  @Override public int hashCode() {
    int result = unicodeForPattern.hashCode();
    result = 31 * result + Arrays.hashCode(shortcodes);
    result = 31 * result + resource;
    result = 31 * result + variants.hashCode();
    return result;
  }


}
