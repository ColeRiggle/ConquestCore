package com.craftersconquest.forges;

import com.craftersconquest.items.conquestitem.Category;
import com.craftersconquest.items.conquestitem.ConquestItem;
import com.craftersconquest.items.conquestitem.NormalConquestItem;
import com.craftersconquest.items.conquestitem.Rarity;
import com.craftersconquest.object.forge.Forge;
import com.craftersconquest.object.forge.Tier;
import com.craftersconquest.object.forge.Type;
import com.craftersconquest.util.InventoryUtil;
import com.craftersconquest.util.StringUtil;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class ForgeUtil {

    private ForgeUtil() {}

    public static final ConquestItem getForgeConquestItem(Forge forge) {
        String id = "forge:" + forge.getType().toString() + ":" + forge.getType().toString();
        return new NormalConquestItem(id, Category.FORGE, Rarity.STELLAR, getForgeItemStack(forge));
    }

    private static final ItemStack getForgeItemStack(Forge forge) {
        Type type = forge.getType();
        Tier tier = forge.getTier();
        String formattedType = StringUtil.getProperNounForm(type.toString());
        String displayName = ChatColor.GOLD + formattedType + " Forge";
        List<String> lore = InventoryUtil.createLore("Place this forge in your guild",
                "to begin generating " + formattedType + ".",
                "",
                ChatColor.AQUA + "Tier: " + tier.toString(),
                ChatColor.RED + "Production Rate: " + tier.getProductionRate());
        return new ItemBuilder(Material.BLAST_FURNACE).
                setDisplayName(displayName).
                setLore(lore).build();
    }
}
