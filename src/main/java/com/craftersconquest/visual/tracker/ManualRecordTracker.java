package com.craftersconquest.visual.tracker;


import org.bukkit.entity.Player;

public class ManualRecordTracker<T extends Number> extends RecordTracker<T> {

    public void updateRecord(Player player, T value) {
        super.updateRecord(player, value);
    }

}
