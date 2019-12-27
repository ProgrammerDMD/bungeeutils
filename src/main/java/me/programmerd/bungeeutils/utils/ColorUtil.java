package me.programmerd.bungeeutils.utils;

import net.md_5.bungee.api.ChatColor;

public class ColorUtil {

    public static String get(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
