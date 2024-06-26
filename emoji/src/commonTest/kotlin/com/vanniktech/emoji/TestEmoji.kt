package com.vanniktech.emoji

val emojiBalloon = TestEmoji(
  unicode = "\ud83c\udf88",
  shortcodes = listOf("balloon"),
)

val emojiYoYo = TestEmoji(
  unicode = "\ud83e\ude80",
  shortcodes = listOf("yo-yo"),
)

val emojiReminderRibbon = TestEmoji(
  unicode = "\ud83c\udf97",
  shortcodes = listOf("reminder_ribbon"),
  variants = listOf(
    TestEmoji("\ud83c\udf97\ufe0f", emptyList()),
  ),
)

val emojiBaldPerson = TestEmoji(
  unicode = "\ud83e\uddd1\u200d\ud83e\uddb2",
  shortcodes = listOf("bald_person"),
  variants = listOf(
    TestEmoji("\ud83e\uddd1\ud83c\udffb\u200d\ud83e\uddb2", emptyList()),
    TestEmoji("\ud83e\uddd1\ud83c\udffc\u200d\ud83e\uddb2", emptyList()),
    TestEmoji("\ud83e\uddd1\ud83c\udffd\u200d\ud83e\uddb2", emptyList()),
    TestEmoji("\ud83e\uddd1\ud83c\udffe\u200d\ud83e\uddb2", emptyList()),
    TestEmoji("\ud83e\uddd1\ud83c\udfff\u200d\ud83e\uddb2", emptyList()),
  ),
)

val emojiSuperhero = TestEmoji(
  unicode = "\ud83e\uddb8",
  shortcodes = listOf("superhero"),
  variants = listOf(
    TestEmoji("\ud83e\uddb8\ud83c\udffb", emptyList()),
    TestEmoji("\ud83e\uddb8\ud83c\udffc", emptyList()),
    TestEmoji("\ud83e\uddb8\ud83c\udffd", emptyList()),
    TestEmoji("\ud83e\uddb8\ud83c\udffe", emptyList()),
    TestEmoji("\ud83e\uddb8\ud83c\udfff", emptyList()),
  ),
)

data class TestEmoji internal constructor(
  override val unicode: String,
  override val shortcodes: List<String>,
  override val variants: List<TestEmoji> = emptyList(),
  private var parent: TestEmoji? = null,
) : Emoji {
  override val base by lazy(LazyThreadSafetyMode.NONE) {
    var result = this
    while (result.parent != null) {
      result = result.parent!!
    }
    result
  }

  init {
    for (variant in variants) {
      variant.parent = this
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as TestEmoji

    if (unicode != other.unicode) return false
    if (shortcodes != other.shortcodes) return false
    if (variants != other.variants) return false

    return true
  }

  override fun hashCode(): Int {
    var result = unicode.hashCode()
    result = 31 * result + shortcodes.hashCode()
    result = 31 * result + variants.hashCode()
    return result
  }

  override fun toString() = "TestEmoji(unicode='$unicode', shortcodes=$shortcodes, variants=$variants)"
}
