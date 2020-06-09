package com.craftersconquest.object;

public class Bounty {

    private double amount;
    private int killCount;

    private Bounty(double amount, int killCount) {
        this.amount = amount;
        this.killCount = killCount;
    }

    public static Bounty fromAmountAndKillCount(double amount, int killCount) {
        return new Bounty(amount, killCount);
    }

    public void incrementKillCount(int amount) {
        killCount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void reset() {
        amount = 0;
        killCount = 0;
    }

}
