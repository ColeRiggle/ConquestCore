package com.craftersconquest.menu;

import com.craftersconquest.core.ConquestCore;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MenuManager {

    private final ConquestCore instance;
    private final HashMap<Player, SmartInventory> lastInventories;

    public MenuManager(ConquestCore instance) {
        this.instance = instance;
        lastInventories = new HashMap<>();
    }



}
