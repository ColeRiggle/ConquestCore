package com.craftersconquest.object.horse;

import com.craftersconquest.object.Upgradable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Horse implements Upgradable {

    private final String name;
    private int level;
    private double xp;
    private Tier tier;

    private Horse(String name, int level, double xp, Tier tier) {
        this.name = name;
        this.level = level;
        this.xp = xp;
        this.tier = tier;
    }

    // Horses are stored in lore in accordance with the following format

    public Horse fromItemStack(ItemStack horse) {
        ItemMeta meta = horse.getItemMeta();
        String name = meta.getDisplayName();
        meta.getLore();
        return null;
    }

    public String getName() {
        return name;
    }

    private int getRequiredXpForNextLevel() {
        return getRequiredXpForLevel(level + 1);
    }

    private int getRequiredXpForLevel(int level) {
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

    private void incrementLevel() {
        level += 1;
        xp = 0;
    }
}
