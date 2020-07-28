package com.craftersconquest.object.guild;

public class Stockpile {

    private int resourceCapacity;
    private int essenceCapacity;

    private double metal;
    private double grain;
    private double crystal;
    private double essence;

    public Stockpile(int resourceCapacity, int essenceCapacity, double metal, double grain, double crystal, double essence) {
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

    public double getMetal() {
        return metal;
    }

    public void setMetal(double metal) {
        this.metal = metal;
    }

    public double getGrain() {
        return grain;
    }

    public void setGrain(double grain) {
        this.grain = grain;
    }

    public double getCrystal() {
        return crystal;
    }

    public void setCrystal(double crystal) {
        this.crystal = crystal;
    }

    public double getEssence() {
        return essence;
    }

    public void setEssence(double essence) {
        this.essence = essence;
    }

    public String toString() {
        return resourceCapacity + ":" + essenceCapacity + ":" + grain + ":" + metal + ":" + crystal + ":" + essence;
    }
}
