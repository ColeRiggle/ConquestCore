package com.craftersconquest.visual.scoreboard.format;

import org.bukkit.ChatColor;

public class DefaultFormatBehavior implements FormatBehavior{

    private final static String SERVER_TITLE = ChatColor.RED + "Conquest";
    private final static String ELEMENT_PREFIX = "   ► ";
    private final static String PREFERRED_ELEMENT_COLOR = ChatColor.GRAY + "";

    @Override
    public String getServerTitle() {
        return SERVER_TITLE;
    }

    @Override
    public String getElementPrefix() {
        return ELEMENT_PREFIX;
    }

    @Override
    public String getPreferredElementColor() {
        return PREFERRED_ELEMENT_COLOR;
    }
}
