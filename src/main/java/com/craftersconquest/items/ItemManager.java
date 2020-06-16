package com.craftersconquest.items;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.items.heads.HeaddatabaseLoader;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    private final ConquestCore instance;
    private final HeaddatabaseLoader loader;
    private final List<ConquestItem> items;

    public ItemManager(ConquestCore instance) {
        this.instance = instance;
        loader = new HeaddatabaseLoader();
        items = new ArrayList<>();
        addItems();
    }

    private void addItems() {
        items.addAll(loader.generate());
    }

    public void update() {
        loader.generate();
    }
}
