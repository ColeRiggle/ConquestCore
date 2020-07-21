package com.craftersconquest.object.forge;

import com.craftersconquest.object.guild.SimpleLocation;

public class Forge {

    private final Type type;
    private Tier tier;
    private SimpleLocation location;

    public Forge(Type type, Tier tier) {
        this.type = type;
        this.tier = tier;
    }

    public Forge(Type type, Tier tier, SimpleLocation location) {
        this(type, tier);
        this.location = location;
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

    public void setLocation(SimpleLocation location) {
        this.location = location;
    }

    public SimpleLocation getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return type.toString() + ":" + tier.toString() + ":" + location.toString();
    }
}
