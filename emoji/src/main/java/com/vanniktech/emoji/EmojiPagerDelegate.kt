package com.vanniktech.emoji

import com.vanniktech.emoji.listeners.OnEmojiClickListener
import com.vanniktech.emoji.listeners.OnEmojiLongClickListener

internal interface EmojiPagerDelegate : OnEmojiClickListener, OnEmojiLongClickListener
