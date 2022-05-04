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
 */

package com.vanniktech.emoji.twitter

import com.vanniktech.emoji.EmojiProvider
import com.vanniktech.emoji.emoji.EmojiCategory
import com.vanniktech.emoji.twitter.category.ActivitiesCategory
import com.vanniktech.emoji.twitter.category.AnimalsAndNatureCategory
import com.vanniktech.emoji.twitter.category.FlagsCategory
import com.vanniktech.emoji.twitter.category.FoodAndDrinkCategory
import com.vanniktech.emoji.twitter.category.ObjectsCategory
import com.vanniktech.emoji.twitter.category.SmileysAndPeopleCategory
import com.vanniktech.emoji.twitter.category.SymbolsCategory
import com.vanniktech.emoji.twitter.category.TravelAndPlacesCategory

class TwitterEmojiProvider : EmojiProvider {
  override val categories: Array<EmojiCategory>
    get() = arrayOf(
      SmileysAndPeopleCategory(),
      AnimalsAndNatureCategory(),
      FoodAndDrinkCategory(),
      ActivitiesCategory(),
      TravelAndPlacesCategory(),
      ObjectsCategory(),
      SymbolsCategory(),
      FlagsCategory(),
    )
}
