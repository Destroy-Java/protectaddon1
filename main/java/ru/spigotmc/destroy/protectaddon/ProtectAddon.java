package ru.spigotmc.destroy.protectaddon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.spigotmc.destroy.protectaddon.commands.ReloadCommand;
import ru.spigotmc.destroy.protectaddon.exceptions.InvalidFormatException;
import ru.spigotmc.destroy.protectaddon.listeners.BreakRegionListener;
import ru.spigotmc.destroy.protectaddon.listeners.PSListeners;
import ru.spigotmc.destroy.protectaddon.utils.SaveUtils;

import java.util.Objects;

public final class ProtectAddon extends JavaPlugin {

    private void msg(String msg) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
    private String getVersion() {
        return Bukkit.getBukkitVersion().replace("-", "").replace("R0.1", "").replace("SNAPSHOT", "");
    }
    public static boolean getHoloVersion() {
        return Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("HolographicDisplays")).getDescription().getVersion().contains("3.0.0");
    }
    public static boolean isDecent() {
        if(Bukkit.getPluginManager().getPlugin("DecentHolograms") == null) {
            return false;
        }
        return Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("DecentHolograms")).isEnabled();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if(!isDecent()) {
            if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
                getLogger().severe("*** HolographicDisplays либо DecentHolograms не установлен или не запущен. ***");
                getLogger().severe("*** Этот плагин отключен. ***");
                this.setEnabled(false);
            }
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("ProtectionStones")) {
            getLogger().severe("*** ProtectionStones не установлен или не запущен. ***");
            getLogger().severe("*** Этот плагин отключен. ***");
            this.setEnabled(false);
        }
        if(!this.isEnabled()) {
            return;
        }
        if(!isDecent()) {
            SaveUtils.loadYamlFile(this);
            if(!SaveUtils.getHolograms().isEmpty()) {
                for (String s : SaveUtils.getHolograms()) {
                    if(getHoloVersion()) {
                        try {
                            SaveUtils.loadNewHologram(s, this);
                        } catch (InvalidFormatException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            SaveUtils.loadHologram(s, this);
                        } catch (InvalidFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        new BreakRegionListener(this);
        new PSListeners(this);
        new ReloadCommand(this);
        String prefix = "&e&lPROTECTADDON &7| &f";
        msg("");
        msg(prefix+"&aПлагин запущен &b("+getVersion()+")&a. &fВерсия: &ev"+getDescription().getVersion());
        msg(prefix+"&fРазработчик: &ahttps://vk.com/emptycsgo");
        msg("");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
