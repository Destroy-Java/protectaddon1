package ru.spigotmc.destroy.protectaddon.listeners;

import dev.espi.protectionstones.PSRegion;
import dev.espi.protectionstones.ProtectionStones;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import ru.spigotmc.destroy.protectaddon.ProtectAddon;
import ru.spigotmc.destroy.protectaddon.utils.ConsoleLogger;
import ru.spigotmc.destroy.protectaddon.utils.HexColor;
import ru.spigotmc.destroy.protectaddon.utils.Utils;

import java.util.Iterator;

public class BreakRegionListener implements Listener {

    ProtectAddon main;
    public BreakRegionListener(ProtectAddon main) {
        Bukkit.getPluginManager().registerEvents(this, main);
        this.main = main;
    }

    @EventHandler
    public void onBreak(BlockExplodeEvent e) {
        if(ProtectionStones.isProtectBlock(e.getBlock())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChange(EntityChangeBlockEvent e) {
        if(ProtectionStones.isProtectBlock(e.getBlock())) {
            if (e.getEntity() instanceof Wither) {
                Iterator<String> iterator = main.getConfig().getConfigurationSection("region-names").getKeys(false).iterator();
                boolean enable = false;
                while (iterator.hasNext()) {
                    String region = iterator.next();
                    String path = "region-names." + region + ".wither-break";
                    if (e.getBlock().getType().equals(Material.valueOf(region))) {
                        if (main.getConfig().getString(path) != null) {
                            enable = main.getConfig().getBoolean(path);
                        }
                    }
                }
                if (enable) {
                    PSRegion region = PSRegion.fromLocation(e.getBlock().getLocation());
                    if (region == null) {
                        return;
                    }
                    Utils.removeHologram(main, e.getBlock(), region.getId());
                    region.deleteRegion(true);
                } else {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        PSRegion region = PSRegion.fromLocation(e.getBlock().getLocation());
        if(region != null && region.getOwners().contains(e.getPlayer().getUniqueId()) || region != null && region.getMembers().contains(e.getPlayer().getUniqueId())) {
            Iterator<String> iterator = main.getConfig().getConfigurationSection("region-names").getKeys(false).iterator();
            while(iterator.hasNext()) {
                try {
                    if (e.getBlock().getType().equals(Material.valueOf(iterator.next()))) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(HexColor.color(main.getConfig().getString("merge-region")));
                        return;
                    }
                } catch (IllegalArgumentException exception) {
                    ConsoleLogger.nullItem(iterator.next());
                    return;
                }
            }
        }
    }
}
