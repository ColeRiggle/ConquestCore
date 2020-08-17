package com.craftersconquest.items.conquestitem;

import com.craftersconquest.forges.ForgeUtil;
import com.craftersconquest.horses.HorseConverter;
import com.craftersconquest.object.forge.Forge;
import com.craftersconquest.object.forge.Tier;
import com.craftersconquest.object.forge.Type;
import com.craftersconquest.object.horse.Horse;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Item {
    REGION_TOOL(getRegionTool()),
    METAL_FORGE(getBaseForge(Type.METAL)),
    GRAIN_FORGE(getBaseForge(Type.GRAIN)),
    CRYSTAL_FORGE(getBaseForge(Type.CRYSTAL)),
    ESSENCE_FORGE(getBaseForge(Type.ESSENCE)),
    HORSE_I(getHorse(com.craftersconquest.object.horse.Tier.I)),
    HORSE_II(getHorse(com.craftersconquest.object.horse.Tier.II)),
    HORSE_III(getHorse(com.craftersconquest.object.horse.Tier.III)),
    HORSE_IV(getHorse(com.craftersconquest.object.horse.Tier.IV));

    private final ConquestItem conquestItem;
    private static final Item[] itemValues = Item.values();
    private static final Map<String, Item> idItemPairs = Collections.unmodifiableMap(initializeMapping());

    Item(ConquestItem conquestItem) {
        this.conquestItem = conquestItem;
    }

    public ConquestItem getConquestItem() {
        return conquestItem;
    }

    public static final Optional<Item> fromId(String id) {
        Item item = null;

        if (id != null && idItemPairs.containsKey(id)) {
            item = idItemPairs.get(id);
        }

        return Optional.ofNullable(item);
    }

    public static boolean itemStackIs(ItemStack base, Item comparison) {
        Optional<Item> wrappedItem = fromItemStack(base);
        return (wrappedItem.isPresent() && wrappedItem.get() == comparison);
    }

    public static Optional<Item> fromItemStack(ItemStack itemStack) {
        String id = NBTEditor.getString(itemStack, ConquestItem.ID_NBT_LOCATION);
        return fromId(id);
    }

    private static Map<String, Item> initializeMapping() {
        Map<String, Item> idItemPairs = new HashMap<>();
        for (Item item : itemValues) {
            idItemPairs.put(item.getConquestItem().getId(), item);
        }

        return idItemPairs;
    }

    private static ConquestItem getBaseForge(Type type) {
        return ForgeUtil.getForgeConquestItem(new Forge(type, Tier.I));
    }

    private static ConquestItem getRegionTool() {
        ItemStack itemStack = new ItemBuilder(Material.DIAMOND).
                setDisplayName(ChatColor.YELLOW + "Region Tool").
                build();
        return new NormalConquestItem("region_tool", Category.CUSTOM_TOOL, Rarity.COMMON, itemStack);
    }

    private static ConquestItem getHorse(com.craftersconquest.object.horse.Tier tier) {
        HorseConverter converter = new HorseConverter();
        return new UniqueConquestItem("horse_" + tier.toString(),
                Category.HORSE,
                Rarity.NONE,
                converter.createItemStackFromHorse(new Horse("default", tier.getMaxLevel() - 10, 0, tier)));
    }
}
