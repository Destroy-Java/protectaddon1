package ru.spigotmc.destroy.protectaddon.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ConsoleLogger {

    private static void msg(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void nullItem(String item) {
        msg("&cНеизвестный предмет: "+item+", возможно, версия сервера");
        msg("&cне поддерживает его, либо его не существует.");
    }
    public static void nullSound(String sound) {
        msg("&cНеизвестный звук: "+sound+", возможно, версия сервера");
        msg("&cне поддерживает его, либо его не существует.");
    }

}
