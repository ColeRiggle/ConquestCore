package com.craftersconquest.gui.menu;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.gui.ConquestInventory;
import com.craftersconquest.object.skill.Skill;
import com.craftersconquest.skills.SkillFormatter;
import com.craftersconquest.util.InventoryUtil;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkillsSingleViewInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final SmartInventory inventory;
    private final Skill skill;
    private final SkillFormatter formatter;

    public SkillsSingleViewInventory(ConquestCore instance, SmartInventory parent, Skill skill) {
        this.instance = instance;
        this.skill = skill;
        inventory = SmartInventory.builder()
                .id("skillsSingleViewInventory")
                .provider(this)
                .size(6, 9)
                .title(ChatColor.AQUA + "Skill: " + skill.getType().getName())
                .parent(parent)
                .build();
        formatter = new SkillFormatter();
    }

    @Override
    public SmartInventory getInventory() {
        return inventory;
    }

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        InventoryUtil.fillSmartInventory(inventoryContents);
        addNavigationButtons(inventoryContents);
        addSkillLevelSnake(player, inventoryContents);
    }

    private void addNavigationButtons(InventoryContents inventoryContents) {
        InventoryUtil.addNavigationButtons(inventoryContents, inventory.getParent(), 5);
    }

    private void addSkillLevelSnake(Player player, InventoryContents inventoryContents) {
        int[] indexes = {36, 27, 18, 9, 0, 1, 2, 11, 20, 29, 30, 31, 22, 13, 4, 5, 6, 15, 24, 33, 34, 35, 26, 17, 8};

        int level = 1;
        for (int index : indexes) {
            inventoryContents.set(InventoryUtil.parseSlotPosFromInt(index),
                    ClickableItem.empty(createIconForLevel(player, level)));
            level++;
        }
    }

    private ItemStack createIconForLevel(Player player, int level) {
        ItemBuilder icon =
                new ItemBuilder(getMaterialForLevel(player, level)).
                        setDisplayName(ChatColor.WHITE + "" +
                                ChatColor.BOLD  + "Level " +
                                Skill.getRomanizedLevel(level));

        String levelDescription = formatter.getLevelDescription(skill, level);

        List<String> lore = new ArrayList<>();
        lore.add("");

        if (skill.getLevel() == level) {
            lore.add(ChatColor.WHITE + "Progress: " + formatter.getProgressBar(skill));
            lore.add("");
        }

        for (String string : levelDescription.split("\n")) {
            lore.add(string);
        }

        icon.setLore(lore);

        return icon.build();
    }

    public ItemStack getMaterialForLevel(Player player, int level) {
        if (skill.getLevel() == level) {
            return InventoryUtil.getPlayerHead(player);
        } else if (skill.getLevel() < level) {
            return new ItemStack(Material.RED_STAINED_GLASS_PANE);
        } else {
            return new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        }
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
