package com.craftersconquest.object.guild;

public class Stockpile {

    private int resourceCapacity;
    private int essenceCapacity;

    private int metal;
    private int grain;
    private int crystal;
    private int essence;

    public Stockpile(int resourceCapacity, int essenceCapacity, int metal, int grain, int crystal, int essence) {
        this.resourceCapacity = resourceCapacity;
        this.essenceCapacity = essenceCapacity;
        this.metal = metal;
        this.grain = grain;
        this.crystal = crystal;
        this.essence = essence;
    }

    public int getResourceCapacity() {
        return resourceCapacity;
    }

    public void setResourceCapacity(int resourceCapacity) {
        this.resourceCapacity = resourceCapacity;
    }

    public int getEssenceCapacity() {
        return essenceCapacity;
    }

    public void setEssenceCapacity(int essenceCapacity) {
        this.essenceCapacity = essenceCapacity;
    }

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public int getGrain() {
        return grain;
    }

    public void setGrain(int grain) {
        this.grain = grain;
    }

    public int getCrystal() {
        return crystal;
    }

    public void setCrystal(int crystal) {
        this.crystal = crystal;
    }

    public int getEssence() {
        return essence;
    }

    public void setEssence(int essence) {
        this.essence = essence;
    }
}
