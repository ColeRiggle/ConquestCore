package com.craftersconquest.visual.tracker;

import org.bukkit.entity.Player;

public abstract class AutomaticRecordTracker<T extends Number> extends RecordTracker<T> {

    public AutomaticRecordTracker(String name) {
        super(name);
    }

    public void updateRecord(Player player) {
        super.updateRecord(player, generateRecord(player));
    }

    public abstract T generateRecord(Player player);
}
