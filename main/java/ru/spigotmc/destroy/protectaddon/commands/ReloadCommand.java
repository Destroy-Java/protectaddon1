package ru.spigotmc.destroy.protectaddon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.spigotmc.destroy.protectaddon.ProtectAddon;
import ru.spigotmc.destroy.protectaddon.utils.HexColor;

public class ReloadCommand implements CommandExecutor {

    ProtectAddon main;
    public ReloadCommand(ProtectAddon main) {
        main.getCommand("protectaddon").setExecutor(this);
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("protectaddon.reload")) {
            sender.sendMessage(HexColor.color(main.getConfig().getString("no-perm-message")));
            return true;
        }
        if(args.length < 1) {
            sender.sendMessage(HexColor.color("#fb0000Недостаточно аргументов."));
            return true;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            main.reloadConfig();
            sender.sendMessage(HexColor.color("#1dfb2cКонфигурация перезагружена."));
        } else {
            sender.sendMessage(HexColor.color("&fИспользуйте #1dfb2c/pa reload"));
        }
        return true;
    }
}
