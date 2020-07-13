package com.craftersconquest.util;

public class StringUtil {

    public static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }
}
