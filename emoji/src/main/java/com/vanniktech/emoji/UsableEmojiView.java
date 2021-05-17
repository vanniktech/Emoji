/*
 UseableEmojiView has been created to address issue #473 and aims to
 implement a version of EmojiView which is usable in xml files as an independent unit.
 it's possible that this implementation might not be the best solution for many cases, but it acts
 as a basic point for other developers to modify it corresponding to their needs.
 */

package com.vanniktech.emoji;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.content.res.AppCompatResources;

import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.vanniktech.emoji.emoji.EmojiCategory;
import com.vanniktech.emoji.listeners.OnEmojiBackspaceClickListener;
import com.vanniktech.emoji.listeners.OnEmojiClickListener;
import com.vanniktech.emoji.listeners.OnEmojiLongClickListener;
import com.vanniktech.emoji.listeners.RepeatListener;

import static java.util.concurrent.TimeUnit.SECONDS;

@SuppressLint("ViewConstructor") public final class UsableEmojiView extends LinearLayout implements ViewPager.OnPageChangeListener {
    private static final long INITIAL_INTERVAL = SECONDS.toMillis(1) / 2;
    private static final int NORMAL_INTERVAL = 50;
    /*
    these variables and their setters have been added to help alter this class to a standard view class.
     */
    private OnEmojiClickListener onEmojiClickListener;
    private OnEmojiLongClickListener onEmojiLongClickListener;

    @ColorInt private int themeAccentColor;
    @ColorInt private int themeIconColor;
    private RecentEmoji recentEmoji;
    private View rootView;
    private Activity context;
    private ViewPager emojisPager;
    private View emojiDivider ;
    private ImageButton[] emojiTabs;
    private EmojiPagerAdapter emojiPagerAdapter;



    /* this method needs to be called at start to initialize the view properly.
        using this method you can setup your onClickListeners.
        @param rootView is the parent view that our emojiView will be placed in.
        @param containRecentEmoji defines if you want recent emojis tab in view.
        @param onEmojiClickListener listener for clicking on emojis
        @param onEmojiLongClickListener listener for long clicking on emojis
     */

    public void setupView(View rootView,boolean containRecentEmoji,
                          final OnEmojiClickListener onEmojiClickListener,
                          final OnEmojiLongClickListener onEmojiLongClickListener) {
        this.rootView = rootView;
        this.context = Utils.asActivity(rootView.getContext());
        View.inflate(this.context, R.layout.emoji_view, this);
        setOrientation(VERTICAL);
        if (containRecentEmoji)
            recentEmoji = new RecentEmojiManager(this.context);
        else
            recentEmoji = NoRecentEmoji.getInstance();
        final TypedValue value = new TypedValue();
        this.context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        emojisPager = findViewById(R.id.emojiViewPager);
        emojiDivider = findViewById(R.id.emojiViewDivider);
        emojiDivider.setBackgroundColor(Utils.resolveColor(context, R.attr.emojiDivider, R.color.emoji_divider));
        emojisPager.setPageTransformer(true, new PageTransformer());

        final LinearLayout emojisTab = findViewById(R.id.emojiViewTab);
        emojisPager.addOnPageChangeListener(this);

        final EmojiCategory[] categories = EmojiManager.getInstance().getCategories();

        if(recentEmoji instanceof NoRecentEmoji) // this means we don't want the recentEmojiTab
        {
            emojiTabs = new ImageButton[categories.length + 1];
            for (int i = 0; i < categories.length; i++) {
                emojiTabs[i] = inflateButton(context, categories[i].getIcon(), categories[i].getCategoryName(), emojisTab);
            }
        }
        else
        {
            emojiTabs = new ImageButton[categories.length + 2];
            emojiTabs[0] = inflateButton(context, R.drawable.emoji_recent, R.string.emoji_category_recent, emojisTab);
            for (int i = 0; i < categories.length; i++) {
                emojiTabs[i + 1] = inflateButton(context, categories[i].getIcon(), categories[i].getCategoryName(), emojisTab);
            }
        }
        emojiTabs[emojiTabs.length - 1] = inflateButton(context, R.drawable.emoji_backspace, R.string.emoji_backspace, emojisTab);

        handleOnClicks(emojisPager);

        emojiPagerAdapter = new EmojiPagerAdapter(onEmojiClickListener, onEmojiLongClickListener, this.recentEmoji, new VariantEmojiManager(rootView.getContext()));
        emojisPager.setAdapter(emojiPagerAdapter);

        int startIndex;
        if(recentEmoji instanceof NoRecentEmoji)
            startIndex = 0;
        else
            startIndex = emojiPagerAdapter.numberOfRecentEmojis() > 0 ? 0 : 1;
        emojisPager.setCurrentItem(startIndex);
        onPageSelected(startIndex);
    }




    public void setThemeAccentColor(int themeAccentColor) {
        this.themeAccentColor = themeAccentColor;
    }

    public void setThemeIconColor(int themeIconColor) {
        this.themeIconColor = themeIconColor;
    }
    public void setOnEmojiClickListener(OnEmojiClickListener onEmojiClickListener) {
        this.onEmojiClickListener = onEmojiClickListener;
    }

    public void setOnEmojiLongClickListener(OnEmojiLongClickListener onEmojiLongClickListener) {
        this.onEmojiLongClickListener = onEmojiLongClickListener;
    }

    public void omitRecentEmojiTab()
    {
        recentEmoji = NoRecentEmoji.getInstance();
    }
    public void setEmojiDividerColor(int dividerColor){
        emojiDivider.setBackgroundColor(dividerColor != 0 ? dividerColor : Utils.resolveColor(context, R.attr.emojiDivider, R.color.emoji_divider));
    }





    @Nullable OnEmojiBackspaceClickListener onEmojiBackspaceClickListener;

    private int emojiTabLastSelectedIndex = -1;
    private void handleOnClicks(final ViewPager emojisPager) {
        for (int i = 0; i < emojiTabs.length - 1; i++) {
            emojiTabs[i].setOnClickListener(new EmojiTabsClickListener(emojisPager, i));
        }

        emojiTabs[emojiTabs.length - 1].setOnTouchListener(new RepeatListener(INITIAL_INTERVAL, NORMAL_INTERVAL, new OnClickListener() {
            @Override public void onClick(final View view) {
                if (onEmojiBackspaceClickListener != null) {
                    onEmojiBackspaceClickListener.onEmojiBackspaceClick(view);
                }
            }
        }));

    }




    /*
    we change the constructor so that it conforms to androids standard view.
     */
    @SuppressWarnings("PMD.CyclomaticComplexity") public UsableEmojiView(final Context context,
                                                                         AttributeSet attrs) {
        super(context,attrs);

    }

    public void setOnEmojiBackspaceClickListener(@Nullable final OnEmojiBackspaceClickListener onEmojiBackspaceClickListener) {
        this.onEmojiBackspaceClickListener = onEmojiBackspaceClickListener;
    }

    private ImageButton inflateButton(final Context context, @DrawableRes final int icon, @StringRes final int categoryName, final ViewGroup parent) {
        final ImageButton button = (ImageButton) LayoutInflater.from(context).inflate(R.layout.emoji_view_category, parent, false);

        button.setImageDrawable(AppCompatResources.getDrawable(context, icon));
        button.setColorFilter(themeIconColor, PorterDuff.Mode.SRC_IN);
        button.setContentDescription(context.getString(categoryName));

        parent.addView(button);

        return button;
    }

    @Override public void onPageSelected(final int i) {
        if (emojiTabLastSelectedIndex != i) {
            if (i == 0 && emojiPagerAdapter.recentEmojiAllowed()) {
                emojiPagerAdapter.invalidateRecentEmojis();
            }

            if (emojiTabLastSelectedIndex >= 0 && emojiTabLastSelectedIndex < emojiTabs.length) {
                emojiTabs[emojiTabLastSelectedIndex].setSelected(false);
                emojiTabs[emojiTabLastSelectedIndex].setColorFilter(themeIconColor, PorterDuff.Mode.SRC_IN);
            }

            emojiTabs[i].setSelected(true);
            emojiTabs[i].setColorFilter(themeAccentColor, PorterDuff.Mode.SRC_IN);

            emojiTabLastSelectedIndex = i;
        }
    }

    @Override public void onPageScrolled(final int i, final float v, final int i2) {
        // No-op.
    }

    @Override public void onPageScrollStateChanged(final int i) {
        // No-op.
    }

    static class EmojiTabsClickListener implements OnClickListener {
        private final ViewPager emojisPager;
        private final int position;

        EmojiTabsClickListener(final ViewPager emojisPager, final int position) {
            this.emojisPager = emojisPager;
            this.position = position;
        }

        @Override public void onClick(final View v) {
            emojisPager.setCurrentItem(position);
        }
    }
}

