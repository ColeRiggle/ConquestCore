package com.craftersconquest.objects.skill;

public enum SkillAbility {

    EXCAVATOR("chance to get double ore drops."),
    HARVESTER("chance to get double crops."),
    FORAGING("chance to get double logs."),
    SORCERER("more experience from mobs.");

    private String abilityDescription;

    SkillAbility(String abilityDescription) {
        this.abilityDescription = abilityDescription;
    }
}
