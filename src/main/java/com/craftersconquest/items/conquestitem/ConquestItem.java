package com.craftersconquest.items.conquestitem;

import org.bukkit.inventory.ItemStack;

public abstract class ConquestItem {

    public abstract String getId();
    public abstract Category getCategory();
    public abstract Rarity getRarity();
    public abstract ItemStack getItemStack();

    protected static final String ID_NBT_LOCATION = "conq_id";
    protected static final String UUID_NBT_LOCATION = "conq_uuid";
    protected static final String CATEGORY_NBT_LOCATION = "conq_category";
    protected static final String RARITY_NBT_LOCATION = "conq_rarity";
}
