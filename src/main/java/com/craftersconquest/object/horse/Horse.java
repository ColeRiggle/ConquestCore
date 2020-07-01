package com.craftersconquest.object.horse;

import com.craftersconquest.object.Upgradable;

public class Horse implements Upgradable {

    private final String name;
    private int level;
    private double xp;
    private Tier tier;

    public Horse(String name, int level, double xp, Tier tier) {
        this.name = name;
        this.level = level;
        this.xp = xp;
        this.tier = tier;
    }

    public String getName() {
        return name;
    }

    public int getRequiredXpForNextLevel() {
        return getRequiredXpForLevel(level + 1);
    }

    public int getRequiredXpForLevel(int level) {
        if (level == 1) {
            return 60;
        } else {
            return getRequiredXpForLevel(level - 1) + 25;
        }
    }

    @Override
    public double getXp() {
        return xp;
    }

    @Override
    public void addXp(double amount) {
        final double potentialAmount = xp + amount;

        if (potentialAmount > getRequiredXpForNextLevel()) {
            final double remainder = potentialAmount - getRequiredXpForNextLevel();
            incrementLevel();
            addXp(remainder);
        } else {
            xp += amount;
        }
    }

    public int getLevel() {
        return level;
    }

    private void incrementLevel() {
        level += 1;
        xp = 0;
        if (level > tier.getMax()) {
            tier = Tier.fromInt(tier.toInt() + 1);
        }
    }

    public Tier getTier() {
        return tier;
    }
}
