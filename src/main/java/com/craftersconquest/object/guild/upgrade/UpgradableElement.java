package com.craftersconquest.object.guild.upgrade;

import java.util.List;

public class UpgradableElement {

    private final String name;
    private final String description;
    private final List<Upgrade> tiers;

    public UpgradableElement(String name, String description, List<Upgrade> tiers) {
        this.name = name;
        this.description = description;
        this.tiers = tiers;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Upgrade> getTiers() {
        return tiers;
    }
}
