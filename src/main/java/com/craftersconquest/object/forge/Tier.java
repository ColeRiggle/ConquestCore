package com.craftersconquest.object.forge;

import java.util.Arrays;

public enum Tier {
    I(1, 3),
    II(1.25, 10),
    III(1.5, 25),
    IV(1.75, 50),
    V(2, 75),
    VI(2.25, 100),
    VII(2.5, 125);

    private static final Tier[] tierValues = Tier.values();

    private final double productionRate;
    private final double upgradeCost;

    Tier(double productionRate, double upgradeCost) {
        this.productionRate = productionRate;
        this.upgradeCost = upgradeCost;
    }

    public double getProductionRate() {
        return productionRate;
    }

    public double getUpgradeCost() {
        return upgradeCost;
    }

    public static Tier getMax() {
        return tierValues[tierValues.length - 1];
    }

    public static Tier fromInt(int value) {
        return tierValues[value - 1];
    }

    public int toInt() {
        return Arrays.asList(tierValues).indexOf(this) + 1;
    }

    public Tier next() {
        if (this == getMax()) {
            return null;
        } else {
            return fromInt(toInt() + 1);
        }
    }
}
