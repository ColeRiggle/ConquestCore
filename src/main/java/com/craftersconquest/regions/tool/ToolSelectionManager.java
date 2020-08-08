package com.craftersconquest.regions.tool;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ToolSelectionManager {

    private Map<Player, RegionSelection> pendingSelection = new HashMap<>();

    public boolean hasSelection(Player player) {
        return pendingSelection.containsKey(player);
    }

    public void removeSelection(Player player) {
        pendingSelection.remove(player);
    }

    public RegionSelection getSelection(Player player) {
        return pendingSelection.get(player);
    }

    public void createSelection(Player player) {
        pendingSelection.put(player, new RegionSelection());
    }
}
