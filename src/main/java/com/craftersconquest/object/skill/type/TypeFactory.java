package com.craftersconquest.object.skill.type;

import com.craftersconquest.object.skill.Ability;
import com.craftersconquest.object.skill.Reward;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeFactory {

    public final static List<String> TYPES =
            new ArrayList<>(List.of(
                    "mining",
                    "farming",
                    "foraging",
                    "combat",
                    "enchanting"));

    private final static List<Reward> MINING_REWARDS =
            new ArrayList<>(List.of(
                    Reward.fromLevelAndDescription(0, "Mine Stone"),
                    Reward.fromLevelAndDescription(0, "Mine Coal Ore"),
                    Reward.fromLevelAndDescription(3, "Mine Iron Ore"),
                    Reward.fromLevelAndDescription(5, "Mine Lapis Ore"),
                    Reward.fromLevelAndDescription(7, "Mine Redstone Ore"),
                    Reward.fromLevelAndDescription(10, "Mine Netherrack"),
                    Reward.fromLevelAndDescription(10, "Mine Quartz"),
                    Reward.fromLevelAndDescription(10, "Mine Gold Ore"),
                    Reward.fromLevelAndDescription(12, "Mine Diamond Ore"),
                    Reward.fromLevelAndDescription(13, "Mine Obsidian"),
                    Reward.fromLevelAndDescription(15, "Mine Emerald")));

    private static final Map<Material,Double> MINING_TRACKED = Map.ofEntries(
            Map.entry(Material.STONE, 0.2),
            Map.entry(Material.COAL_ORE,1.0),
            Map.entry(Material.IRON_ORE,2.0),
            Map.entry(Material.LAPIS_ORE,2.5),
            Map.entry(Material.REDSTONE_ORE,3.0),
            Map.entry(Material.NETHERRACK,1.0),
            Map.entry(Material.NETHER_QUARTZ_ORE,4.0),
            Map.entry(Material.GOLD_ORE,5.0),
            Map.entry(Material.DIAMOND_ORE,10.0),
            Map.entry(Material.OBSIDIAN,15.0),
            Map.entry(Material.EMERALD_ORE,15.0)
    );

    private final static List<Reward> FARMING_REWARDS =
            new ArrayList<>(List.of(
                    Reward.fromLevelAndDescription(0, "Harvest Wheat"),
                    Reward.fromLevelAndDescription(1, "Harvest Potatoes"),
                    Reward.fromLevelAndDescription(3, "Harvest Carrots"),
                    Reward.fromLevelAndDescription(3, "Harvest Beetroot"),
                    Reward.fromLevelAndDescription(5, "Harvest Melons"),
                    Reward.fromLevelAndDescription(5, "Harvest Pumpkins"),
                    Reward.fromLevelAndDescription(7, "Harvest Cactus"),
                    Reward.fromLevelAndDescription(8, "Harvest Sugar Cane"),
                    Reward.fromLevelAndDescription(14, "Harvest Mushrooms")));

    private static final Map<Material,Double> FARMING_TRACKED = Map.ofEntries(
            Map.entry(Material.WHEAT, 1.0),
            Map.entry(Material.POTATOES,1.0),
            Map.entry(Material.CARROTS,1.0),
            Map.entry(Material.BEETROOT,2.0),
            Map.entry(Material.MELON,4.0),
            Map.entry(Material.PUMPKIN,5.0),
            Map.entry(Material.BAMBOO,0.2),
            Map.entry(Material.COCOA_BEANS,5.0),
            Map.entry(Material.CACTUS,0.2),
            Map.entry(Material.SUGAR_CANE,2.0),
            Map.entry(Material.BROWN_MUSHROOM,8.0),
            Map.entry(Material.RED_MUSHROOM,8.0),
            Map.entry(Material.NETHER_WART,5.0)
    );

    private final static List<Reward> FORAGING_REWARDS =
            new ArrayList<>(List.of(
                    Reward.fromLevelAndDescription(0, "Chop Oak Trees"),
                    Reward.fromLevelAndDescription(3, "Chop Birch Trees"),
                    Reward.fromLevelAndDescription(5, "Chop Spruce Trees"),
                    Reward.fromLevelAndDescription(5, "Chop Dark Oak Trees"),
                    Reward.fromLevelAndDescription(8, "Chop Jungle Trees"),
                    Reward.fromLevelAndDescription(12, "Chop Acacia Trees")));

    private static final Map<Material,Double> FORAGING_TRACKED = Map.ofEntries(
            Map.entry(Material.OAK_LOG, 1.0),
            Map.entry(Material.BIRCH_LOG,1.0),
            Map.entry(Material.SPRUCE_LOG,1.0),
            Map.entry(Material.DARK_OAK_LOG,2.0),
            Map.entry(Material.JUNGLE_LOG,4.0),
            Map.entry(Material.ACACIA_LOG,5.0)
    );

    private final static List<Reward> ENCHANTING_REWARDS =
            new ArrayList<>(List.of());

    private static final Map<Material,Double> ENCHANTING_TRACKED = Map.ofEntries();

    private final static List<Reward> COMBAT_REWARDS =
            new ArrayList<>(List.of());

    private static final Map<Material,Double> COMBAT_TRACKED = Map.ofEntries();

    private static final Type MINING = new Type("Mining", Material.IRON_PICKAXE, Ability.EXCAVATOR, MINING_REWARDS, MINING_TRACKED);
    private static final Type FARMING = new Type("Farming", Material.STONE_HOE, Ability.HARVESTER, FARMING_REWARDS, FARMING_TRACKED);
    private static final Type FORAGING = new Type("Foraging", Material.GOLDEN_AXE, Ability.FORAGER, FORAGING_REWARDS, FORAGING_TRACKED);
    private static final Type ENCHANTING = new Type("Enchanting", Material.ENCHANTED_BOOK, Ability.SORCERER, ENCHANTING_REWARDS, ENCHANTING_TRACKED);
    private static final Type COMBAT = new Type("Combat", Material.DIAMOND_SWORD, Ability.OUTLAW, COMBAT_REWARDS, COMBAT_TRACKED);

    public Type getType(String name) {
        switch (name) {
            case "mining":
                return MINING;
            case "farming":
                return FARMING;
            case "foraging":
                return FORAGING;
            case "enchanting":
                return ENCHANTING;
            case "combat":
                return COMBAT;
        }

        return null;
    }

}
