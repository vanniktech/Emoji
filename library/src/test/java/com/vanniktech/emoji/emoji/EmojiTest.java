package com.vanniktech.emoji.emoji;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

@SuppressWarnings("checkstyle:magicnumber")
public class EmojiTest {
    @Test
    public void fromCodePoint() {
        final Emoji emoji = Emoji.fromCodePoint(0x1f600);
        assertThat(emoji.getEmoji()).isEqualTo("😀");
    }

    @Test
    public void fromChar() {
        final Emoji emoji = Emoji.fromChar((char) 0x2708);
        assertThat(emoji.getEmoji()).isEqualTo("✈");
    }

    @Test
    public void getEmoji() {
        final Emoji emoji = new Emoji("emoji");
        assertThat(emoji.getEmoji()).isEqualTo("emoji");
    }

    @Test
    public void hash() {
        final Emoji nik = new Emoji("nik");
        final Emoji foo = new Emoji("foo");

        assertThat(nik.hashCode()).isEqualTo(new Emoji("nik").hashCode());
        assertThat(foo.hashCode()).isEqualTo(new Emoji("foo").hashCode());
        assertThat(nik.hashCode()).isNotEqualTo(foo.hashCode());
    }

    @Test
    public void equality() {
        final Emoji bar = new Emoji("bar");
        final Emoji test = new Emoji("test");

        assertThat(bar).isEqualTo(new Emoji("bar"));
        assertThat(test).isEqualTo(new Emoji("test"));
        assertThat(bar).isNotEqualTo(test);
    }
}
