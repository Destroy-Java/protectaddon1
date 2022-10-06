package ru.spigotmc.destroy.protectaddon.listeners;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import dev.espi.protectionstones.event.PSCreateEvent;
import dev.espi.protectionstones.event.PSRemoveEvent;
import eu.decentsoftware.holograms.api.DHAPI;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.spigotmc.destroy.protectaddon.ProtectAddon;
import ru.spigotmc.destroy.protectaddon.utils.ConsoleLogger;
import ru.spigotmc.destroy.protectaddon.utils.HexColor;
import ru.spigotmc.destroy.protectaddon.utils.SaveUtils;
import ru.spigotmc.destroy.protectaddon.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PSListeners implements Listener {

    ProtectAddon main;

    public PSListeners(ProtectAddon main) {
        Bukkit.getPluginManager().registerEvents(this, main);
        this.main = main;
    }

    @EventHandler
    public void onPlace(PSCreateEvent e) {
        if(ProtectAddon.isDecent()) {
            List<String> list = new ArrayList<>();
            Location loc = e.getRegion().getProtectBlock().getLocation().add(0.5, 2, 0.5);
            Iterator<String> iterator = main.getConfig().getConfigurationSection("region-names").getKeys(false).iterator();
            String name = main.getConfig().getString("standart-name");
            while(iterator.hasNext()) {
                String region = iterator.next();
                String path = "region-names." + region + ".";
                try {
                    if (e.getRegion().getProtectBlock().getType().equals(Material.valueOf(region.toUpperCase()))) {
                        boolean enable = true;
                        if(main.getConfig().getString(path+"hologram") != null) {
                            enable = main.getConfig().getBoolean(path+"hologram");
                        }
                        if(enable) {
                            if (main.getConfig().getString(path + "name") != null) {
                                name = main.getConfig().getString(path + "name");
                            }
                            if (name != null) {
                                list.add(HexColor.color(name));
                            }
                            if (main.getConfig().getString(path + "place-sound") != null) {
                                try {
                                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf(main.getConfig().getString(path + "place-sound")), 1, 1);
                                } catch (IllegalArgumentException exception) {
                                    ConsoleLogger.nullSound(main.getConfig().getString(path + "place-sound"));
                                }
                            }
                            for (String s : main.getConfig().getStringList(path + "lines")) {
                                list.add(HexColor.color(s.replace("%player%", e.getPlayer().getName())));
                            }
                            DHAPI.createHologram(e.getRegion().getId(), loc, true, list);
                        }
                        return;
                    }
                } catch (IllegalArgumentException exception) {
                    ConsoleLogger.nullItem(iterator.next());
                }
            }
            if (name != null) {
                list.add(HexColor.color(name));
            }
            for (String s : main.getConfig().getStringList("default-lines")) {
                list.add(HexColor.color(s.replace("%player%", e.getPlayer().getName())));
            }
            DHAPI.createHologram(e.getRegion().getId(), loc, true, list);
        } else if(ProtectAddon.getHoloVersion()) {
            Location loc = e.getRegion().getProtectBlock().getLocation().add(0.5, 2, 0.5);
            me.filoghost.holographicdisplays.api.hologram.Hologram holo = HolographicDisplaysAPI.get(main).createHologram(loc);
            Iterator<String> iterator = main.getConfig().getConfigurationSection("region-names").getKeys(false).iterator();
            String name = HexColor.color(main.getConfig().getString("standart-name"));
            while(iterator.hasNext()) {
                String region = iterator.next();
                String path = "region-names." + region + ".";
                if(e.getRegion().getProtectBlock().getType().equals(Material.valueOf(region.toUpperCase()))) {
                    boolean enable = true;
                    if(main.getConfig().getString(path+"hologram") != null) {
                        enable = main.getConfig().getBoolean(path+"hologram");
                    }
                    if(enable) {
                        if (main.getConfig().getString(path + "name") != null) {
                            name = HexColor.color(main.getConfig().getString(path + "name"));
                        }
                        if (main.getConfig().getString(path + "place-sound") != null) {
                            try {
                                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf(main.getConfig().getString(path + "place-sound")), 1, 1);
                            } catch (IllegalArgumentException exception) {
                                ConsoleLogger.nullSound(main.getConfig().getString(path + "place-sound"));
                            }
                        }
                        holo.getLines().appendText(name);
                        for (String s : main.getConfig().getStringList(path + "lines")) {
                            holo.getLines().appendText(HexColor.color(s.replace("%player%", e.getPlayer().getName())));
                        }
                        SaveUtils.saveHologram(holo, e.getRegion().getId());
                    }
                    return;
                }
            }
            holo.getLines().appendText(name);
            for (String s : main.getConfig().getStringList("default-lines")) {
                holo.getLines().appendText(HexColor.color(s.replace("%player%", e.getPlayer().getName())));
            }
            SaveUtils.saveHologram(holo, e.getRegion().getId());
        } else {
            Location loc = e.getRegion().getProtectBlock().getLocation().add(0.5, 2, 0.5);
            Hologram holo = HologramsAPI.createHologram(main, loc);
            Iterator<String> iterator = main.getConfig().getConfigurationSection("region-names").getKeys(false).iterator();
            String name = HexColor.color(main.getConfig().getString("standart-name"));
            while (iterator.hasNext()) {
                String region = iterator.next();
                String path = "region-names." + region + ".";
                if (e.getRegion().getProtectBlock().getType().equals(Material.valueOf(region.toUpperCase()))) {
                    boolean enable = true;
                    if(main.getConfig().getString(path+"hologram") != null) {
                        enable = main.getConfig().getBoolean(path+"hologram");
                    }
                    if(enable) {
                        if (main.getConfig().getString(path + "name") != null) {
                            name = HexColor.color(main.getConfig().getString(path + "name"));
                        }
                        if (main.getConfig().getString(path + "place-sound") != null) {
                            try {
                                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf(main.getConfig().getString(path + "place-sound")), 1, 1);
                            } catch (IllegalArgumentException exception) {
                                ConsoleLogger.nullSound(main.getConfig().getString(path + "place-sound"));
                            }
                        }
                        holo.appendTextLine(name);
                        for (String s : main.getConfig().getStringList(path + "lines")) {
                            holo.appendTextLine(HexColor.color(s.replace("%player%", e.getPlayer().getName())));
                        }
                        SaveUtils.saveHologram(holo, e.getRegion().getId());
                    }
                    return;
                }
            }
            holo.appendTextLine(name);
            for (String s : main.getConfig().getStringList("default-lines")) {
                holo.appendTextLine(HexColor.color(s.replace("%player%", e.getPlayer().getName())));
            }
            SaveUtils.saveHologram(holo, e.getRegion().getId());
        }
    }
    @EventHandler
    public void onRemove(PSRemoveEvent e) {
        Utils.removeHologram(main, e.getRegion().getProtectBlock(), e.getPlayer(), e.getRegion().getId());
    }
}