package ru.spigotmc.destroy.protectaddon.utils;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import ru.spigotmc.destroy.protectaddon.ProtectAddon;

import java.util.Iterator;

public class Utils {
    public static void removeHologram(ProtectAddon main, Block block, Player player, String id) {
        if(ProtectAddon.isDecent()) {
            for(String s : eu.decentsoftware.holograms.api.holograms.Hologram.getCachedHologramNames()) {
                eu.decentsoftware.holograms.api.holograms.Hologram holo = eu.decentsoftware.holograms.api.holograms.Hologram.getCachedHologram(s);
                if(holo != null) {
                    if(holo.getLocation().getWorld() == block.getLocation().getWorld()) {
                        if (holo.getLocation().distance(block.getLocation().add(0.5, 2, 0.5)) <= 0.5) {
                            holo.delete();
                            holo.destroy();
                            Iterator<String> iterator = main.getConfig().getConfigurationSection("region-names").getKeys(false).iterator();
                            while (iterator.hasNext()) {
                                String region = iterator.next();
                                String path = "region-names." + region + ".";
                                if(block.getType().equals(Material.valueOf(region))) {
                                    if (main.getConfig().getString(path + "break-sound") != null) {
                                        try {
                                            player.playSound(player.getLocation(), Sound.valueOf(main.getConfig().getString(path + "break-sound")), 1, 1);
                                        } catch (IllegalArgumentException exception) {
                                            ConsoleLogger.nullSound(main.getConfig().getString(path + "break-sound"));
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if(ProtectAddon.getHoloVersion()) {
            for (me.filoghost.holographicdisplays.api.hologram.Hologram h : HolographicDisplaysAPI.get(main).getHolograms()) {
                if(h.getPosition().toLocation().getWorld() == block.getLocation().getWorld()) {
                    if (h.getPosition().toLocation().distance(block.getLocation().add(0.5, 2, 0.5)) <= 1.0) {
                        h.delete();
                        if (SaveUtils.getHolograms().contains(id)) {
                            SaveUtils.deleteHologram(id);
                        }
                        Iterator<String> iterator = main.getConfig().getConfigurationSection("region-names").getKeys(false).iterator();
                        while (iterator.hasNext()) {
                            String region = iterator.next();
                            String path = "region-names." + region + ".";
                            if(block.getType().equals(Material.valueOf(region))) {
                                if (main.getConfig().getString(path + "break-sound") != null) {
                                    try {
                                        player.playSound(player.getLocation(), Sound.valueOf(main.getConfig().getString(path + "break-sound")), 1, 1);
                                    } catch (IllegalArgumentException exception) {
                                        ConsoleLogger.nullSound(main.getConfig().getString(path + "break-sound"));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for (Hologram h : HologramsAPI.getHolograms(main)) {
                if(h.getLocation().getWorld() == block.getLocation().getWorld()) {
                    if (h.getLocation().distance(block.getLocation().add(0.5, 2, 0.5)) <= 1.0) {
                        h.delete();
                        if (SaveUtils.getHolograms().contains(id)) {
                            SaveUtils.deleteHologram(id);
                        }
                        Iterator<String> iterator = main.getConfig().getConfigurationSection("region-names").getKeys(false).iterator();
                        while (iterator.hasNext()) {
                            String region = iterator.next();
                            String path = "region-names." + region + ".";
                            if(block.getType().equals(Material.valueOf(region))) {
                                if (main.getConfig().getString(path + "break-sound") != null) {
                                    try {
                                        player.playSound(player.getLocation(), Sound.valueOf(main.getConfig().getString(path + "break-sound")), 1, 1);
                                    } catch (IllegalArgumentException exception) {
                                        ConsoleLogger.nullSound(main.getConfig().getString(path + "break-sound"));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void removeHologram(ProtectAddon main, Block block, String id) {
        if(ProtectAddon.isDecent()) {
            for(String s : eu.decentsoftware.holograms.api.holograms.Hologram.getCachedHologramNames()) {
                eu.decentsoftware.holograms.api.holograms.Hologram holo = eu.decentsoftware.holograms.api.holograms.Hologram.getCachedHologram(s);
                if(holo != null) {
                    if(holo.getLocation().getWorld() == block.getLocation().getWorld()) {
                        if (holo.getLocation().distance(block.getLocation().add(0.5, 2, 0.5)) <= 0.5) {
                            holo.delete();
                            holo.destroy();
                        }
                    }
                }
            }
        } else if(ProtectAddon.getHoloVersion()) {
            for (me.filoghost.holographicdisplays.api.hologram.Hologram h : HolographicDisplaysAPI.get(main).getHolograms()) {
                if(h.getPosition().toLocation().getWorld() == block.getLocation().getWorld()) {
                    if (h.getPosition().toLocation().distance(block.getLocation().add(0.5, 2, 0.5)) <= 1.0) {
                        h.delete();
                        if (SaveUtils.getHolograms().contains(id)) {
                            SaveUtils.deleteHologram(id);
                        }
                    }
                }
            }
        } else {
            for (Hologram h : HologramsAPI.getHolograms(main)) {
                if(h.getLocation().getWorld() == block.getLocation().getWorld()) {
                    if (h.getLocation().distance(block.getLocation().add(0.5, 2, 0.5)) <= 1.0) {
                        h.delete();
                        if (SaveUtils.getHolograms().contains(id)) {
                            SaveUtils.deleteHologram(id);
                        }
                    }
                }
            }
        }
    }
}
