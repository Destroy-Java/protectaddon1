package ru.spigotmc.destroy.protectaddon.utils;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import ru.spigotmc.destroy.protectaddon.ProtectAddon;
import ru.spigotmc.destroy.protectaddon.exceptions.InvalidFormatException;
import ru.spigotmc.destroy.protectaddon.exceptions.WorldNotFoundException;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SaveUtils {
    private static FileConfiguration config;
    private static File file;
    private static final DecimalFormat numberFormat = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.ROOT));

    public static void loadYamlFile(Plugin plugin) {
        file = new File(plugin.getDataFolder(), "database.yml");
        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();
            plugin.saveResource("database.yml", true);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void loadHologram(String name, ProtectAddon main) throws InvalidFormatException {
        List<String> lines = config.getStringList(name+".lines");
        String locationString = config.getString(name+".location");
        if (locationString != null && lines.size() != 0) {
            Location loc = locationFromString(locationString);
            Hologram holo = HologramsAPI.createHologram(main, loc);
            for (String line : lines) {
                holo.appendTextLine(HexColor.color(line));
            }
        }
    }
    public static void loadNewHologram(String name, ProtectAddon main) throws InvalidFormatException {
        List<String> lines = config.getStringList(name+".lines");
        String locationString = config.getString(name+".location");
        if (locationString != null && lines.size() != 0) {
            Location loc = locationFromString(locationString);
            me.filoghost.holographicdisplays.api.hologram.Hologram holo = HolographicDisplaysAPI.get(main).createHologram(loc);
            for (String line : lines) {
                holo.getLines().appendText(HexColor.color(line));
            }
        }
    }

    public static void saveHologram(Hologram holo, String name) {
        List<String> linesList = new ArrayList<>();
        ConfigurationSection cfg = getOrCreateSection(name);
        cfg.set("location", locationToString(holo.getLocation()));
        for(int i = 0; i < holo.size(); i++) {
            linesList.add(holo.getLine(i).toString().replace("CraftTextLine ", "").replace("[text=", "").replace("]", ""));
        }
        cfg.set("lines", linesList);
        try{
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveHologram(me.filoghost.holographicdisplays.api.hologram.Hologram holo, String name) {
        List<String> linesList = new ArrayList<>();
        ConfigurationSection cfg = getOrCreateSection(name);
        cfg.set("location", locationToString(holo.getPosition().toLocation()));
        for(int i = 0; i < holo.getLines().size(); i++) {
            linesList.add(holo.getLines().get(i).toString().replace("TextLine{text=", "").replace("}", ""));
        }
        cfg.set("lines", linesList);
        try{
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<String> getHolograms() {
        return config.getKeys(false);
    }

    private static ConfigurationSection getOrCreateSection(String name) {
        return config.isConfigurationSection(name) ? config.getConfigurationSection(name) : config.createSection(name);
    }

    public static void deleteHologram(String name) {
        config.set(name, null);
        try{
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String locationToString(Location loc) {
        return loc.getWorld().getName() + ", " + numberFormat.format(loc.getX()) + ", " + numberFormat.format(loc.getY()) + ", " + numberFormat.format(loc.getZ());
    }
    private static Location locationFromString(String input) throws InvalidFormatException {
        if (input == null) {
            throw new InvalidFormatException();
        } else {
            String[] parts = input.split(",");
            if (parts.length != 4) {
                throw new InvalidFormatException();
            } else {
                try {
                    double x = Double.parseDouble(parts[1].replace(" ", ""));
                    double y = Double.parseDouble(parts[2].replace(" ", ""));
                    double z = Double.parseDouble(parts[3].replace(" ", ""));
                    World world = Bukkit.getWorld(parts[0].trim());
                    if (world == null) {
                        throw new WorldNotFoundException(parts[0].trim());
                    } else {
                        return new Location(world, x, y, z);
                    }
                } catch (NumberFormatException | WorldNotFoundException e) {
                    throw new InvalidFormatException();
                }
            }
        }
    }
}