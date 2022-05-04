package com.vanniktech.emoji

import com.vanniktech.emoji.emoji.Emoji

object NoVariantEmoji : VariantEmoji {
  override fun getVariant(desiredEmoji: Emoji): Emoji = desiredEmoji
  override fun addVariant(newVariant: Emoji) = Unit
  override fun persist() = Unit
}
