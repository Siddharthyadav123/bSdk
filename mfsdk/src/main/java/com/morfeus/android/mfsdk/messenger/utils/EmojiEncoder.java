package com.morfeus.android.mfsdk.messenger.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible to convert emoji to unicode and it's respective surrogates
 */
public class EmojiEncoder {

    public static String convertStringToSurrogates(String message) {
        if (message != null) {
            char[] source = message.toCharArray();
            for (int i = 0; i < source.length; i++) {
                int type = Character.getType(source[i]);
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    String unicode = charToHex(source[i]);
                    message = message.replace(String.valueOf(source[i]), unicode);
                }
            }
        }
        return message;
    }

    public static boolean haveEmojis(String message) {
        if (message != null) {
            char[] source = message.toCharArray();
            for (int i = 0; i < source.length; i++) {
                int type = Character.getType(source[i]);
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return true;
                }
            }
        }
        return false;
    }


    public static String convertStringToUnicode(String message) {
        if (message != null) {
            List<Character> surrogatePairs = new ArrayList<>();
            char[] source = message.toCharArray();
            for (int i = 0; i < source.length; i++) {
                int type = Character.getType(source[i]);
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    surrogatePairs.add(source[i]);
                }
            }
            if (surrogatePairs.size() % 2 == 0) {
                for (int i = 0; i < surrogatePairs.size(); i = i + 2) {
                    char[] units = {surrogatePairs.get(i), surrogatePairs.get(i + 1)};
                    String unicode = decodeUTF16Pairs(units);
                    message = message.replace(String.valueOf(surrogatePairs.get(i))
                            + String.valueOf(surrogatePairs.get(i + 1)), unicode);
                }
            }
        }
        return message;
    }


    private static String decodeUTF16Pairs(char[] units) {
        if (units.length == 2) {
            int code = 0x10000;
            code += (units[0] & 0x03FF) << 10;
            code += (units[1] & 0x03FF);
            return decToHex(code) + "";
        }
        return null;
    }

    private static String unicodeEscaped(char ch) {
        if (ch < 0x10) {
            return "\\u000" + Integer.toHexString(ch);
        } else if (ch < 0x100) {
            return "\\u00" + Integer.toHexString(ch);
        } else if (ch < 0x1000) {
            return "\\u0" + Integer.toHexString(ch);
        }
        return "\\u" + Integer.toHexString(ch);
    }

    private static String charToHex(char c) {
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }

    private static String byteToHex(byte b) {
        char hexDigit[] = {
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        char[] array = {hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f]};
        return new String(array);
    }


    private static String decToHex(int dec) {
        int sizeOfIntInHalfBytes = 5;
        int numberOfBitsInAHalfByte = 4;
        int halfByte = 0x0F;
        char[] hexDigits = {
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };

        StringBuilder hexBuilder = new StringBuilder(sizeOfIntInHalfBytes);
        hexBuilder.setLength(sizeOfIntInHalfBytes);
        for (int i = sizeOfIntInHalfBytes - 1; i >= 0; --i) {
            int j = dec & halfByte;
            hexBuilder.setCharAt(i, hexDigits[j]);
            dec >>= numberOfBitsInAHalfByte;
        }
        return hexBuilder.toString();
    }


}
