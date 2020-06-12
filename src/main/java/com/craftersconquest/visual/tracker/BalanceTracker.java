package com.craftersconquest.visual.tracker;

import com.craftersconquest.core.ConquestCore;
import org.bukkit.entity.Player;

public class BalanceTracker extends AutomaticRecordTracker<Double> {

    private final ConquestCore instance;

    public BalanceTracker(ConquestCore instance) {
        super("balance");
        this.instance = instance;
    }

    @Override
    public Double generateRecord(Player player) {
        return instance.getEconomy().getBalance(player);
    }
}
