package com.craftersconquest.object.horse;

import org.bukkit.entity.Horse;

public enum Tier {

    I(10, Horse.Color.BROWN),
    II(20, Horse.Color.CHESTNUT),
    III(30, Horse.Color.BLACK),
    IV(40, Horse.Color.WHITE);

    private final int max;
    private final Horse.Color color;

    private static final Tier[] tierValues = Tier.values();

    Tier(int max, Horse.Color color) {
        this.max = max;
        this.color = color;
    }

    public int getMax() {
        return max;
    }

    public Horse.Color getColor() {
        return color;
    }

    public int toInt() {
        return max / 10;
    }

    public static Tier fromInt(int value) {
        return tierValues[value - 1];
    }
}
