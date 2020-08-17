package com.craftersconquest.horses;

import com.craftersconquest.object.horse.Horse;
import com.craftersconquest.object.horse.Tier;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HorseConverter {

    private final static String SPEED_PREFIX = ChatColor.BLUE + "Speed: ";
    private final static String JUMP_PREFIX = ChatColor.BLUE + "Jump: ";
    private final static String XP_PREFIX = ChatColor.AQUA + "XP: ";

    // !! Format !!
    // Tier X Horse
    // Custom name
    // Speed
    // Jump
    // XP Bar

    public ItemStack createItemStackFromHorse(Horse horse) {
        ItemStack baseItemStack = new ItemBuilder(Material.SADDLE).
                setDisplayName(getDisplayName(horse)).
                setLore(createHorseLore(horse)).
                build();

        return baseItemStack;
    }

    private String getDisplayName(Horse horse) {
        if (horse.getName().equals("")) {
            return ChatColor.WHITE + "Tier " + horse.getTier().toString() + " Horse";
        } else {
            return horse.getName();
        }
    }

    private List<String> createHorseLore(Horse horse) {
        List<String> lore = new ArrayList<>();
        int currentValue = horse.getLevel();
        int maxValue = horse.getTier().getMaxLevel();
        lore.add("");
        lore.add(SPEED_PREFIX + currentValue + "/" + maxValue);
        lore.add(JUMP_PREFIX + currentValue + "/" + maxValue);
        lore.add(XP_PREFIX + (int) horse.getXp() + "/" + horse.getRequiredXpForNextLevel());
        return lore;
    }

    public Horse getHorseFromItemStack(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();

        String itemName = itemMeta.getDisplayName();
        int begin = 5;
        int end = itemName.indexOf(" Horse");
        Tier tier = Tier.valueOf(itemName.substring(begin, end));

        String name = lore.get(0);
        int level = getLevelFromString(lore.get(1));
        double xp = getXpFromString(lore.get(3));

        Horse horse = new Horse(name, level, xp, tier);
        return horse;
    }

    private int getLevelFromString(String levelLore) {
        return Integer.valueOf(getLoreSubstring(levelLore));
    }

    private double getXpFromString(String xpLore) {
        return Double.valueOf(getLoreSubstring(xpLore));
    }

    private String getLoreSubstring(String lore) {
        int begin = lore.indexOf(' ') + 1;
        int end = lore.indexOf('/');
        return lore.substring(begin, end);
    }

    public org.bukkit.entity.Horse spawnHorseEntity(Location location, Horse horse, Player owner) {
        World world = location.getWorld();
        org.bukkit.entity.Horse entity = (org.bukkit.entity.Horse) world.spawnEntity(location, EntityType.HORSE);
        entity.setInvulnerable(true);
        entity.setOwner(owner);
        entity.setAdult();
        entity.setCustomName(getHorseEntityName(horse, owner));
        entity.setAgeLock(true);
        entity.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        entity.setBreed(false);
        updateHorseEntity(horse, entity);
        return entity;
    }

    public void updateHorseEntity(Horse horse, org.bukkit.entity.Horse entity) {
        double velocity = 0.2 + horse.getLevel() * (1 / 150);
        double jump = 0.6 + horse.getLevel() * (1 / 50);
        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(velocity);
        entity.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(jump);
        entity.setColor(horse.getTier().getColor());
    }

    private String getHorseEntityName(Horse horse, Player owner) {
        return owner.getName() + "'s Horse";
    }

}
