package com.craftersconquest.core;

import org.bukkit.ChatColor;

public final class Settings {

    private Settings() { }

    public static final int DAYS_PER_YEAR = 1600;

    public static final int SEASONS_COUNT = 4;

    public static final int DAYS_PER_SEASON = DAYS_PER_YEAR / SEASONS_COUNT;

    public static final String RPG_WORLD_NAME = "Lobby";

    public static final String ADMIN_PERMISSION = "com.craftersconquest.admin";

    public static final String PLAYER_PERMISSION = "com.craftersconquest.player";

    public static final int MAX_GUILD_NAME_LENGTH = 20;

    public static final int MAX_GUILD_MEMBERS = 4;

    public static final int MAX_FORGES_PER_TYPE = 6;

    public static final int FORGE_PRODUCTION_INTERVAL = 200;

}
