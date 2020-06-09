package com.craftersconquest.object.skill;

import com.craftersconquest.object.skill.type.Type;

public class Skill {

    private final Type type;
    private double xp;
    private Level level;

    protected Skill(Type type, double xp, int level) {
        this.type = type;
        this.xp = xp;
        this.level = Level.fromInt(level);
    }

    public Type getType() {
        return type;
    }

    public double getXp() {
        return xp;
    }

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

    private void incrementLevel() {
        level = Level.fromInt(level.numericalValue + 1);
        xp = 0;
    }

    public int getRequiredXpForNextLevel() {
        return Level.fromInt(level.numericalValue + 1).requiredXp;
    }

    public int getLevel() {
        return level.numericalValue;
    }

    private enum Level {
        ZERO(0, 0, 0),
        I(1, 50, 25),
        II (2, 60, 50),
        III (3, 70, 100),
        IV (4, 200, 200),
        V (5, 300, 300),
        VI (6, 500, 400),
        VII (7, 700, 500),
        VIII (8, 1000, 600),
        IX (9, 1300, 700),
        X (10, 1700, 800),
        XI (11, 2200, 900),
        XII (12, 2800, 1000),
        XIII (13, 3500, 1100),
        XIV (14, 4500, 1200),
        XV (15, 5500, 1300),
        XVI (16, 7000, 1500),
        XVII (17, 8500, 1700),
        XVIII (18, 10000, 1900),
        XIX (19, 11500, 2100),
        XX (20, 13000, 2500),
        XXI (21, 15000, 3000),
        XXII (22, 17000, 3500),
        XXIII (23, 19500, 4000),
        XXIV (24, 25000, 4500),
        XXV (25, 30000, 5500);

        int numericalValue;
        int requiredXp;
        int coinReward;

        Level(int numericalValue, int requiredXp, int coinReward) {
            this.numericalValue = numericalValue;
            this.requiredXp = requiredXp;
            this.coinReward = coinReward;
        }

        public static Level fromInt(int value) {
            return Level.values()[value];
        }
    }
}