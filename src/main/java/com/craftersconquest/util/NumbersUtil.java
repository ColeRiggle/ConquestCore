package com.craftersconquest.util;

import java.text.NumberFormat;

public class NumbersUtil {

    public static String formatDouble(double input) {
        NumberFormat.getInstance().setMaximumFractionDigits(2);
        return NumberFormat.getInstance().format(input);
    }

    public static String formatInt(int input) {
        return NumberFormat.getInstance().format(input);
    }
}
