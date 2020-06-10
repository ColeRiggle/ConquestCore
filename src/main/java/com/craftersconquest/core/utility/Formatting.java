package com.craftersconquest.core.utility;

public final class Formatting {

    private Formatting() { }

    public static String getPlural(double quantity, String singular, String plural) {
        if (quantity == 1) {
            return singular;
        }
        return plural;
    }
}
