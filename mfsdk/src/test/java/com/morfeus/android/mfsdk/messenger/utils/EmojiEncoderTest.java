package com.morfeus.android.mfsdk.messenger.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmojiEncoderTest {
    @Test
    public void test_have_emoji_method() throws Exception {
        String inputText = null;
        assertEquals("Error: Have emoji method returned true for null input"
                , false, EmojiEncoder.haveEmojis(inputText));

        String inputText2 = "";
        assertEquals("Error: Have emoji method returned true for blank input"
                , false, EmojiEncoder.haveEmojis(inputText2));

        String inputText3 = " ";
        assertEquals("Error: Have emoji method returned true for space input"
                , false, EmojiEncoder.haveEmojis(inputText3));

        String inputText4 = "Hiii";
        assertEquals("Error: Have emoji method returned true for text input"
                , false, EmojiEncoder.haveEmojis(inputText4));

        String inputText5 = "��";
        assertEquals("Error: Have emoji method returned false for single emoji input"
                , true, EmojiEncoder.haveEmojis(inputText5));

        String inputText6 = "hii��";
        assertEquals("Error: Have emoji method returned false for single emoji with text input"
                , true, EmojiEncoder.haveEmojis(inputText6));

        String inputText7 = "hii����";
        assertEquals("Error: Have emoji method returned false for two emoji " +
                        "with having no space with text input"
                , true, EmojiEncoder.haveEmojis(inputText7));

        String inputText8 = "hii �� ��";
        assertEquals("Error: Have emoji method returned false for multiple emoji " +
                        "with text input and space"
                , true, EmojiEncoder.haveEmojis(inputText8));
    }


    @Test
    public void test_emoji_unicode_method() throws Exception {
        String inputText = null;
        assertEquals("Error: unicode method not accepting null"
                , inputText, EmojiEncoder.convertStringToUnicode(inputText));

        String inputText2 = "";
        assertEquals("Error: unicode method should return black string"
                , inputText2, EmojiEncoder.convertStringToUnicode(inputText2));

        String inputText3 = " ";
        assertEquals("Error: unicode method should return space string"
                , inputText3, EmojiEncoder.convertStringToUnicode(inputText3));

        String inputText4 = "Hiii";
        assertEquals("Error: unicode method should return same string"
                , inputText4, EmojiEncoder.convertStringToUnicode(inputText4));

        String inputText5 = "hii��";
        assertEquals("Error: unicode method should return hii with unicode of emoji"
                , "hii0F7FD", EmojiEncoder.convertStringToUnicode(inputText5));

        String inputText6 = "hii ��";
        assertEquals("Error: unicode method should return hii with unicode and space"
                , "hii 0F7FD", EmojiEncoder.convertStringToUnicode(inputText6));

        String inputText7 = "hii����";
        assertEquals("Error: unicode method should return hii with two unicode of emoji"
                , "hii0F7FD0F7FD", EmojiEncoder.convertStringToUnicode(inputText7));
    }

    @Test
    public void test_emoji_surrogates_method() throws Exception {
        String inputText = null;
        assertEquals("Error: surrogate method not accepting null"
                , inputText, EmojiEncoder.convertStringToSurrogates(inputText));

        String inputText2 = "";
        assertEquals("Error: surrogate method should return black string"
                , inputText2, EmojiEncoder.convertStringToSurrogates(inputText2));

        String inputText3 = " ";
        assertEquals("Error: surrogate method should return space string"
                , inputText3, EmojiEncoder.convertStringToSurrogates(inputText3));

        String inputText4 = "Hiii";
        assertEquals("Error: surrogate method should return same string"
                , inputText4, EmojiEncoder.convertStringToSurrogates(inputText4));

        String inputText5 = "hii��";
        assertEquals("Error: surrogate method should return hii with surrogate pairs"
                , "hiiFFFDFFFD", EmojiEncoder.convertStringToSurrogates(inputText5));

        String inputText6 = "hii ��";
        assertEquals("Error: surrogate method should return hii then space with surrogate pairs"
                , "hii FFFDFFFD", EmojiEncoder.convertStringToSurrogates(inputText6));

        String inputText7 = "hii����";
        assertEquals("Error: surrogate method should return hii with two pairs of surrogates"
                , "hiiFFFDFFFDFFFDFFFD", EmojiEncoder.convertStringToSurrogates(inputText7));
    }
}
