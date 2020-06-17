package com.craftersconquest.gui.menu;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.gui.ConquestInventory;
import com.craftersconquest.object.skill.Skill;
import com.craftersconquest.object.skill.type.Type;
import com.craftersconquest.skills.SkillFormatter;
import com.craftersconquest.util.InventoryUtil;
import de.domedd.developerapi.itembuilder.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SkillsInventory implements ConquestInventory, InventoryProvider {

    private final ConquestCore instance;
    private final SmartInventory inventory;
    private final SkillFormatter formatter;

    public SkillsInventory(ConquestCore instance, SmartInventory parent) {
        this.instance = instance;
        inventory = SmartInventory.builder()
                .id("skillsInventory")
                .provider(this)
                .size(3, 9)
                .title(ChatColor.AQUA + "Skills")
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
        addSkillSelectionButtons(player, inventoryContents);
    }

    private void addNavigationButtons(InventoryContents inventoryContents) {
        InventoryUtil.addNavigationButtons(inventoryContents, inventory.getParent(), 2);
    }

    private void addSkillSelectionButtons(Player player, InventoryContents inventoryContents) {
        List<Skill> skills = instance.getCacheManager().getConquestPlayer(player.getUniqueId()).getSkills();

        int index = 2;
        for (Skill skill : skills) {
            inventoryContents.set(1, index, getClickableSkillIcon(skill, player));
            index++;
        }
    }

    private ClickableItem getClickableSkillIcon(Skill skill, Player player) {
        Type type = skill.getType();
        String currentLevel = ChatColor.WHITE + "" + ChatColor.BOLD + "Level " + Skill.getRomanizedLevel(skill.getLevel());
        String progressBar = formatter.getProgressBar(skill);

        ItemStack icon = new ItemBuilder(type.getIcon()).
                setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + type.getName()).
                setLore(InventoryUtil.createLore("", currentLevel, progressBar, "", "Click to view details")).
                addItemFlag(ItemFlag.HIDE_ATTRIBUTES).
                build();

        return ClickableItem.of(icon, e -> onSkillIconClick(skill, player));
    }

    private void onSkillIconClick(Skill skill, Player player) {
        SkillsSingleViewInventory skillsSingleViewInventory =
                new SkillsSingleViewInventory(instance, inventory, skill);
        skillsSingleViewInventory.getInventory().open(player);
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
