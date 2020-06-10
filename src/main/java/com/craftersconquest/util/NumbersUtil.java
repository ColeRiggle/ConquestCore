package com.craftersconquest.util;

import java.text.NumberFormat;

public class NumbersUtil {

    public static String formatDouble(double input) {
        return NumberFormat.getInstance().format(input);
    }

    public static String formatInt(int input) {
        return NumberFormat.getInstance().format(input);
    }
}
