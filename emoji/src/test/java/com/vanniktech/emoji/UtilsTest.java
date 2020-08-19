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

package com.vanniktech.emoji;

import com.vanniktech.emoji.emoji.Emoji;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {
  @Rule public final ExpectedException expectedException = ExpectedException.none();

  @Test public void checkNull() {
    expectedException.expect(IllegalArgumentException.class);
    expectedException.expectMessage("param is null");

    Utils.checkNotNull(null, "param is null");
  }

  @Test public void checkNotNull() {
    Utils.checkNotNull("valid", "null is null");
  }

  @Test public void asListFilter() {
    final Emoji[] emojis = new Emoji[] {
      new Emoji("\u1234".codePointAt(0), new String[]{"test"}, R.drawable.emoji_backspace, false),
      new Emoji("\u1234".codePointAt(0), new String[]{"test"}, R.drawable.emoji_backspace, true),
    };

    final List<Emoji> filtered = Utils.asListWithoutDuplicates(emojis);

    assertThat(filtered).containsExactly(emojis[0]);
  }
}
