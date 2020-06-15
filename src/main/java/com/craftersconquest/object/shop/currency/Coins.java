package com.craftersconquest.object.shop.currency;

import com.craftersconquest.core.ConquestCore;
import com.craftersconquest.object.Unit;
import org.bukkit.entity.Player;

public class Coins extends Currency {

    private final ConquestCore instance;
    private final static Unit unit = new Unit("Coin", "Coins");

    public Coins(ConquestCore instance) {
        this.instance = instance;
    }

    @Override
    public double getBalance(Player player) {
        return instance.getEconomy().getBalance(player);
    }

    @Override
    public void withdraw(Player player, double amount) {
        instance.getEconomy().withdrawPlayer(player, amount);
    }

    @Override
    public void deposit(Player player, double amount) {
        instance.getEconomy().depositPlayer(player, amount);
    }

    @Override
    public String getUnit(double quantity) {
        return unit.getUnit(quantity);
    }

}
