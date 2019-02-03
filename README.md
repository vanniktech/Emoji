# Emoji

A simple library to add Emoji support to your Android app. In a PopupWindow Emojis can be chosen. In order to edit and display text with Emojis this library provides public APIs: [`EmojiEditText`](emoji/src/main/java/com/vanniktech/emoji/EmojiEditText.java), [`EmojiTextView`](emoji/src/main/java/com/vanniktech/emoji/EmojiTextView.java) & [`EmojiButton`](emoji/src/main/java/com/vanniktech/emoji/EmojiButton.java).

The library has 3 different providers to choose from ([iOS](#ios-emojis), [Google](#google) & [Twitter](#twitter)).

## iOS Emojis

<img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/ios_1_1498998365491.png" alt="Normal Keyboard" width="270"><img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/ios_2_1498998367998.png" alt="Emoji Keyboard" width="270" hspace="20"><img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/ios_3_1498998369550.png" alt="Recent Emojis" width="270">

For getting the above iOS Emojis simply add the dependency and code below.

```groovy
compile 'com.vanniktech:emoji-ios:0.5.1'
```

And install the provider (preferably in your Application class):

```java
// This line needs to be executed before any usage of EmojiTextView, EmojiEditText or EmojiButton.
EmojiManager.install(new IosEmojiProvider());
```

## Google

<img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/google_1_1498998373883.png" alt="Normal Keyboard" width="270"><img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/google_2_1498998376865.png" alt="Emoji Keyboard" width="270" hspace="20"><img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/google_3_1498998378352.png" alt="Recent Emojis" width="270">

For getting the above Google Emojis simply add the dependency and code below.

```groovy
compile 'com.vanniktech:emoji-google:0.5.1'
```

And install the provider (preferably in your Application class):

```java
// This line needs to be executed before any usage of EmojiTextView, EmojiEditText or EmojiButton.
EmojiManager.install(new GoogleEmojiProvider());
```

## Twitter

<img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/twitter_1_1498998347702.png" alt="Normal Keyboard" width="270"><img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/twitter_2_1498998350492.png" alt="Emoji Keyboard" width="270" hspace="20"><img src="./fastlane/metadata/android/en-US/images/phoneScreenshots/twitter_3_1498998352130.png" alt="Recent Emojis" width="270">

For getting the above Twitter Emojis simply add the dependency and code below.

```groovy
compile 'com.vanniktech:emoji-twitter:0.5.1'
```

And install the provider (preferably in your Application class):

```java
// This line needs to be executed before any usage of EmojiTextView, EmojiEditText or EmojiButton.
EmojiManager.install(new TwitterEmojiProvider());
```

### Custom Emojis

If you want to display your own Emojis you can create your own implementation of [`EmojiProvider`](emoji/src/main/java/com/vanniktech/emoji/EmojiProvider.java) and pass it to `EmojiManager.install`.

All of the core API lays in, which is being pulled in automatically by the provided implementations ([iOS](#ios-emojis), [Google](#google) & [Twitter](#twitter)):

```groovy
compile 'com.vanniktech:emoji:0.5.1'
```

### Custom EditText

If you want to add the emoji support to your existing `EditText`, you only have to
`implement` `EmojiEditTextInterface`. An example can be seen on the default `EditText`
implementation: `EmojiEditText`.

Keep in mind that this custom class must be a subclass of `android.view.View`.

---

## Inserting Emojis

Declare your [`EmojiEditText`](emoji/src/main/java/com/vanniktech/emoji/EmojiEditText.java) in your layout xml file.

```xml
<com.vanniktech.emoji.EmojiEditText
  android:id="@+id/emojiEditText"
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:imeOptions="actionSend"
  android:inputType="textCapSentences|textMultiLine"
  android:maxLines="3"/>
```

To open the [`EmojiPopup`](emoji/src/main/java/com/vanniktech/emoji/EmojiPopup.java) execute the code below:

```java
final EmojiPopup emojiPopup = EmojiPopup.Builder.fromRootView(rootView).build(emojiEditText);
emojiPopup.toggle(); // Toggles visibility of the Popup.
emojiPopup.dismiss(); // Dismisses the Popup.
emojiPopup.isShowing(); // Returns true when Popup is showing.
```

The `rootView` is the rootView of your layout xml file which will be used for calculating the height of the keyboard.
`emojiEditText` is the [`EmojiEditText`](emoji/src/main/java/com/vanniktech/emoji/EmojiEditText.java) that you declared in your layout xml file.

### Displaying Emojis

```xml
<com.vanniktech.emoji.EmojiTextView
  android:id="@+id/emojiTextView"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"/>
```

Just use the [`EmojiTextView`](emoji/src/main/java/com/vanniktech/emoji/EmojiTextView.java) and call `setText` with the String that contains Unicode encoded Emojis. To change the size of the displayed Emojis use the `lineHeight` property from TextView.

## Listeners

The [`EmojiPopup`](emoji/src/main/java/com/vanniktech/emoji/EmojiPopup.java) builder allows you to declare several listeners.

```java
setOnSoftKeyboardCloseListener(OnSoftKeyboardCloseListener listener);
setOnEmojiClickListener(OnEmojiClickListener listener);
setOnSoftKeyboardOpenListener(OnSoftKeyboardOpenListener listener);
setOnEmojiPopupShownListener(OnEmojiPopupShownListener listener);
setOnEmojiPopupDismissListener(OnEmojiPopupDismissListener listener);
setOnEmojiBackspaceClickListener(OnEmojiBackspaceClickListener listener);
```

### Custom Recent Emoji implementation

You can pass your own implementation of the recent Emojis. Just let one of your classes implement the [`RecentEmoji`](emoji/src/main/java/com/vanniktech/emoji/RecentEmoji.java) interface and pass it when you're building the [`EmojiPopup`](emoji/src/main/java/com/vanniktech/emoji/EmojiPopup.java):

```java
setRecentEmoji(yourClassThatImplementsRecentEmoji)
```

If no instance or a null instance is set the [default implementation](./emoji/src/main/java/com/vanniktech/emoji/RecentEmojiManager.java) will be used.

### Custom Variant Emoji implementation

You can pass your own implementation of the variant Emojis. Just let one of your classes implement the [`VariantEmoji`](emoji/src/main/java/com/vanniktech/emoji/VariantEmoji.java) interface and pass it when you're building the [`EmojiPopup`](emoji/src/main/java/com/vanniktech/emoji/EmojiPopup.java):

```java
setVariantEmoji(yourClassThatImplementsVariantEmoji)
```

If no instance or a null instance is set the [default implementation](./emoji/src/main/java/com/vanniktech/emoji/VariantEmojiManager.java) will be used.

## Animations

### Custom keyboard enter and exit animations

You can pass your own animation style for enter and exit transitions of the Emoji keyboard while you're building the [`EmojiPopup`](emoji/src/main/java/com/vanniktech/emoji/EmojiPopup.java):

```java
setKeyboardAnimationStyle(R.style.emoji_fade_animation_style);
```

If no style is set the keyboard will appear and exit as a regular PopupWindow. 
This library currently ships with two animation styles as an example:

- R.style.emoji_slide_animation_style
- R.style.emoji_fade_animation_style

### Custom page transformers

You can pass your own Page Transformer for the Emoji keyboard View Pager while you're building the [`EmojiPopup`](emoji/src/main/java/com/vanniktech/emoji/EmojiPopup.java):

```java
setPageTransformer(new MagicTransformer());
```

If no transformer is set ViewPager will behave as its usual self. Please do note that this library currently does not ship any example Page Transformers.

# Snapshots

This library is also distributed as a SNAPSHOT if you like to check out the latest features.

> Note: The API is not stable and may change and break your code at any time if you use a SNAPSHOT.

Add this to your repositories:

```groovy
maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
```

And **one** of these to your dependencies:

```groovy
compile 'com.vanniktech:emoji-ios:0.6.0-SNAPSHOT'
compile 'com.vanniktech:emoji-google:0.6.0-SNAPSHOT'
compile 'com.vanniktech:emoji-twitter:0.6.0-SNAPSHOT'
```

# Proguard

No configuration needed.

# License

Copyright (C) 2016 Vanniktech - Niklas Baudy

Licensed under the Apache License, Version 2.0
