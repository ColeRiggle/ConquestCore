package com.craftersconquest.visual.tracker;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class RecordTracker<T extends Number> {

    private final Map<Player, T> records;

    public RecordTracker() {
        records = new HashMap<>();
    }

    public void removePlayer(Player player) {
        records.remove(player);
    }

    protected void updateRecord(Player player, T value) {
        records.put(player, value);
    }

    public T getRecord(Player player) {
        return records.get(player);
    }
}
