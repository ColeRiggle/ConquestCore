package com.craftersconquest.object.forge;

public class Forge {

    private final Type type;
    private Tier tier;

    public Forge(Type type, Tier tier) {
        this.type = type;
        this.tier = tier;
    }

    public Type getType() {
        return type;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

}
