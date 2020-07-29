package com.craftersconquest.object.guild;

import com.craftersconquest.object.forge.Type;

import java.util.HashMap;
import java.util.Map;

public class Stockpile {

    private int resourceCapacity;
    private int essenceCapacity;
    private final Map<Type, Double> amounts;

    public Stockpile(int resourceCapacity, int essenceCapacity, double metal, double grain, double crystal, double essence) {
        this.resourceCapacity = resourceCapacity;
        this.essenceCapacity = essenceCapacity;
        amounts = new HashMap<>();
        amounts.put(Type.METAL, metal);
        amounts.put(Type.GRAIN, grain);
        amounts.put(Type.CRYSTAL, crystal);
        amounts.put(Type.ESSENCE, essence);
    }

    public double getBalance(Type type) {
        return amounts.get(type);
    }

    public void add(Type type, double amount) {
        double base = amounts.get(type);
        double newAmount = base + amount;
        if (newAmount < getCapacity(type)) {
            amounts.put(type, newAmount);
        }
    }

    public int getCapacity(Type type) {
        if (type == Type.ESSENCE) {
            return essenceCapacity;
        } else {
            return resourceCapacity;
        }
    }

    public void setCapacity(Type type, int capacity) {
        if (type == Type.ESSENCE) {
            essenceCapacity = capacity;
        } else {
            resourceCapacity = capacity;
        }
    }

    public String toString() {
        return resourceCapacity + ":" + essenceCapacity + ":" +
                amounts.get(Type.GRAIN) + ":" + amounts.get(Type.METAL) + ":" +
                amounts.get(Type.CRYSTAL) + ":" + amounts.get(Type.ESSENCE);
    }
}
