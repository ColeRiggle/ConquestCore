package com.craftersconquest.object;

import org.bukkit.entity.Player;

public abstract class Currency {

    abstract public void getBalance(Player player);

    abstract public void withdraw(Player player);

    abstract public void deposit(Player player);
}
