package com.craftersconquest.items.conquestitem;

import com.craftersconquest.forges.ForgeUtil;
import com.craftersconquest.object.forge.Forge;
import com.craftersconquest.object.forge.Tier;
import com.craftersconquest.object.forge.Type;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Item {
    METAL_FORGE(getBaseForge(Type.METAL)),
    GRAIN_FORGE(getBaseForge(Type.GRAIN)),
    CRYSTAL_FORGE(getBaseForge(Type.CRYSTAL)),
    ESSENCE_FORGE(getBaseForge(Type.ESSENCE));

    private final ConquestItem conquestItem;
    private static final Item[] itemValues = Item.values();
    private static final Map<String, Item> idItemPairs = Collections.unmodifiableMap(initializeMapping());

    Item(ConquestItem conquestItem) {
        this.conquestItem = conquestItem;
    }

    public ConquestItem getConquestItem() {
        return conquestItem;
    }

    public static final Item fromId(String id) {
        if (idItemPairs.containsKey(id)) {
            return idItemPairs.get(id);
        }

        return null;
    }

    private static Map<String, Item> initializeMapping() {
        Map<String, Item> idItemPairs = new HashMap<>();
        for (Item item : itemValues) {
            idItemPairs.put(item.getConquestItem().getId(), item);
        }

        return idItemPairs;
    }

    private static ConquestItem getBaseForge(Type type) {
        return ForgeUtil.getForgeConquestItem(new Forge(type, Tier.I));
    }
}
