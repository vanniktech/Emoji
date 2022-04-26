# Change Log

Version 0.10.0 *(In development)*
---------------------------------

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
