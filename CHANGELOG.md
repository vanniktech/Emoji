# Change Log

Version 0.23.0 *(In development)*
---------------------------------

Version 0.22.0 *(2025-09-08)*
-----------------------------

- API: Deprecate emojisCount(), emojis() and isOnlyEmojis() function calls on CharSequence. Instead use CharSequence#emojiInformation() [\#1121](https://github.com/vanniktech/Emoji/pull/1121) ([vanniktech](https://github.com/vanniktech))
- API: EmojiInformation#visualLength to get text length respecting that an Emoji can consist of multiple characters. [\#1118](https://github.com/vanniktech/Emoji/pull/1118) ([vanniktech](https://github.com/vanniktech))
- Breaking: EmojiInformation rename emojis to emojiRanges & add new emojiCount property. [\#1120](https://github.com/vanniktech/Emoji/pull/1120) ([vanniktech](https://github.com/vanniktech))
- Breaking: Only allow non null CharSequence#emojiInformation() call. [\#1119](https://github.com/vanniktech/Emoji/pull/1119) ([vanniktech](https://github.com/vanniktech))
- Bug fix: Correct range of EmojiRange. It was off by one. [\#1117](https://github.com/vanniktech/Emoji/pull/1117) ([vanniktech](https://github.com/vanniktech))
- Fix: correct Emoji Popup height on devices with gesture navigation [\#1188](https://github.com/vanniktech/Emoji/pull/1188) ([DavidImbox](https://github.com/DavidImbox))

Version 0.21.0 *(2024-06-27)*
-----------------------------

- Multiplatform: Add iOS target. [\#834](https://github.com/vanniktech/Emoji/pull/834) ([vanniktech](https://github.com/vanniktech))
- Technical: Import JvmField annotation for Multiplatform. [\#1107](https://github.com/vanniktech/Emoji/pull/1107) ([vanniktech](https://github.com/vanniktech))
- Technical: Update Android Gradle Plugin to 8.5.0 [\#1101](https://github.com/vanniktech/Emoji/pull/1101) ([vanniktech](https://github.com/vanniktech))
- Technical: Update Kotlin to 2.0.0 [\#1095](https://github.com/vanniktech/Emoji/pull/1095) ([renovate[bot]](https://github.com/apps/renovate))
- Technical: Use equals without referencing javaClass. [\#1106](https://github.com/vanniktech/Emoji/pull/1106) ([vanniktech](https://github.com/vanniktech))
- Use generator to create `unicode` String [\#1109](https://github.com/vanniktech/Emoji/pull/1109) ([cketti](https://github.com/cketti))

Huge thanks to [cketti](https://github.com/cketti) for helping making this library available on iOS!

Version 0.20.0 *(2024-05-18)*
-----------------------------

- API: Remove no longer needed isDuplicate property from Emoji interface. [\#1090](https://github.com/vanniktech/Emoji/pull/1090) ([vanniktech](https://github.com/vanniktech))
- API: VariantEmoji\#getVariants method to modify which variants shall be displayed. Also by default filters out the Variant Selector 16 variants. [\#1092](https://github.com/vanniktech/Emoji/pull/1092) ([vanniktech](https://github.com/vanniktech))
- Behavior change: Stop filtering out emojis which have been obsoleted. This way we also have the neutral version of many emojis such like fairy. Assimilates behavior to Telegram/Whatsapp. [\#1089](https://github.com/vanniktech/Emoji/pull/1089) ([vanniktech](https://github.com/vanniktech))
- Behavior change: Stop filtering out neutral emojis that contain male & female variants such as juggling, handball, water\_polo, person\_doing\_cartwheel, wrestlers, shrug & facepalm. [\#1088](https://github.com/vanniktech/Emoji/pull/1088) ([vanniktech](https://github.com/vanniktech))

Version 0.19.0 *(2024-05-17)*
-----------------------------

- API: New emoji-androidx-emoji2 module which uses androidx.emoji2:emoji2 [\#1086](https://github.com/vanniktech/Emoji/pull/1086) ([vanniktech](https://github.com/vanniktech))
- Bug fix: Fix EmojiUtils.isOnlyEmojis not detecting emojis with variant selectors. [\#1087](https://github.com/vanniktech/Emoji/pull/1087) ([vanniktech](https://github.com/vanniktech))
- Technical: Update Emoji source to 15.1.2 to include new emojis. [\#1085](https://github.com/vanniktech/Emoji/pull/1085) ([vanniktech](https://github.com/vanniktech))

Version 0.18.0 *(2023-12-11)*
-----------------------------

- Behavior change: SearchEmojiManager sorts by found index and then by shortcode. [\#1041](https://github.com/vanniktech/Emoji/pull/1041) ([vanniktech](https://github.com/vanniktech))
- Breaking change: Emoji is no longer Parcelable. [\#1043](https://github.com/vanniktech/Emoji/pull/1043) ([vanniktech](https://github.com/vanniktech))
- Bug fix: Correctly use stable ids in EmojiAdapter to prevent showing the same Emoji multiple times. [\#1040](https://github.com/vanniktech/Emoji/pull/1040) ([vanniktech](https://github.com/vanniktech))
- Technical: Kotlin 1.9.21 & Target Android 34. [\#1044](https://github.com/vanniktech/Emoji/pull/1044) ([vanniktech](https://github.com/vanniktech))

Version 0.17.0 *(2023-06-27)*
-----------------------------

- Support Emoji Version 15.0 [\#991](https://github.com/vanniktech/Emoji/pull/991) ([vanniktech](https://github.com/vanniktech))
- Gradle: Set Jvm Toolchain to 11. [\#990](https://github.com/vanniktech/Emoji/pull/990) ([vanniktech](https://github.com/vanniktech))

Version 0.16.0 *(2023-03-25)*
-----------------------------

- Android: Target 33. [\#888](https://github.com/vanniktech/Emoji/pull/888) ([vanniktech](https://github.com/vanniktech))
- Breaking: EmojiTheming constructor requires all colors to be set. New EmojiTheming.from\(Context\) to retrieve colors from your Activity/Fragment. [\#904](https://github.com/vanniktech/Emoji/pull/904) ([vanniktech](https://github.com/vanniktech))
- Generate smaller Emoji Chunks classes to avoid MethodTooLargeException [\#884](https://github.com/vanniktech/Emoji/pull/884) ([bmarty](https://github.com/bmarty))
- Technical: Use Android Source Set Layout Version 2. [\#962](https://github.com/vanniktech/Emoji/pull/962) ([vanniktech](https://github.com/vanniktech))
- Theming: Fix Cursor color with Android Material 1.8.0 and later. [\#954](https://github.com/vanniktech/Emoji/pull/954) ([vanniktech](https://github.com/vanniktech))

Version 0.15.0 *(2022-05-28)*
-----------------------------

- Multiplatform: Fix Android publishing. [\#837](https://github.com/vanniktech/Emoji/pull/837) ([vanniktech](https://github.com/vanniktech))

Version 0.14.0 *(2022-05-27)*
-----------------------------

There was a problem publishing Android artifacts with 0.14.0, please update directly to 0.15.0

- Multiplatform: JVM example. [\#836](https://github.com/vanniktech/Emoji/pull/836) ([vanniktech](https://github.com/vanniktech))
- Nuke PMD configuration. [\#835](https://github.com/vanniktech/Emoji/pull/835) ([vanniktech](https://github.com/vanniktech))
- Action: First do jvmTest, ktlint & testDebug & then build. [\#833](https://github.com/vanniktech/Emoji/pull/833) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move emoji-ios tests into commonTest. [\#832](https://github.com/vanniktech/Emoji/pull/832) ([vanniktech](https://github.com/vanniktech))
- Android: Generate list with sheets and stop using Resource reflection. [\#831](https://github.com/vanniktech/Emoji/pull/831) ([vanniktech](https://github.com/vanniktech))
- Breaking: Rename EmojiDrawableProvider to EmojiAndroidProvider. [\#830](https://github.com/vanniktech/Emoji/pull/830) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Generate EmojiProviders into jvmMain. [\#829](https://github.com/vanniktech/Emoji/pull/829) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: EmojiCategory implementations into commonMain. [\#828](https://github.com/vanniktech/Emoji/pull/828) ([vanniktech](https://github.com/vanniktech))
- Breaking: Use EmojiDrawableProvider\#getIcon\(EmojiCategory\) instead of EmojiCategory\#icon for proper Multiplatform support. [\#827](https://github.com/vanniktech/Emoji/pull/827) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move most of the tests into commonTest. [\#826](https://github.com/vanniktech/Emoji/pull/826) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move SearchEmojiManager into commonMain. [\#825](https://github.com/vanniktech/Emoji/pull/825) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move CharSequence extension methods into commonMain. [\#824](https://github.com/vanniktech/Emoji/pull/824) ([vanniktech](https://github.com/vanniktech))
- Breaking: Use EmojiCategory\#categoryNames Map\<String, String\> instead of Android Resource for proper Multiplatform support. [\#823](https://github.com/vanniktech/Emoji/pull/823) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move EmojiManager into commonMain. [\#822](https://github.com/vanniktech/Emoji/pull/822) ([vanniktech](https://github.com/vanniktech))
- Breaking: Extract EmojiManager\#replaceWithImages as an extension function. [\#821](https://github.com/vanniktech/Emoji/pull/821) ([vanniktech](https://github.com/vanniktech))
- Move EmojiDrawableProvider\#release\(\) into EmojiProvider. [\#820](https://github.com/vanniktech/Emoji/pull/820) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move EmojiCategory into commonMain. [\#819](https://github.com/vanniktech/Emoji/pull/819) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Add JVM target. [\#818](https://github.com/vanniktech/Emoji/pull/818) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move EmojiProvider into commonMain. [\#817](https://github.com/vanniktech/Emoji/pull/817) ([vanniktech](https://github.com/vanniktech))
- Breaking: Rename EmojiProvider\#destroy\(\) to EmojiProvider\#release\(\). [\#816](https://github.com/vanniktech/Emoji/pull/816) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move Chunks of generated Categories into commonMain. [\#815](https://github.com/vanniktech/Emoji/pull/815) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move NoSearchEmoji into commonMain. [\#814](https://github.com/vanniktech/Emoji/pull/814) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move SearchEmoji into commonMain. [\#813](https://github.com/vanniktech/Emoji/pull/813) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move EmptyEmojiTrait into commonMain. [\#812](https://github.com/vanniktech/Emoji/pull/812) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move EmojiInformation into commonMain. [\#811](https://github.com/vanniktech/Emoji/pull/811) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move NoRecentEmoji into commonMain. [\#810](https://github.com/vanniktech/Emoji/pull/810) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move NoVariantEmoji into commonMain. [\#809](https://github.com/vanniktech/Emoji/pull/809) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move EmojiTraitable into commonMain. [\#808](https://github.com/vanniktech/Emoji/pull/808) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move RecentEmoji into commonMain. [\#807](https://github.com/vanniktech/Emoji/pull/807) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move SearchEmojiResult into commonMain. [\#806](https://github.com/vanniktech/Emoji/pull/806) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move EmojiTrait into commonMain. [\#805](https://github.com/vanniktech/Emoji/pull/805) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move VariantEmoji into commonMain. [\#804](https://github.com/vanniktech/Emoji/pull/804) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: EmojiAndroidCategory interface to start preparing EmojiCategory for commonMain. [\#803](https://github.com/vanniktech/Emoji/pull/803) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move EmojiRange into commonMain. [\#802](https://github.com/vanniktech/Emoji/pull/802) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move Emoji implementations into commonMain. [\#801](https://github.com/vanniktech/Emoji/pull/801) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move EmojiTrait into commonMain. [\#800](https://github.com/vanniktech/Emoji/pull/800) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Move Emoji interface to commonMain. [\#799](https://github.com/vanniktech/Emoji/pull/799) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: EmojiDrawableProvider interface to start preparing Emoji for commonMain. [\#798](https://github.com/vanniktech/Emoji/pull/798) ([vanniktech](https://github.com/vanniktech))
- Generator: Update directories & don't delete values directory. [\#797](https://github.com/vanniktech/Emoji/pull/797) ([vanniktech](https://github.com/vanniktech))
- Multiplatform: Parcelize support for Android. [\#796](https://github.com/vanniktech/Emoji/pull/796) ([vanniktech](https://github.com/vanniktech))
- Tests: Do full exception format logging. [\#795](https://github.com/vanniktech/Emoji/pull/795) ([vanniktech](https://github.com/vanniktech))
- Bug fix: Recent Emojis were not removing last recent Emoji. [\#794](https://github.com/vanniktech/Emoji/pull/794) ([vanniktech](https://github.com/vanniktech))
- Start with Kotlin Multiplatform \(android only for now\) [\#780](https://github.com/vanniktech/Emoji/pull/780) ([vanniktech](https://github.com/vanniktech))

Version 0.13.0 *(2022-05-23)*
-----------------------------

- Use EmojiTheming on internal ViewPager. [\#793](https://github.com/vanniktech/Emoji/pull/793) ([vanniktech](https://github.com/vanniktech))
- Sample: Showcase theming + fix edge case when searching for an Emoji which hasn't been downloaded and shown as a glyph. [\#792](https://github.com/vanniktech/Emoji/pull/792) ([vanniktech](https://github.com/vanniktech))
- Use EmojiTheming on internal RecyclerViews. [\#791](https://github.com/vanniktech/Emoji/pull/791) ([vanniktech](https://github.com/vanniktech))
- Add Dependency Guard plugin. [\#790](https://github.com/vanniktech/Emoji/pull/790) ([vanniktech](https://github.com/vanniktech))

Version 0.12.0 *(2022-05-16)*
-----------------------------

- EmojiSearchDialog: Fix crash when typing in swim \(each letter one by one with some delay\) [\#787](https://github.com/vanniktech/Emoji/pull/787) ([vanniktech](https://github.com/vanniktech))
- Improve compatibility with Java Code. [\#786](https://github.com/vanniktech/Emoji/pull/786) ([vanniktech](https://github.com/vanniktech))

Version 0.11.0 *(2022-05-11)*
-----------------------------

- Mark almost all resources as private. [\#783](https://github.com/vanniktech/Emoji/pull/783) ([vanniktech](https://github.com/vanniktech))
- Translate category names into German. [\#782](https://github.com/vanniktech/Emoji/pull/782) ([vanniktech](https://github.com/vanniktech))
- Stricter android resource prefixes. [\#781](https://github.com/vanniktech/Emoji/pull/781) ([vanniktech](https://github.com/vanniktech))
- EmojiRange: Replace start & end with IntRange. [\#779](https://github.com/vanniktech/Emoji/pull/779) ([vanniktech](https://github.com/vanniktech))
- EmojiPopup: Hide EmojiResultReceiver.Receiver usage. [\#778](https://github.com/vanniktech/Emoji/pull/778) ([vanniktech](https://github.com/vanniktech))
- EmojiReplacer interface is now a fun interface. [\#777](https://github.com/vanniktech/Emoji/pull/777) ([vanniktech](https://github.com/vanniktech))
- Ignore whitespace when using force single emoji & searching for an Emoji. [\#776](https://github.com/vanniktech/Emoji/pull/776) ([vanniktech](https://github.com/vanniktech))
- EditText\#input gets addWhitespace parameter. [\#775](https://github.com/vanniktech/Emoji/pull/775) ([vanniktech](https://github.com/vanniktech))
- Use Emojis name when calling extension functions from Java. [\#774](https://github.com/vanniktech/Emoji/pull/774) ([vanniktech](https://github.com/vanniktech))
- Remove deprecated methods. [\#773](https://github.com/vanniktech/Emoji/pull/773) ([vanniktech](https://github.com/vanniktech))
- Add metalava. [\#680](https://github.com/vanniktech/Emoji/pull/680) ([vanniktech](https://github.com/vanniktech))

Version 0.10.0 *(2022-05-11)*
-----------------------------

This release has quite a few internal and external changes. Most noticeably, everything has been converted to Kotlin. Please also have a look at the sample app for the breaking changes as well as the new features.

Big thanks to @rubengees for updating to emojis 14.0 & @mario for reviews!

- Update dependencies. [\#772](https://github.com/vanniktech/Emoji/pull/772) ([vanniktech](https://github.com/vanniktech))
- Use android.namespace. [\#771](https://github.com/vanniktech/Emoji/pull/771) ([vanniktech](https://github.com/vanniktech))
- Use Gradle's Version Catalog. [\#770](https://github.com/vanniktech/Emoji/pull/770) ([vanniktech](https://github.com/vanniktech))
- Disable PMD as we're Kotlin only now. [\#769](https://github.com/vanniktech/Emoji/pull/769) ([vanniktech](https://github.com/vanniktech))
- Move Emoji interface to com.vanniktech.emoji [\#768](https://github.com/vanniktech/Emoji/pull/768) ([vanniktech](https://github.com/vanniktech))
- EmojiCategory now wants List\<Emoji\> and also ditch CategoryUtils. [\#767](https://github.com/vanniktech/Emoji/pull/767) ([vanniktech](https://github.com/vanniktech))
- Use Kotlin's sortWith instead of java.util.Comparator. [\#766](https://github.com/vanniktech/Emoji/pull/766) ([vanniktech](https://github.com/vanniktech))
- EmojiManager\#verifyInstalled is now internal. [\#765](https://github.com/vanniktech/Emoji/pull/765) ([vanniktech](https://github.com/vanniktech))
- Move EmojiCategory interface to com.vanniktech.emoji [\#764](https://github.com/vanniktech/Emoji/pull/764) ([vanniktech](https://github.com/vanniktech))
- Use Kotlin's Regex to search for Emojis. [\#763](https://github.com/vanniktech/Emoji/pull/763) ([vanniktech](https://github.com/vanniktech))
- Inline EmojiEditable interface and provide extension functions. [\#762](https://github.com/vanniktech/Emoji/pull/762) ([vanniktech](https://github.com/vanniktech))
- Generate toString\(\)/equals\(\)/hashcode\(\) for Emoji implementations and remove data keyword. [\#761](https://github.com/vanniktech/Emoji/pull/761) ([vanniktech](https://github.com/vanniktech))
- EmojiVariantPopup: Make scrollable. [\#760](https://github.com/vanniktech/Emoji/pull/760) ([vanniktech](https://github.com/vanniktech))
- Reduce Java footprint by using Kotlin equivalents. [\#759](https://github.com/vanniktech/Emoji/pull/759) ([vanniktech](https://github.com/vanniktech))
- Emoji classes are now parcelable. [\#757](https://github.com/vanniktech/Emoji/pull/757) ([vanniktech](https://github.com/vanniktech))
- Let generated Emoji classes be data class. [\#756](https://github.com/vanniktech/Emoji/pull/756) ([vanniktech](https://github.com/vanniktech))
- Emoji generate directly unicode instead of internal code points. [\#755](https://github.com/vanniktech/Emoji/pull/755) ([vanniktech](https://github.com/vanniktech))
- Emoji shortcodes is now a List\<String\> [\#754](https://github.com/vanniktech/Emoji/pull/754) ([vanniktech](https://github.com/vanniktech))
- Generate GoogleCompatEmoji class. [\#753](https://github.com/vanniktech/Emoji/pull/753) ([vanniktech](https://github.com/vanniktech))
- Emoji variants is now a List\<Emoji\> [\#752](https://github.com/vanniktech/Emoji/pull/752) ([vanniktech](https://github.com/vanniktech))
- Emoji is now an interface. [\#751](https://github.com/vanniktech/Emoji/pull/751) ([vanniktech](https://github.com/vanniktech))
- Recent package which contains everything for recenting. [\#750](https://github.com/vanniktech/Emoji/pull/750) ([vanniktech](https://github.com/vanniktech))
- Variant package which contains everything for varianting. [\#749](https://github.com/vanniktech/Emoji/pull/749) ([vanniktech](https://github.com/vanniktech))
- Search package which contains everything for searching. [\#748](https://github.com/vanniktech/Emoji/pull/748) ([vanniktech](https://github.com/vanniktech))
- Remove Emoji\#length. [\#747](https://github.com/vanniktech/Emoji/pull/747) ([vanniktech](https://github.com/vanniktech))
- Inline Emoji\#hasVariants. [\#746](https://github.com/vanniktech/Emoji/pull/746) ([vanniktech](https://github.com/vanniktech))
- Open MaterialEmojiLayoutFactory. [\#745](https://github.com/vanniktech/Emoji/pull/745) ([vanniktech](https://github.com/vanniktech))
- Move internals into internal package. [\#744](https://github.com/vanniktech/Emoji/pull/744) ([vanniktech](https://github.com/vanniktech))
- NoVariantEmoji in case you don't want variant selection. [\#743](https://github.com/vanniktech/Emoji/pull/743) ([vanniktech](https://github.com/vanniktech))
- Emoji class is now abstract. [\#742](https://github.com/vanniktech/Emoji/pull/742) ([vanniktech](https://github.com/vanniktech))
- Remove Emoji\#resource. [\#741](https://github.com/vanniktech/Emoji/pull/741) ([vanniktech](https://github.com/vanniktech))
- Emoji class only use one constructor. [\#740](https://github.com/vanniktech/Emoji/pull/740) ([vanniktech](https://github.com/vanniktech))
- Emoji remove constructor with single code point. [\#739](https://github.com/vanniktech/Emoji/pull/739) ([vanniktech](https://github.com/vanniktech))
- Use Point instead of CacheKey. [\#738](https://github.com/vanniktech/Emoji/pull/738) ([vanniktech](https://github.com/vanniktech))
- Emoji search also append whitespace and auto close after clicking an entry. [\#737](https://github.com/vanniktech/Emoji/pull/737) ([vanniktech](https://github.com/vanniktech))
- Update copyrights. [\#736](https://github.com/vanniktech/Emoji/pull/736) ([vanniktech](https://github.com/vanniktech))
- emoji-material: Use defStyleAttr constructor with default value. [\#735](https://github.com/vanniktech/Emoji/pull/735) ([vanniktech](https://github.com/vanniktech))
- Allow subclasses from EmojiDisplayable & EmojiEditable components. [\#734](https://github.com/vanniktech/Emoji/pull/734) ([vanniktech](https://github.com/vanniktech))
- Use base plugin for publishing. [\#733](https://github.com/vanniktech/Emoji/pull/733) ([vanniktech](https://github.com/vanniktech))
- Sample: Face lift and regenerate screenshots. [\#732](https://github.com/vanniktech/Emoji/pull/732) ([vanniktech](https://github.com/vanniktech))
- Deprecate EmojiPopup.Builder in favor of EmojiPopup constructor. [\#731](https://github.com/vanniktech/Emoji/pull/731) ([vanniktech](https://github.com/vanniktech))
- Hide more APIs in emoji module. [\#730](https://github.com/vanniktech/Emoji/pull/730) ([vanniktech](https://github.com/vanniktech))
- Refactoring: Move SearchInPlaceTrait into traits package. [\#729](https://github.com/vanniktech/Emoji/pull/729) ([vanniktech](https://github.com/vanniktech))
- Drop Java Support \(indirectly by removing @JvmField annotations\) [\#728](https://github.com/vanniktech/Emoji/pull/728) ([vanniktech](https://github.com/vanniktech))
- Lint: Check everything. [\#727](https://github.com/vanniktech/Emoji/pull/727) ([vanniktech](https://github.com/vanniktech))
- Let build fail if there are any warnings. [\#726](https://github.com/vanniktech/Emoji/pull/726) ([vanniktech](https://github.com/vanniktech))
- emoji-google is now Kotlin only. [\#725](https://github.com/vanniktech/Emoji/pull/725) ([vanniktech](https://github.com/vanniktech))
- emoji-facebook is now Kotlin only. [\#724](https://github.com/vanniktech/Emoji/pull/724) ([vanniktech](https://github.com/vanniktech))
- emoji-ios is now Kotlin only. [\#723](https://github.com/vanniktech/Emoji/pull/723) ([vanniktech](https://github.com/vanniktech))
- emoji-google-compat is now Kotlin only. [\#722](https://github.com/vanniktech/Emoji/pull/722) ([vanniktech](https://github.com/vanniktech))
- emoji-twitter is now Kotlin only. [\#721](https://github.com/vanniktech/Emoji/pull/721) ([vanniktech](https://github.com/vanniktech))
- Use Vector Drawable for recent icon. [\#720](https://github.com/vanniktech/Emoji/pull/720) ([vanniktech](https://github.com/vanniktech))
- Avoid generating accessor method. [\#719](https://github.com/vanniktech/Emoji/pull/719) ([vanniktech](https://github.com/vanniktech))
- Sample: Use Timber for logging. [\#718](https://github.com/vanniktech/Emoji/pull/718) ([vanniktech](https://github.com/vanniktech))
- Remove deprecated systemWindowInsetBottom usage. [\#717](https://github.com/vanniktech/Emoji/pull/717) ([vanniktech](https://github.com/vanniktech))
- Hide more APIs in each provider module. [\#716](https://github.com/vanniktech/Emoji/pull/716) ([vanniktech](https://github.com/vanniktech))
- Enhance generated code. [\#715](https://github.com/vanniktech/Emoji/pull/715) ([vanniktech](https://github.com/vanniktech))
- Remove deprecated stableInsetBottom usage. [\#714](https://github.com/vanniktech/Emoji/pull/714) ([vanniktech](https://github.com/vanniktech))
- Make Utils internal and move EditText & TextView extensions into their own file. [\#713](https://github.com/vanniktech/Emoji/pull/713) ([vanniktech](https://github.com/vanniktech))
- Convert GoogleCompatEmoji class to Kotlin. [\#712](https://github.com/vanniktech/Emoji/pull/712) ([vanniktech](https://github.com/vanniktech))
- Generate CategoryChunk in Kotlin. [\#711](https://github.com/vanniktech/Emoji/pull/711) ([vanniktech](https://github.com/vanniktech))
- Generate CategoryUtils class in Kotlin. [\#710](https://github.com/vanniktech/Emoji/pull/710) ([vanniktech](https://github.com/vanniktech))
- Generate Emoji Class in Kotlin. [\#709](https://github.com/vanniktech/Emoji/pull/709) ([vanniktech](https://github.com/vanniktech))
- Generate GoogleCompatEmojiProvider as Kotlin. [\#708](https://github.com/vanniktech/Emoji/pull/708) ([vanniktech](https://github.com/vanniktech))
- Generate all EmojiProvider implementations in Kotlin. [\#707](https://github.com/vanniktech/Emoji/pull/707) ([vanniktech](https://github.com/vanniktech))
- Public TextView\#init. [\#706](https://github.com/vanniktech/Emoji/pull/706) ([vanniktech](https://github.com/vanniktech))
- Generate all EmojiCategory implementations in Kotlin. [\#705](https://github.com/vanniktech/Emoji/pull/705) ([vanniktech](https://github.com/vanniktech))
- Inline Utils\#asListWithoutDuplicates. [\#704](https://github.com/vanniktech/Emoji/pull/704) ([vanniktech](https://github.com/vanniktech))
- Improve updating documentation. [\#703](https://github.com/vanniktech/Emoji/pull/703) ([vanniktech](https://github.com/vanniktech))
- EmojiEditable remove backspace & input function and instead provide public extension functions for all EditText. [\#702](https://github.com/vanniktech/Emoji/pull/702) ([vanniktech](https://github.com/vanniktech))
- Convert GoogleCompatEmojiDrawable class to Kotlin. [\#701](https://github.com/vanniktech/Emoji/pull/701) ([vanniktech](https://github.com/vanniktech))
- EmojiReplacer fallback can be optional. [\#700](https://github.com/vanniktech/Emoji/pull/700) ([vanniktech](https://github.com/vanniktech))
- Public EditText.inputEmoji. [\#699](https://github.com/vanniktech/Emoji/pull/699) ([vanniktech](https://github.com/vanniktech))
- Remove EmojiPopup\#setPopupWindowHeight [\#698](https://github.com/vanniktech/Emoji/pull/698) ([vanniktech](https://github.com/vanniktech))
- emoji is now Kotlin only. [\#697](https://github.com/vanniktech/Emoji/pull/697) ([vanniktech](https://github.com/vanniktech))
- Unify lint.xml files. [\#696](https://github.com/vanniktech/Emoji/pull/696) ([vanniktech](https://github.com/vanniktech))
- Public EditText.dispatchBackspace. [\#695](https://github.com/vanniktech/Emoji/pull/695) ([vanniktech](https://github.com/vanniktech))
- Convert Utils class to Kotlin. [\#694](https://github.com/vanniktech/Emoji/pull/694) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiPopup class to Kotlin. [\#693](https://github.com/vanniktech/Emoji/pull/693) ([vanniktech](https://github.com/vanniktech))
- Update emojis to version 14.0 [\#691](https://github.com/vanniktech/Emoji/pull/691) ([rubengees](https://github.com/rubengees))
- Convert EmojiManager class to Kotlin. [\#689](https://github.com/vanniktech/Emoji/pull/689) ([vanniktech](https://github.com/vanniktech))
- Inline EmojiInput interface into EmojiEditable so that all UI components benefit from traits. [\#688](https://github.com/vanniktech/Emoji/pull/688) ([vanniktech](https://github.com/vanniktech))
- EmojiEditable: Emoji Input is always non-null. [\#687](https://github.com/vanniktech/Emoji/pull/687) ([vanniktech](https://github.com/vanniktech))
- EmojiEditable: Consistent isEditMode handling. [\#686](https://github.com/vanniktech/Emoji/pull/686) ([vanniktech](https://github.com/vanniktech))
- Convert Emoji class to Kotlin. [\#685](https://github.com/vanniktech/Emoji/pull/685) ([vanniktech](https://github.com/vanniktech))
- Refactoring: Move Kotlin functions to top level and deprecate old mechanisms. [\#684](https://github.com/vanniktech/Emoji/pull/684) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiUtils class to Kotlin. [\#683](https://github.com/vanniktech/Emoji/pull/683) ([vanniktech](https://github.com/vanniktech))
- app is now Kotlin only. [\#682](https://github.com/vanniktech/Emoji/pull/682) ([vanniktech](https://github.com/vanniktech))
- emoji-material is now Kotlin only. [\#681](https://github.com/vanniktech/Emoji/pull/681) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiMultiAutoCompleteTextView class to Kotlin. [\#679](https://github.com/vanniktech/Emoji/pull/679) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiEditText class to Kotlin. [\#678](https://github.com/vanniktech/Emoji/pull/678) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiMaterialCheckBox class to Kotlin. [\#677](https://github.com/vanniktech/Emoji/pull/677) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiMaterialRadioButton class to Kotlin. [\#676](https://github.com/vanniktech/Emoji/pull/676) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiTextInputEditText class to Kotlin. [\#675](https://github.com/vanniktech/Emoji/pull/675) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiMaterialButton class to Kotlin. [\#674](https://github.com/vanniktech/Emoji/pull/674) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiButton class to Kotlin. [\#673](https://github.com/vanniktech/Emoji/pull/673) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiAutoCompleteTextView class to Kotlin. [\#672](https://github.com/vanniktech/Emoji/pull/672) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiCheckbox class to Kotlin. [\#671](https://github.com/vanniktech/Emoji/pull/671) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiTextView class to Kotlin. [\#670](https://github.com/vanniktech/Emoji/pull/670) ([vanniktech](https://github.com/vanniktech))
- Refactoring: Replace disableKeyboardInput with installDisableKeyboardInput and handle more cases. [\#669](https://github.com/vanniktech/Emoji/pull/669) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiView class to Kotlin. [\#668](https://github.com/vanniktech/Emoji/pull/668) ([vanniktech](https://github.com/vanniktech))
- ScreenshotsTest: Use DemoModeRule for better screenshots. [\#667](https://github.com/vanniktech/Emoji/pull/667) ([vanniktech](https://github.com/vanniktech))
- Convert ScreenshotsTest class to Kotlin. [\#666](https://github.com/vanniktech/Emoji/pull/666) ([vanniktech](https://github.com/vanniktech))
- Refactoring: Use espresso utils, get rid of assertj & more. [\#665](https://github.com/vanniktech/Emoji/pull/665) ([vanniktech](https://github.com/vanniktech))
- Convert MainActivity class to Kotlin. [\#664](https://github.com/vanniktech/Emoji/pull/664) ([vanniktech](https://github.com/vanniktech))
- Convert MainDialog class to Kotlin. [\#663](https://github.com/vanniktech/Emoji/pull/663) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiPagerAdapter class to Kotlin. [\#662](https://github.com/vanniktech/Emoji/pull/662) ([vanniktech](https://github.com/vanniktech))
- Bring back AsyncTask as Executors sometimes don't work. [\#661](https://github.com/vanniktech/Emoji/pull/661) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiVariantPopup class to Kotlin. [\#660](https://github.com/vanniktech/Emoji/pull/660) ([vanniktech](https://github.com/vanniktech))
- Refactoring: Use fun interface to functional interfaces. [\#659](https://github.com/vanniktech/Emoji/pull/659) ([vanniktech](https://github.com/vanniktech))
- Convert PageTransformer class to Kotlin. [\#658](https://github.com/vanniktech/Emoji/pull/658) ([vanniktech](https://github.com/vanniktech))
- Convert CustomViewActivity class to Kotlin. [\#657](https://github.com/vanniktech/Emoji/pull/657) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiApplication class to Kotlin. [\#656](https://github.com/vanniktech/Emoji/pull/656) ([vanniktech](https://github.com/vanniktech))
- Convert ChatAdapter class to Kotlin. [\#655](https://github.com/vanniktech/Emoji/pull/655) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiImageView class to Kotlin. [\#654](https://github.com/vanniktech/Emoji/pull/654) ([vanniktech](https://github.com/vanniktech))
- Convert RecentEmojiManager class to Kotlin. [\#653](https://github.com/vanniktech/Emoji/pull/653) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiSpan class to Kotlin. [\#652](https://github.com/vanniktech/Emoji/pull/652) ([vanniktech](https://github.com/vanniktech))
- Convert GridView classes to Kotlin. [\#651](https://github.com/vanniktech/Emoji/pull/651) ([vanniktech](https://github.com/vanniktech))
- Convert MaterialEmojiLayoutFactory class to Kotlin. [\#650](https://github.com/vanniktech/Emoji/pull/650) ([vanniktech](https://github.com/vanniktech))
- Refactoring: Extract inset variables. [\#649](https://github.com/vanniktech/Emoji/pull/649) ([vanniktech](https://github.com/vanniktech))
- Refactoring: Move MaximalNumberOfEmojisInputFilter into inputfilters package. [\#648](https://github.com/vanniktech/Emoji/pull/648) ([vanniktech](https://github.com/vanniktech))
- Remove deprecated AsyncTask with Executors. [\#647](https://github.com/vanniktech/Emoji/pull/647) ([vanniktech](https://github.com/vanniktech))
- Convert MaximalNumberOfEmojisInputFilter class to Kotlin. [\#646](https://github.com/vanniktech/Emoji/pull/646) ([vanniktech](https://github.com/vanniktech))
- Convert VariantEmojiManager class to Kotlin. [\#645](https://github.com/vanniktech/Emoji/pull/645) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiArrayAdapter class to Kotlin. [\#644](https://github.com/vanniktech/Emoji/pull/644) ([vanniktech](https://github.com/vanniktech))
- Refactorign: Move OnlyEmojisInputFilter into inputfilters package. [\#643](https://github.com/vanniktech/Emoji/pull/643) ([vanniktech](https://github.com/vanniktech))
- UI Components: Properly init them using their own StylableRes. [\#642](https://github.com/vanniktech/Emoji/pull/642) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiDisplayable interface to Kotlin. [\#641](https://github.com/vanniktech/Emoji/pull/641) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiEditable interface to Kotlin. [\#640](https://github.com/vanniktech/Emoji/pull/640) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiResultReceiver class to Kotlin. [\#639](https://github.com/vanniktech/Emoji/pull/639) ([vanniktech](https://github.com/vanniktech))
- Convert all Tests to Kotlin. [\#638](https://github.com/vanniktech/Emoji/pull/638) ([vanniktech](https://github.com/vanniktech))
- Refactoring: Start moving most of the Trait infrastructure into its own package. [\#637](https://github.com/vanniktech/Emoji/pull/637) ([vanniktech](https://github.com/vanniktech))
- Rename forceSingleEmoji\(\) to installForceSingleEmoji\(\) and leverage EmojiTrait mechanism. [\#636](https://github.com/vanniktech/Emoji/pull/636) ([vanniktech](https://github.com/vanniktech))
- Convert SingleEmojiTrait class to Kotlin. [\#635](https://github.com/vanniktech/Emoji/pull/635) ([vanniktech](https://github.com/vanniktech))
- SearchInPlaceTrait: Allow searching for emojis. [\#634](https://github.com/vanniktech/Emoji/pull/634) ([vanniktech](https://github.com/vanniktech))
- Search Dialog: Tint Edit Text cursor and handles correctly. [\#633](https://github.com/vanniktech/Emoji/pull/633) ([vanniktech](https://github.com/vanniktech))
- Emoji Search: Highlight which part of the shortcode matches the query. [\#632](https://github.com/vanniktech/Emoji/pull/632) ([vanniktech](https://github.com/vanniktech))
- Search RecyclerView limit height to 300dp. [\#631](https://github.com/vanniktech/Emoji/pull/631) ([vanniktech](https://github.com/vanniktech))
- In place dialog when typing : in an EditText constructed from EmojiPopup. [\#630](https://github.com/vanniktech/Emoji/pull/630) ([vanniktech](https://github.com/vanniktech))
- Sample: Showcase EmojiView on it's own without a keyboard. [\#629](https://github.com/vanniktech/Emoji/pull/629) ([vanniktech](https://github.com/vanniktech))
- Internal: EmojiView rearrange a few things and move EmojiPopUp\#dismiss behavior into EmojiView\#tearDown. [\#628](https://github.com/vanniktech/Emoji/pull/628) ([vanniktech](https://github.com/vanniktech))
- Internal: EmojiView prepare for public usage. [\#627](https://github.com/vanniktech/Emoji/pull/627) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiForceable interface to Kotlin. [\#626](https://github.com/vanniktech/Emoji/pull/626) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiProvider interface to Kotlin. [\#625](https://github.com/vanniktech/Emoji/pull/625) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiInformation class to Kotlin. [\#624](https://github.com/vanniktech/Emoji/pull/624) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiLayoutFactory class to Kotlin. [\#623](https://github.com/vanniktech/Emoji/pull/623) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiRange class to Kotlin. [\#622](https://github.com/vanniktech/Emoji/pull/622) ([vanniktech](https://github.com/vanniktech))
- Remove View parameter from OnEmojiBackspaceClickListener interface. [\#621](https://github.com/vanniktech/Emoji/pull/621) ([vanniktech](https://github.com/vanniktech))
- Remove EmojiImageView parameter from OnEmojiClickListener interface. [\#620](https://github.com/vanniktech/Emoji/pull/620) ([vanniktech](https://github.com/vanniktech))
- Convert OnSoftKeyboardOpenListener interface to Kotlin. [\#619](https://github.com/vanniktech/Emoji/pull/619) ([vanniktech](https://github.com/vanniktech))
- Convert OnSoftKeyboardCloseListener interface to Kotlin. [\#618](https://github.com/vanniktech/Emoji/pull/618) ([vanniktech](https://github.com/vanniktech))
- Convert OnEmojiPopupShownListener interface to Kotlin. [\#617](https://github.com/vanniktech/Emoji/pull/617) ([vanniktech](https://github.com/vanniktech))
- Convert OnEmojiPopupDismissListener interface to Kotlin. [\#616](https://github.com/vanniktech/Emoji/pull/616) ([vanniktech](https://github.com/vanniktech))
- Convert OnEmojiLongClickListener interface to Kotlin. [\#615](https://github.com/vanniktech/Emoji/pull/615) ([vanniktech](https://github.com/vanniktech))
- Convert OnEmojiClickListener interface to Kotlin. [\#614](https://github.com/vanniktech/Emoji/pull/614) ([vanniktech](https://github.com/vanniktech))
- Convert OnEmojiBackspaceClickListener interface to Kotlin. [\#613](https://github.com/vanniktech/Emoji/pull/613) ([vanniktech](https://github.com/vanniktech))
- Convert CacheKey class to Kotlin. [\#612](https://github.com/vanniktech/Emoji/pull/612) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiCategory interface to Kotlin. [\#611](https://github.com/vanniktech/Emoji/pull/611) ([vanniktech](https://github.com/vanniktech))
- Convert RepeatListener class to Kotlin. [\#610](https://github.com/vanniktech/Emoji/pull/610) ([vanniktech](https://github.com/vanniktech))
- NoSearchEmoji which allows you to hide the search feature. [\#609](https://github.com/vanniktech/Emoji/pull/609) ([vanniktech](https://github.com/vanniktech))
- Convert OnlyEmojisInputFilter class to Kotlin. [\#608](https://github.com/vanniktech/Emoji/pull/608) ([vanniktech](https://github.com/vanniktech))
- Convert NoRecentEmoji class to Kotlin. [\#607](https://github.com/vanniktech/Emoji/pull/607) ([vanniktech](https://github.com/vanniktech))
- Convert EmojiReplacer interface to Kotlin. [\#606](https://github.com/vanniktech/Emoji/pull/606) ([vanniktech](https://github.com/vanniktech))
- Convert VariantEmoji interface to Kotlin. [\#605](https://github.com/vanniktech/Emoji/pull/605) ([vanniktech](https://github.com/vanniktech))
- Convert RecentEmoji interface to Kotlin. [\#604](https://github.com/vanniktech/Emoji/pull/604) ([vanniktech](https://github.com/vanniktech))
- SearchEmoji interface which allows customizing the search algorithm. [\#603](https://github.com/vanniktech/Emoji/pull/603) ([vanniktech](https://github.com/vanniktech))
- Internal: EmojiView hide backspace when EditText is null and no Backspace listener is set. [\#602](https://github.com/vanniktech/Emoji/pull/602) ([vanniktech](https://github.com/vanniktech))
- Internal: EmojiView EditText is optional. [\#601](https://github.com/vanniktech/Emoji/pull/601) ([vanniktech](https://github.com/vanniktech))
- Internal: EmojiView handles backspace on it's own. [\#600](https://github.com/vanniktech/Emoji/pull/600) ([vanniktech](https://github.com/vanniktech))
- Delete JaCoCo as CodeCov has been turned off for quite a while. [\#599](https://github.com/vanniktech/Emoji/pull/599) ([vanniktech](https://github.com/vanniktech))
- Sample: Move custom view sample from Menu to Button. [\#598](https://github.com/vanniktech/Emoji/pull/598) ([vanniktech](https://github.com/vanniktech))
- Sample: Move dialog sample from Menu to Button to also showcase MaterialEmojiLayoutFactory. [\#597](https://github.com/vanniktech/Emoji/pull/597) ([vanniktech](https://github.com/vanniktech))
- Sample: Move option to switch between Provider from Menu to Button. [\#596](https://github.com/vanniktech/Emoji/pull/596) ([vanniktech](https://github.com/vanniktech))
- Sample: Nuke MainActivityAutoCompleteTextView & MainActivityMultiAutoCompleteTextView \(unused and only differ in one XML line, the View for entering Text + Emojis\) [\#595](https://github.com/vanniktech/Emoji/pull/595) ([vanniktech](https://github.com/vanniktech))
- Internal: EmojiView gets setUp method. [\#594](https://github.com/vanniktech/Emoji/pull/594) ([vanniktech](https://github.com/vanniktech))
- Internal: EmojiView handles clicking on it's own. [\#593](https://github.com/vanniktech/Emoji/pull/593) ([vanniktech](https://github.com/vanniktech))
- Internal: EmojiView is taking each dependency instead of EmojiPopup.Builder. [\#592](https://github.com/vanniktech/Emoji/pull/592) ([vanniktech](https://github.com/vanniktech))
- Sample: Fix Custom View and explain what it's actually doing. [\#591](https://github.com/vanniktech/Emoji/pull/591) ([vanniktech](https://github.com/vanniktech))
- Make EmojiResultReceiver private. [\#590](https://github.com/vanniktech/Emoji/pull/590) ([vanniktech](https://github.com/vanniktech))
- Internal: EmojiView is now holding the EmojiVariantPopup. [\#589](https://github.com/vanniktech/Emoji/pull/589) ([vanniktech](https://github.com/vanniktech))
- Fix ANR when persisting Recent-& Variant emojis. [\#588](https://github.com/vanniktech/Emoji/pull/588) ([vanniktech](https://github.com/vanniktech))
- Remove library config file. [\#587](https://github.com/vanniktech/Emoji/pull/587) ([vanniktech](https://github.com/vanniktech))
- Update generator for new data source version [\#586](https://github.com/vanniktech/Emoji/pull/586) ([rubengees](https://github.com/rubengees))
- Sample: Follow system setting for dark mode & fix dialog. [\#585](https://github.com/vanniktech/Emoji/pull/585) ([vanniktech](https://github.com/vanniktech))
- Unified EmojiTheming which has all of the colors for each Emoji UI component. [\#584](https://github.com/vanniktech/Emoji/pull/584) ([vanniktech](https://github.com/vanniktech))
- Rename: color/emoji\_divider to color/emoji\_divider\_color and attr/emojiDivider to attr/emojiDividerColor. [\#583](https://github.com/vanniktech/Emoji/pull/583) ([vanniktech](https://github.com/vanniktech))
- Rename: color/emoji\_background to color/emoji\_background\_color and attr/emojiBackground to attr/emojiBackgroundColor. [\#582](https://github.com/vanniktech/Emoji/pull/582) ([vanniktech](https://github.com/vanniktech))
- Rename: color/emoji\_icons to color/emoji\_primary\_color and attr/emojiIcons to attr/emojiPrimaryColor. [\#581](https://github.com/vanniktech/Emoji/pull/581) ([vanniktech](https://github.com/vanniktech))
- Update ktlint to 0.45.2 & disable Detekt. [\#580](https://github.com/vanniktech/Emoji/pull/580) ([vanniktech](https://github.com/vanniktech))
- Inline emoji-kotlin into emoji module and remove emoji-kotlin module. [\#579](https://github.com/vanniktech/Emoji/pull/579) ([vanniktech](https://github.com/vanniktech))
- Add failing test for isOnlyEmojis\(\) [\#578](https://github.com/vanniktech/Emoji/pull/578) ([vanniktech](https://github.com/vanniktech))
- Stop using Gradle's subproject. [\#577](https://github.com/vanniktech/Emoji/pull/577) ([vanniktech](https://github.com/vanniktech))
- Update LeakCanary to 2.9.1 [\#576](https://github.com/vanniktech/Emoji/pull/576) ([vanniktech](https://github.com/vanniktech))
- Update Kotlin to 1.6.21 [\#575](https://github.com/vanniktech/Emoji/pull/575) ([vanniktech](https://github.com/vanniktech))
- Editorconfig file. [\#574](https://github.com/vanniktech/Emoji/pull/574) ([vanniktech](https://github.com/vanniktech))
- Add EmojiTextInputEditText. [\#573](https://github.com/vanniktech/Emoji/pull/573) ([vanniktech](https://github.com/vanniktech))
- Emoji search icon next to categories, which allows to search emoji by name \(english names only\) [\#563](https://github.com/vanniktech/Emoji/pull/563) ([vanniktech](https://github.com/vanniktech))

Version 0.9.0 *(2022-04-09)*
----------------------------

- Fix Memory Leak with setOnApplyWindowInsetsListener. [\#569](https://github.com/vanniktech/Emoji/pull/569) ([vanniktech](https://github.com/vanniktech))
- Keep file to keep sheets which are accessed through reflection. [\#562](https://github.com/vanniktech/Emoji/pull/562) ([vanniktech](https://github.com/vanniktech))
- Update dependencies. [\#561](https://github.com/vanniktech/Emoji/pull/561) ([vanniktech](https://github.com/vanniktech))

Version 0.8.0 *(2021-09-27)*
----------------------------

- NoRecentEmoji implementation. Fixes \#477 [\#510](https://github.com/vanniktech/Emoji/pull/510) ([vanniktech](https://github.com/vanniktech))
- Fix Memory Leak with OnAttachStateChangeListener. [\#508](https://github.com/vanniktech/Emoji/pull/508) ([vanniktech](https://github.com/vanniktech))
- Update gradle-maven-publish-plugin to 0.16.0 [\#504](https://github.com/vanniktech/Emoji/pull/504) ([vanniktech](https://github.com/vanniktech))
- Switch to GitHub workflows. [\#503](https://github.com/vanniktech/Emoji/pull/503) ([vanniktech](https://github.com/vanniktech))
- EmojiEditText cursor height is too small by wirting emojis followed by text fixes \#492 [\#493](https://github.com/vanniktech/Emoji/pull/493) ([denjoo](https://github.com/denjoo))
- Only construct RecentEmojiManager if one hasn’t already been set [\#478](https://github.com/vanniktech/Emoji/pull/478) ([lukesleeman](https://github.com/lukesleeman))
- minSdk 21, targetSdk 29 & update all of the dependencies [\#465](https://github.com/vanniktech/Emoji/pull/465) ([vanniktech](https://github.com/vanniktech))

Version 0.7.0 *(2020-08-19)*
----------------------------

- Fix hardcoded text not displaying Emojis correctly [\#462](https://github.com/vanniktech/Emoji/pull/462) ([vanniktech](https://github.com/vanniktech))
- Add EmojiCheckbox. [\#460](https://github.com/vanniktech/Emoji/pull/460) ([vanniktech](https://github.com/vanniktech))
- Add duplicate support for Emoji start with variant selector. [\#456](https://github.com/vanniktech/Emoji/pull/456) ([vanniktech](https://github.com/vanniktech))
- Add license headers to JS/KT/Java files. [\#455](https://github.com/vanniktech/Emoji/pull/455) ([vanniktech](https://github.com/vanniktech))
- Add 3-arg-view constructors. [\#454](https://github.com/vanniktech/Emoji/pull/454) ([vanniktech](https://github.com/vanniktech))
- EmojiPopUp: RequestApplyInsets [\#452](https://github.com/vanniktech/Emoji/pull/452) ([ghshenavar](https://github.com/ghshenavar))
- Call EmojiPopup\#start\(\) when rootView is already laid out. [\#448](https://github.com/vanniktech/Emoji/pull/448) ([vanniktech](https://github.com/vanniktech))
- Add shortcodes to emojis [\#446](https://github.com/vanniktech/Emoji/pull/446) ([rubengees](https://github.com/rubengees))
- Unify code across widgets. [\#443](https://github.com/vanniktech/Emoji/pull/443) ([vanniktech](https://github.com/vanniktech))
- Add SingleEmojiTrait to force single \(replaceable\) on an EditText [\#442](https://github.com/vanniktech/Emoji/pull/442) ([vanniktech](https://github.com/vanniktech))
- Fix render issue when isInEditMode\(\) [\#435](https://github.com/vanniktech/Emoji/pull/435) ([Namolem](https://github.com/Namolem))
- Update generator for Unicode 12.1 [\#426](https://github.com/vanniktech/Emoji/pull/426) ([rubengees](https://github.com/rubengees))
- Allow to pass in selectedIconColor. [\#425](https://github.com/vanniktech/Emoji/pull/425) ([vanniktech](https://github.com/vanniktech))
- Fix emoji keyboard in samples [\#411](https://github.com/vanniktech/Emoji/pull/411) ([mario](https://github.com/mario))
- Delay opening of the popup to correctly align with window insets. Fixes \#400 [\#410](https://github.com/vanniktech/Emoji/pull/410) ([mario](https://github.com/mario))
- Add sample with custom view. [\#409](https://github.com/vanniktech/Emoji/pull/409) ([vanniktech](https://github.com/vanniktech))
- Fix set popup window height method [\#402](https://github.com/vanniktech/Emoji/pull/402) ([mario](https://github.com/mario))
- Automatically call start and stop when attaching/detaching EmojiPopup [\#397](https://github.com/vanniktech/Emoji/pull/397) ([rubengees](https://github.com/rubengees))
- Add MaterialEmojiLayoutFactory. [\#396](https://github.com/vanniktech/Emoji/pull/396) ([vanniktech](https://github.com/vanniktech))
- Add EmojiLayoutFactory. [\#395](https://github.com/vanniktech/Emoji/pull/395) ([vanniktech](https://github.com/vanniktech))
- Add emoji-material module for material bindings. [\#394](https://github.com/vanniktech/Emoji/pull/394) ([vanniktech](https://github.com/vanniktech))
- Fix emoji only filter [\#393](https://github.com/vanniktech/Emoji/pull/393) ([mario](https://github.com/mario))
- Fix custom keyboard height [\#392](https://github.com/vanniktech/Emoji/pull/392) ([mario](https://github.com/mario))
- Fix keyboard calculation for API v21+ by introducing start/stop into EmojiPopup [\#389](https://github.com/vanniktech/Emoji/pull/389) ([mario](https://github.com/mario))
- Support textAllCaps option. Fixes \#361 [\#383](https://github.com/vanniktech/Emoji/pull/383) ([mario](https://github.com/mario))
- Don't use bundled AppCompat emojis. Instead download the font. [\#380](https://github.com/vanniktech/Emoji/pull/380) ([vanniktech](https://github.com/vanniktech))
- Support use case where only Emoji Dialog should be shown. [\#378](https://github.com/vanniktech/Emoji/pull/378) ([vanniktech](https://github.com/vanniktech))
- Ship OnlyEmojisInputFilter & MaximalNumberOfEmojisInputFilter. [\#377](https://github.com/vanniktech/Emoji/pull/377) ([vanniktech](https://github.com/vanniktech))
- Update dependencies. [\#376](https://github.com/vanniktech/Emoji/pull/376) ([vanniktech](https://github.com/vanniktech))
- Allow popup height to be changed with a setter [\#373](https://github.com/vanniktech/Emoji/pull/373) ([VitalyKuznetsov](https://github.com/VitalyKuznetsov))
- Fix memory leak in EmojiPopup [\#370](https://github.com/vanniktech/Emoji/pull/370) ([rubengees](https://github.com/rubengees))
- Optimise the category png's to save some space [\#367](https://github.com/vanniktech/Emoji/pull/367) ([rocboronat](https://github.com/rocboronat))
- Make EmojiManager's initialization methods synchronized [\#365](https://github.com/vanniktech/Emoji/pull/365) ([rubengees](https://github.com/rubengees))
- Change default Keyboard + ViewPager animation. [\#353](https://github.com/vanniktech/Emoji/pull/353) ([vanniktech](https://github.com/vanniktech))
- Content descriptors added for supporting talkback accessibility [\#352](https://github.com/vanniktech/Emoji/pull/352) ([AlexMavDev](https://github.com/AlexMavDev))
- Remove EmojiOne [\#338](https://github.com/vanniktech/Emoji/pull/338) ([rubengees](https://github.com/rubengees))
- Update everything to AndroidX [\#335](https://github.com/vanniktech/Emoji/pull/335) ([mario](https://github.com/mario))
- Update README license [\#332](https://github.com/vanniktech/Emoji/pull/332) ([mario](https://github.com/mario))

I want to thank each and every contributor. Special thanks goes out to @mario & @rubengees.

Version 0.6.0 *(2019-02-15)*
----------------------------

- Add disclaimer about instantiating the EmojiPopup early to the README [\#337](https://github.com/vanniktech/Emoji/pull/337) ([rubengees](https://github.com/rubengees))
- Show duplicate emojis in the input but not in the picker [\#336](https://github.com/vanniktech/Emoji/pull/336) ([rubengees](https://github.com/rubengees))
- Add support for custom ViewPager.PageTransformer. [\#334](https://github.com/vanniktech/Emoji/pull/334) ([mario](https://github.com/mario))
- Add keyboard animation [\#333](https://github.com/vanniktech/Emoji/pull/333) ([mario](https://github.com/mario))
- Fix emoji visibility issue [\#330](https://github.com/vanniktech/Emoji/pull/330) ([mario](https://github.com/mario))
- Fix Emoji keyboard in certain cases [\#329](https://github.com/vanniktech/Emoji/pull/329) ([mario](https://github.com/mario))
- Update Android SDK license. [\#327](https://github.com/vanniktech/Emoji/pull/327) ([vanniktech](https://github.com/vanniktech))
- Fix Layout issues in EmojiDialog for several edge cases. [\#326](https://github.com/vanniktech/Emoji/pull/326) ([mario](https://github.com/mario))
- Nuke EmojiEditTextInterface and allow any kind of EditText to be passed. [\#324](https://github.com/vanniktech/Emoji/pull/324) ([vanniktech](https://github.com/vanniktech))
- Add attrs for color customization on theme level [\#320](https://github.com/vanniktech/Emoji/pull/320) ([rubengees](https://github.com/rubengees))
- Remove sudo: false from travis config. [\#316](https://github.com/vanniktech/Emoji/pull/316) ([vanniktech](https://github.com/vanniktech))
- Fix some typos in CHANGELOG.md [\#309](https://github.com/vanniktech/Emoji/pull/309) ([felixonmars](https://github.com/felixonmars))
- Upgrade Jimp to 0.5.0, remove manual promises [\#302](https://github.com/vanniktech/Emoji/pull/302) ([akwizgran](https://github.com/akwizgran))
- ﻿Fix a crash when context instanceof ContextThemeWrapper [\#298](https://github.com/vanniktech/Emoji/pull/298) ([AlanGinger](https://github.com/AlanGinger))
- Use soft references to hold sprites [\#297](https://github.com/vanniktech/Emoji/pull/297) ([akwizgran](https://github.com/akwizgran))
- Allow to override background, icon and divider colors [\#295](https://github.com/vanniktech/Emoji/pull/295) ([RashidianPeyman](https://github.com/RashidianPeyman))
- Add support to AutoCompleteTextView and MultiAutoCompleteTextView [\#292](https://github.com/vanniktech/Emoji/pull/292) ([rocboronat](https://github.com/rocboronat))
- Use Gradle Maven Publish Plugin for publishing. [\#283](https://github.com/vanniktech/Emoji/pull/283) ([vanniktech](https://github.com/vanniktech))
- Add latest emojis for EmojiOne [\#281](https://github.com/vanniktech/Emoji/pull/281) ([rubengees](https://github.com/rubengees))
- Introduce EmojiEditTextInterface which allows custom EditText to work with the Popup. [\#277](https://github.com/vanniktech/Emoji/pull/277) ([Foo-Manroot](https://github.com/Foo-Manroot))
- Nuke badges in README. [\#276](https://github.com/vanniktech/Emoji/pull/276) ([vanniktech](https://github.com/vanniktech))
- Expose instance so that can access replaceWithImages from external package [\#270](https://github.com/vanniktech/Emoji/pull/270) ([SY102134](https://github.com/SY102134))
- Tweak Travis configuration. [\#267](https://github.com/vanniktech/Emoji/pull/267) ([vanniktech](https://github.com/vanniktech))
- Add release method which releases the sheet but not the data structure [\#266](https://github.com/vanniktech/Emoji/pull/266) ([rubengees](https://github.com/rubengees))
- Add missing verifyInstalled to the EmojiManager [\#265](https://github.com/vanniktech/Emoji/pull/265) ([rubengees](https://github.com/rubengees))
- Slightly improve README. [\#262](https://github.com/vanniktech/Emoji/pull/262) ([vanniktech](https://github.com/vanniktech))
- Update emojis and use sprite sheet instead of individual images [\#252](https://github.com/vanniktech/Emoji/pull/252) ([rubengees](https://github.com/rubengees))
- Expose some API so that the library can also be used with other systems such as React. [\#246](https://github.com/vanniktech/Emoji/pull/246) ([SY102134](https://github.com/SY102134))
- Unify Detekt configurations with RC6. [\#232](https://github.com/vanniktech/Emoji/pull/232) ([vanniktech](https://github.com/vanniktech))
- Better travis.yml [\#230](https://github.com/vanniktech/Emoji/pull/230) ([vanniktech](https://github.com/vanniktech))
- Remove duplicates from Checkstyle configuration file. [\#229](https://github.com/vanniktech/Emoji/pull/229) ([vanniktech](https://github.com/vanniktech))
- Fix for emoji keyboard UI artifact \(while fast scrolling\) [\#223](https://github.com/vanniktech/Emoji/pull/223) ([yshubin](https://github.com/yshubin))
- Update Support Library version [\#208](https://github.com/vanniktech/Emoji/pull/208) ([MrHadiSatrio](https://github.com/MrHadiSatrio))
- Empty list memory optimization [\#201](https://github.com/vanniktech/Emoji/pull/201) ([stefanhaustein](https://github.com/stefanhaustein))
- Obey library loading state and add modifiers only where needed [\#199](https://github.com/vanniktech/Emoji/pull/199) ([stefanhaustein](https://github.com/stefanhaustein))
- Add infrastructure to let the provider perform emoji span replacements and utilize in emoji-google-compat [\#198](https://github.com/vanniktech/Emoji/pull/198) ([stefanhaustein](https://github.com/stefanhaustein))
- Emoji Support Library integration [\#196](https://github.com/vanniktech/Emoji/pull/196) ([stefanhaustein](https://github.com/stefanhaustein))
- Let Emoji provide a drawable in addition to the resource id.  [\#195](https://github.com/vanniktech/Emoji/pull/195) ([stefanhaustein](https://github.com/stefanhaustein))
- Don't clean build again when deploying SNAPSHOTS. [\#193](https://github.com/vanniktech/Emoji/pull/193) ([vanniktech](https://github.com/vanniktech))
- Adjust README for Snapshots [\#189](https://github.com/vanniktech/Emoji/pull/189) ([rubengees](https://github.com/rubengees))
- Delete codecov yml file. [\#186](https://github.com/vanniktech/Emoji/pull/186) ([vanniktech](https://github.com/vanniktech))
- Let Gradle install all of the Android dependencies. [\#178](https://github.com/vanniktech/Emoji/pull/178) ([vanniktech](https://github.com/vanniktech))
- Kotlin module [\#147](https://github.com/vanniktech/Emoji/pull/147) ([aballano](https://github.com/aballano))

I want to thank each and every contributor. Thanks @aballano for adding a kotlin module. @stefanhaustein for integrating Google's Emoji AppCompat. Big thanks to @rubengees & @mario who did most of the work and are actively contributing to this library.

Version 0.5.1 *(2017-07-02)*
----------------------------

- Showcase different sizes. [\#172](https://github.com/vanniktech/Emoji/pull/172) ([vanniktech](https://github.com/vanniktech))
- Avoid scrolling the emoji grid after opening the variant popup [\#171](https://github.com/vanniktech/Emoji/pull/171) ([rubengees](https://github.com/rubengees))
- Fix emoji height calculations [\#170](https://github.com/vanniktech/Emoji/pull/170) ([rubengees](https://github.com/rubengees))
- Update Error Prone to 2.0.20 [\#169](https://github.com/vanniktech/Emoji/pull/169) ([vanniktech](https://github.com/vanniktech))
- Update generator for latest changes [\#166](https://github.com/vanniktech/Emoji/pull/166) ([rubengees](https://github.com/rubengees))
- Refactor the VariantEmojiManager [\#165](https://github.com/vanniktech/Emoji/pull/165) ([rubengees](https://github.com/rubengees))
- Update Android Studio Gradle Build Tools to 2.3.3 [\#163](https://github.com/vanniktech/Emoji/pull/163) ([vanniktech](https://github.com/vanniktech))
- Update Checkstyle to 7.8.2 [\#162](https://github.com/vanniktech/Emoji/pull/162) ([vanniktech](https://github.com/vanniktech))
- Update PMD to 5.8.0 [\#161](https://github.com/vanniktech/Emoji/pull/161) ([vanniktech](https://github.com/vanniktech))
- Reflect Emoji Skin Tone automatically in Emoji List. [\#157](https://github.com/vanniktech/Emoji/pull/157) ([vanniktech](https://github.com/vanniktech))
- Also don't generate BuildConfig for Emoji Vendors. [\#154](https://github.com/vanniktech/Emoji/pull/154) ([vanniktech](https://github.com/vanniktech))
- Pull out EmojiRange to package level. [\#152](https://github.com/vanniktech/Emoji/pull/152) ([vanniktech](https://github.com/vanniktech))
- EmojiUtils: Add some documentation to public methods and clean a few things up. [\#151](https://github.com/vanniktech/Emoji/pull/151) ([vanniktech](https://github.com/vanniktech))
- Merge EmojiHandler into EmojiManager. [\#150](https://github.com/vanniktech/Emoji/pull/150) ([vanniktech](https://github.com/vanniktech))
- Add back Emoji Size and use line height as the default. [\#146](https://github.com/vanniktech/Emoji/pull/146) ([vanniktech](https://github.com/vanniktech))
- Added EmojiUtils [\#145](https://github.com/vanniktech/Emoji/pull/145) ([aballano](https://github.com/aballano))
- Don't generate BuildConfig file for release builds. [\#143](https://github.com/vanniktech/Emoji/pull/143) ([vanniktech](https://github.com/vanniktech))
- Add fastlane screengrab to capture screenshots automatically. [\#142](https://github.com/vanniktech/Emoji/pull/142) ([vanniktech](https://github.com/vanniktech))
- Add EmojiButton. [\#137](https://github.com/vanniktech/Emoji/pull/137) ([vanniktech](https://github.com/vanniktech))
- Do some clean ups. [\#135](https://github.com/vanniktech/Emoji/pull/135) ([vanniktech](https://github.com/vanniktech))
- Add night theme support to sample and library. [\#134](https://github.com/vanniktech/Emoji/pull/134) ([vanniktech](https://github.com/vanniktech))
- Add new twitter module and clean up gradle files [\#133](https://github.com/vanniktech/Emoji/pull/133) ([rubengees](https://github.com/rubengees))
- New emojis [\#132](https://github.com/vanniktech/Emoji/pull/132) ([rubengees](https://github.com/rubengees))
- Performance optimization [\#129](https://github.com/vanniktech/Emoji/pull/129) ([rubengees](https://github.com/rubengees))
- Update all emojis to emoji 5.0 [\#119](https://github.com/vanniktech/Emoji/pull/119) ([rubengees](https://github.com/rubengees))
- Adjust generator for emoji 5.0 [\#118](https://github.com/vanniktech/Emoji/pull/118) ([rubengees](https://github.com/rubengees))
- Better emoji height management [\#117](https://github.com/vanniktech/Emoji/pull/117) ([rubengees](https://github.com/rubengees))
- Update Code Quality Configurations. [\#111](https://github.com/vanniktech/Emoji/pull/111) ([vanniktech](https://github.com/vanniktech))
- Remove right Scrollbar in Categories [\#108](https://github.com/vanniktech/Emoji/pull/108) ([RicoChr](https://github.com/RicoChr))
- Improve Popup logic [\#97](https://github.com/vanniktech/Emoji/pull/97) ([rubengees](https://github.com/rubengees))
- Add support for Google emojis [\#95](https://github.com/vanniktech/Emoji/pull/95) ([rubengees](https://github.com/rubengees))
- Make the EmojiEditText more performant [\#93](https://github.com/vanniktech/Emoji/pull/93) ([rubengees](https://github.com/rubengees))
- Support for skin tones in the picker [\#91](https://github.com/vanniktech/Emoji/pull/91) ([rubengees](https://github.com/rubengees))

Many thanks to [rubengees](https://github.com/rubengees) for helping out with a lot of issues.

**Note:**

0.5.1 does contain a few breaking changes. Please consult with the README.

Version 0.5.0 *(2017-07-02)*
----------------------------

There was a problem with publishing to mavenCentral. Please don't use this version. Instead use `0.5.1`.

Version 0.4.0 *(2017-02-15)*
----------------------------

- Soft keyboard not detected when toggling the emoji-popup [\#60](https://github.com/vanniktech/Emoji/issues/60)
- Can't show keyboard [\#58](https://github.com/vanniktech/Emoji/issues/58)
- Opening emoticons, and change the keyboard [\#57](https://github.com/vanniktech/Emoji/issues/57)
- On android 6 emoji not averlays keyboard [\#20](https://github.com/vanniktech/Emoji/issues/20)
- Optimize EmojiGridView hierarchy [\#39](https://github.com/vanniktech/Emoji/pull/39) ([vanniktech](https://github.com/vanniktech))
- Split v4 [\#49](https://github.com/vanniktech/Emoji/pull/49) ([vanniktech](https://github.com/vanniktech))
- Make colors customizable [\#70](https://github.com/vanniktech/Emoji/pull/70) ([rubengees](https://github.com/rubengees))
- Rewrite for more Emojis, modularity and performance [\#77](https://github.com/vanniktech/Emoji/pull/77) ([rubengees](https://github.com/rubengees))

Huge thanks to [rubengees](https://github.com/rubengees) for making this library able to support multiple Emojis ([iOS](https://github.com/vanniktech/Emoji#ios-emojis) & [Emoji One](https://github.com/vanniktech/Emoji#emojione)) as well as fixing those issues:

- Skin tones support [\#34](https://github.com/vanniktech/Emoji/issues/34)
- Add flags [\#12](https://github.com/vanniktech/Emoji/issues/12)
- Add missing Symbols [\#11](https://github.com/vanniktech/Emoji/issues/11)
- Add missing People emojis [\#10](https://github.com/vanniktech/Emoji/issues/10)

**Note:**

0.4.0 is a breaking change so please consult with the README in order to set it up correctly. If you want to continue using the iOS Emojis change this:

```diff
-compile 'com.vanniktech:emoji:0.4.0'
+compile 'com.vanniktech:emoji-ios:0.4.0'
```

and add `EmojiManager.install(new IosEmojiProvider());` in your Applications `onCreate()` method.

Version 0.3.0 *(2016-05-03)*
----------------------------

- Remove Global Layout listener when dismissing the popup. Fixes \#22 [\#24](https://github.com/vanniktech/Emoji/pull/24) ([vanniktech](https://github.com/vanniktech))
- Show People category first when no recent emojis are present [\#16](https://github.com/vanniktech/Emoji/pull/16) ([vanniktech](https://github.com/vanniktech))

Version 0.2.0 *(2016-03-29)*
----------------------------

- Add Recent Emojis Tab [\#13](https://github.com/vanniktech/Emoji/pull/13) ([vanniktech](https://github.com/vanniktech))
- Adding new emojis [\#9](https://github.com/vanniktech/Emoji/pull/9) ([vanniktech](https://github.com/vanniktech))
- Add Library resource config file. Fixes \#6 [\#7](https://github.com/vanniktech/Emoji/pull/7) ([vanniktech](https://github.com/vanniktech))

Version 0.1.0 *(2016-03-12)*
----------------------------

- Initial release
