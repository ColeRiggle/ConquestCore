package com.craftersconquest.blocklist;

import org.bukkit.Material;

import java.util.ArrayList;

public class BlocklistMaterialTracker {

    private final ArrayList<Material> trackedMaterials;

    public BlocklistMaterialTracker() {
        trackedMaterials = new ArrayList<>();
        generateMaterials();
    }

    private void generateMaterials() {
        trackedMaterials.add(Material.SUGAR_CANE);
    }

    public boolean contains(Material material) {
        return trackedMaterials.contains(material);
    }
}
