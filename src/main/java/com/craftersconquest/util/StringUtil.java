package com.craftersconquest.util;

public class StringUtil {

    public static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }

    public static String getProperNounForm(String s) {
        String fullLowercase = s.toLowerCase();
        return fullLowercase.substring(0, 1).toUpperCase() + fullLowercase.substring(1);
    }
}
