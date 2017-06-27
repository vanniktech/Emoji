package com.vanniktech.emoji;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vanniktech.emoji.emoji.Emoji;

import java.util.ArrayList;
import java.util.StringTokenizer;

final class RecentVariantManager {

    private static RecentVariantManager instance;

    public static RecentVariantManager getInstance (){
        if(RecentVariantManager.instance == null){
            RecentVariantManager.instance = new RecentVariantManager();
        }
        return RecentVariantManager.instance;
    }

    private static final String PREFERENCE_NAME = "emoji-recent-skintone-manager";
    private static final String EMOJI_DELIMITER = "~";
    private static final String RECENT_VARIANTS = "recent-skintones";
    private static final int EMOJI_GUESS_SIZE = 5;

    private ArrayList<Emoji> recentVariantsList = new ArrayList<>(0);

    public Emoji getMostRecentVariant(@NonNull final Emoji desiredEmoji, Context context){
        if(recentVariantsList.isEmpty()){
            final String savedRecentVariants = getPreferences(context.getApplicationContext()).getString(RECENT_VARIANTS, "");

            if(savedRecentVariants.length() > 0){
                final StringTokenizer stringTokenizer = new StringTokenizer(savedRecentVariants, EMOJI_DELIMITER);
                recentVariantsList = new ArrayList<>(stringTokenizer.countTokens());

                while (stringTokenizer.hasMoreTokens()) {
                    final String token = stringTokenizer.nextToken();

                    final Emoji emoji = EmojiManager.getInstance().findEmoji(token);

                    if(emoji != null && emoji.getLength() == token.length()){
                        recentVariantsList.add(emoji);
                    }
                }
            }
        }

        if(recentVariantsList.size() > 0) {
            for (Emoji emoji : recentVariantsList) {
                if (desiredEmoji.getBase() == emoji.getBase()) return emoji;
            }
        }
        return desiredEmoji;
    }

    /**
     * @return true, if something was actually changed
     */
    public boolean addRecentVariant(@NonNull final Emoji currentVariant){
        if(currentVariant.getBase() == currentVariant) return false;
        for(Emoji emoji : recentVariantsList){
            if(emoji.getBase() == currentVariant.getBase()){
                if(emoji != currentVariant) {
                    recentVariantsList.remove(emoji);
                    recentVariantsList.add(currentVariant);
                    return true;
                } else {
                    return false;
                }
            }
        }
        recentVariantsList.add(currentVariant);
        return true;
    }

    public void persist(Context context) {
        if (recentVariantsList.size() > 0) {
            final StringBuilder stringBuilder = new StringBuilder(recentVariantsList.size() * EMOJI_GUESS_SIZE);
            for (final Emoji emoji : recentVariantsList) {
                stringBuilder.append(emoji.getUnicode())
                        .append(EMOJI_DELIMITER);
            }

            stringBuilder.setLength(stringBuilder.length() - EMOJI_DELIMITER.length());

            getPreferences(context.getApplicationContext()).edit().putString(RECENT_VARIANTS, stringBuilder.toString()).apply();
        }
    }

    private SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

}
