package com.craftersconquest.visual.tracker;

import org.bukkit.entity.Player;

public abstract class AutomaticRecordTracker<T extends Number> extends RecordTracker<T> {

    public void updateRecord(Player player) {
        super.updateRecord(player, getRecord(player));
    }

    public abstract T getRecord(Player player);
}
