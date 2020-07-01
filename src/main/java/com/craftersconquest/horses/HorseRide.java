package com.craftersconquest.horses;

import com.craftersconquest.object.horse.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HorseRide {

    private final Player player;
    private ItemStack triggerItem;
    private final Horse horse;
    private final org.bukkit.entity.Horse horseEntity;

    public HorseRide(Player player, ItemStack triggerItem, Horse horse, org.bukkit.entity.Horse horseEntity) {
        this.player = player;
        this.triggerItem = triggerItem;
        this.horse = horse;
        this.horseEntity = horseEntity;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getTriggerItem() {
        return triggerItem;
    }

    public void setTriggerItem(ItemStack item) {
        triggerItem = item;
    }

    public Horse getHorse() {
        return horse;
    }

    public org.bukkit.entity.Horse getHorseEntity() {
        return horseEntity;
    }
}
