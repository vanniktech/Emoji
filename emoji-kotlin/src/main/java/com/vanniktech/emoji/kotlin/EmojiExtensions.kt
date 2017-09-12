package com.vanniktech.emoji.kotlin

import com.vanniktech.emoji.EmojiUtils

fun String.isOnlyEmojis() = EmojiUtils.isOnlyEmojis(this)

fun String.emojisCount() = EmojiUtils.emojisCount(this)

fun String.emojis() = EmojiUtils.emojis(this)

fun String.emojiInformation() = EmojiUtils.emojiInformation(this)
