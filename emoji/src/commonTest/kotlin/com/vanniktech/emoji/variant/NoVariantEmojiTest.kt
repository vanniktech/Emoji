package com.vanniktech.emoji.variant

import com.vanniktech.emoji.emojiBalloon
import com.vanniktech.emoji.emojiReminderRibbon
import com.vanniktech.emoji.emojiSuperhero
import kotlin.test.Test
import kotlin.test.assertEquals

class NoVariantEmojiTest {
  @Test fun implementation() {
    NoVariantEmoji.addVariant(emojiReminderRibbon.variants.first())
    NoVariantEmoji.persist()

    assertEquals(expected = emojiBalloon, actual = NoVariantEmoji.getVariant(emojiBalloon))
    assertEquals(expected = emojiReminderRibbon, actual = NoVariantEmoji.getVariant(emojiReminderRibbon))
    assertEquals(expected = emojiReminderRibbon.variants.first(), actual = NoVariantEmoji.getVariant(emojiReminderRibbon.variants.first()))
    assertEquals(expected = emojiSuperhero, actual = NoVariantEmoji.getVariant(emojiSuperhero))
    assertEquals(expected = emojiSuperhero.variants[2], actual = NoVariantEmoji.getVariant(emojiSuperhero.variants[2]))

    assertEquals(expected = emptyList(), actual = NoVariantEmoji.getVariants(emojiBalloon))
    assertEquals(expected = emptyList(), actual = NoVariantEmoji.getVariants(emojiReminderRibbon))
    assertEquals(expected = emptyList(), actual = NoVariantEmoji.getVariants(emojiReminderRibbon.variants.first()))
    assertEquals(expected = emptyList(), actual = NoVariantEmoji.getVariants(emojiSuperhero))
    assertEquals(expected = emptyList(), actual = NoVariantEmoji.getVariants(emojiSuperhero.variants[2]))
  }
}
