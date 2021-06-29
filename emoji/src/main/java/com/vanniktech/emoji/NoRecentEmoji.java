/*
this class is implement to resolve issue #477
 */

package com.vanniktech.emoji;

import androidx.annotation.NonNull;

import com.vanniktech.emoji.emoji.Emoji;

import java.util.Collection;
/* a singleton class which comprises the object noRecentEmoji (whihc is of type RecentEmoji)
this object will be used in ... classes to eliminate the recentEmoji tab
 */

public class NoRecentEmoji implements RecentEmoji{
    private static NoRecentEmoji noRecentEmoji = null; // our singleton object

    //private constructor
    private NoRecentEmoji() {
    }

    public static NoRecentEmoji getInstance()
    {
        if (noRecentEmoji == null)
            noRecentEmoji = new NoRecentEmoji();
        return noRecentEmoji;
    }

    /* it's not necessary to implement Recentemoji's methods.
    we simply want our singleton object to be of type RecentEmoji.
     */
    @NonNull
    @Override
    public Collection<Emoji> getRecentEmojis() {
        return null;
    }

    @Override
    public void addEmoji(@NonNull Emoji emoji) {

    }

    @Override
    public void persist() {

    }
}
