package com.craftersconquest.object.horse;

public enum Tier {

    I(10, 10),
    II(20, 20),
    III(30, 30),
    IV(40, 40);

    private final int maxSpeed;
    private final int maxJump;

    Tier(int maxSpeed, int maxJump) {
        this.maxSpeed = maxSpeed;
        this.maxJump = maxJump;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getMaxJump() {
        return maxJump;
    }
}
