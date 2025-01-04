package com.bruce.backend.stringmanipulation;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class StringManipulation {

    public static String strrep(String original, String target, String replacement) {
        if (target.isEmpty()) {
            return original;
        }
        StringBuilder result = new StringBuilder();
        int startIndex = 0;
        int matchIndex;
        while ((matchIndex = original.indexOf(target, startIndex)) != -1) {
            result.append(original, startIndex, matchIndex).append(replacement);
            startIndex = matchIndex + target.length();
        }
        result.append(original.substring(startIndex));
        return result.toString();
    }
}
