package com.craftersconquest.object.skill;

public enum Ability {

    EXCAVATOR("chance to get double ore drops."),
    HARVESTER("chance to get double crops."),
    FORAGER("chance to get double logs."),
    SORCERER("more experience from mobs."),
    OUTLAW("coins from killing players.");

    private final String abilityDescription;

    Ability(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }
}
