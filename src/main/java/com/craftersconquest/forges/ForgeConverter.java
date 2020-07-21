package com.craftersconquest.forges;

import com.craftersconquest.items.conquestitem.ConquestItem;
import com.craftersconquest.items.conquestitem.ExistingConquestItem;
import com.craftersconquest.object.forge.Forge;
import com.craftersconquest.object.forge.Tier;
import com.craftersconquest.object.forge.Type;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ForgeConverter {

    public Forge getForgeFromItemStack(ItemStack itemStack) {
        Optional<ConquestItem> potentialConquestItem = ExistingConquestItem.fromItemStack(itemStack);
        ConquestItem conquestItem = potentialConquestItem.get();
        String[] idComponents = conquestItem.getId().split(":");
        Type type = Type.valueOf(idComponents[1]);
        Tier tier = Tier.valueOf(idComponents[2]);
        return new Forge(type, tier);
    }
}
