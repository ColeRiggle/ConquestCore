package com.craftersconquest.horses;

import com.craftersconquest.object.horse.Horse;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HorseItemStackConverter {

    public ItemStack createItemStackFromHorse(Horse horse) {
        return new ItemStack(Material.SADDLE);
    }
}
