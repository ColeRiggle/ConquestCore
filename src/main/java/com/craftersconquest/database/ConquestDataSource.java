package com.craftersconquest.database;

import com.craftersconquest.objects.Guild;
import com.craftersconquest.player.ConquestPlayer;

public abstract class ConquestDataSource {

    abstract public ConquestPlayer loadPlayer(String UUID);
    abstract public Guild loadGuild(String name);

}
