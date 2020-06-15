package com.craftersconquest.object.shop.currency;

import org.bukkit.entity.Player;

public abstract class Currency {

    abstract public double getBalance(Player player);

    abstract public void withdraw(Player player, double amount);

    abstract public void deposit(Player player, double amount);

    abstract public String getUnit(double quantity);
}
