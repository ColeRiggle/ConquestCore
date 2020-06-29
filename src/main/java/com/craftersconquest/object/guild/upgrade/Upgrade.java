package com.craftersconquest.object.guild.upgrade;

import com.craftersconquest.object.shop.item.Cost;

public class Upgrade {

    private final String description;
    private final Cost cost;
    private final RepeatableAction action;

    public Upgrade(String description, Cost cost, RepeatableAction action) {
        this.description = description;
        this.cost = cost;
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public Cost getCost() {
        return cost;
    }

    public RepeatableAction getAction() {
        return action;
    }
}
