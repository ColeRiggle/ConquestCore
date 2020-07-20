package com.craftersconquest.items.conquestitem;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public abstract class ConquestItem {

    public abstract String getId();
    public abstract Category getCategory();
    public abstract Rarity getRarity();
    public abstract ItemStack getItemStack();

    protected static final String ID_NBT_LOCATION = "conq_id";
    protected static final String UUID_NBT_LOCATION = "conq_uuid";
    protected static final String CATEGORY_NBT_LOCATION = "conq_category";
    protected static final String RARITY_NBT_LOCATION = "conq_rarity";

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCategory(), getRarity(), getItemStack());
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (this == anotherObject) return true;
        if (anotherObject == null) return false;
        if (!(anotherObject instanceof ConquestItem)) return false;
        return equals((ConquestItem) anotherObject);
    }

    public boolean equals(ConquestItem anotherConquestItem) {
        String firstUUID = NBTEditor.getString(getItemStack(), UUID_NBT_LOCATION);
        String secondUUID = NBTEditor.getString(anotherConquestItem.getItemStack(), UUID_NBT_LOCATION);

        boolean sameUUIDs = true;

        if (firstUUID != null && secondUUID != null) {
            sameUUIDs = firstUUID == secondUUID;
        } else if ((firstUUID != null && secondUUID == null) ||
                (firstUUID == null && secondUUID != null)) {
            sameUUIDs = false;
        }

        return sameUUIDs && (isSameType(anotherConquestItem));
    }

    public boolean isSameType(ConquestItem anotherConquestItem) {
        return this.getId() == anotherConquestItem.getId();
    }

}
