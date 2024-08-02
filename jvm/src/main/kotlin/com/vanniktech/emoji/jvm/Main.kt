package com.vanniktech.emoji.jvm

import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.emojiInformation
import com.vanniktech.emoji.ios.IosEmojiProvider
import com.vanniktech.emoji.search.SearchEmojiManager

fun main() {
  EmojiManager.install(IosEmojiProvider())

  val emojiString = """Hello from JVM ❤️💚💙"""
  println("\nGetting emoji information from: $emojiString")
  val emojiInformation = emojiString.emojiInformation()
  println(emojiInformation)

  val query = "swim"
  println("\nSearching for all emojis with $query:")
  SearchEmojiManager().search(query = query)
    .forEach {
      println(it)
    }
}
