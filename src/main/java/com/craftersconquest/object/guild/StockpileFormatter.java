package com.craftersconquest.object.guild;

import com.craftersconquest.object.forge.Type;

import java.util.HashMap;
import java.util.Map;

public class StockpileFormatter {

    public Map<Type, String> getResourcesQuantities(Stockpile stockpile, String chatColor) {
        Map<Type, String> resources = new HashMap<>();
        for (Type type : Type.getValues()) {
             resources.put(type, getResourceQuantity(stockpile, type, chatColor));
        }
        return resources;
    }

    public String getResourceQuantity(Stockpile stockpile, Type type, String chatColor) {
        return chatColor + stockpile.getBalance(type) + " / " + stockpile.getCapacity(type);
    }
}
