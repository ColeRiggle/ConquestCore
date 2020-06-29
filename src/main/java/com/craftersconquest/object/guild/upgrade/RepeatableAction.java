package com.craftersconquest.object.guild.upgrade;

import com.craftersconquest.object.guild.Guild;
import com.craftersconquest.player.ConquestPlayer;

import java.util.function.Consumer;

public abstract class RepeatableAction {

    private final Consumer<Guild> oneTime;
    private final Consumer<ConquestPlayer> perPlayer;

    public RepeatableAction(Consumer<Guild> oneTime, Consumer<ConquestPlayer> perPlayer) {
        this.oneTime = oneTime;
        this.perPlayer = perPlayer;
    }

    public void oneTimeExecute(Guild guild) {
        oneTime.accept(guild);
    }

    public void execute(ConquestPlayer player) {
        perPlayer.accept(player);
    }
}
