package com.vanniktech.emoji.kotlin

import com.vanniktech.emoji.EmojiUtils

fun CharSequence.isOnlyEmojis() = EmojiUtils.isOnlyEmojis(this)

fun CharSequence.emojisCount() = EmojiUtils.emojisCount(this)

fun CharSequence.emojis() = EmojiUtils.emojis(this)

fun CharSequence.emojiInformation() = EmojiUtils.emojiInformation(this)
