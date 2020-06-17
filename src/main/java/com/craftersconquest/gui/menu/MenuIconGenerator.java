package com.craftersconquest.gui.menu;

import com.craftersconquest.util.InventoryUtil;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MenuIconGenerator {

    public ItemStack getProfileIcon(Player player) {
        String displayName = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Profile";

        return createIcon(InventoryUtil.getPlayerHead(player), displayName, InventoryUtil.createLore(
                "",
                "View your player profile and",
                "see your account stats"));
    }

    public ItemStack getSkillsIcon() {
        Material material = Material.IRON_PICKAXE;
        String displayName = ChatColor.AQUA + "" + ChatColor.BOLD + "Skills";

        return createIcon(material, displayName, InventoryUtil.createLore(
                "",
                "View your skill progression",
                "and rewards."));
    }

    public ItemStack getQuestsIcon() {
        Material material = Material.WRITABLE_BOOK;
        String displayName = ChatColor.BLUE + "" + ChatColor.BOLD + "Quests";

        return createIcon(material, displayName, InventoryUtil.createLore(
                "",
                "View your active quests, take",
                "on new assignments, and",
                "level up your skills."));
    }

    public ItemStack getGuildIcon() {
        Material material = Material.DIAMOND_SWORD;
        String displayName = ChatColor.RED + "" + ChatColor.BOLD + "Guild";

        return createIcon(material, displayName, InventoryUtil.createLore(
                "",
                "View and manage your guild."));
    }

    public ItemStack getStreaksIcon() {
        Material material = Material.PRISMARINE_CRYSTALS;
        String displayName = ChatColor.YELLOW + "" + ChatColor.BOLD + "Streaks";

        return createIcon(material, displayName, InventoryUtil.createLore(
                "",
                "View your streaks and get",
                "rewarded for logging in."));
    }

    public ItemStack getHelpIcon() {
        Material material = Material.SPRUCE_SIGN;
        String displayName = ChatColor.GOLD + "" + ChatColor.BOLD + "Help";

        return createIcon(material, displayName, InventoryUtil.createLore(
                "",
                "View crafting recipes and",
                "basic information."));
    }

    public ItemStack getWorkbenchIcon() {
        Material material = Material.CRAFTING_TABLE;
        String displayName = ChatColor.GRAY + "" + ChatColor.BOLD + "Workbench";

        return createIcon(material, displayName, InventoryUtil.createLore(
                "",
                "Visit your virtual workbench"));
    }

    private ItemStack createIcon(ItemStack item, String displayName, List<String> lore) {
        ItemBuilder itemBuilder = new ItemBuilder(item);
        itemBuilder.setDisplayName(displayName);
        lore.add("");
        lore.add(ChatColor.WHITE + "Click to open");
        itemBuilder.setLore(lore);
        itemBuilder.addItemFlag(ItemFlag.HIDE_ATTRIBUTES);
        return itemBuilder.build();
    }

    private ItemStack createIcon(Material material, String displayName, List<String> lore) {
        return createIcon(new ItemStack(material), displayName, lore);
    }
}
