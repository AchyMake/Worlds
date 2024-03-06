package org.achymake.worlds.data;

import org.achymake.worlds.Worlds;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public record WorldConfig(Worlds plugin) {
    private Message getMessage() {
        return plugin.getMessage();
    }
    public boolean folderExist(String worldName) {
        return new File(plugin.getServer().getWorldContainer(), worldName).exists();
    }
    public boolean worldExist(String worldName) {
        return plugin.getServer().getWorld(worldName) != null;
    }
    public void setupWorlds() {
        File folder = new File(plugin.getDataFolder(), "worlds");
        if (folder.exists()) {
            getMessage().sendLog(Level.INFO, "worlds folder detected");
            getMessage().sendLog(Level.INFO, "tempting to create worlds");
            if (folder.list().length > 0) {
                for (File files : folder.listFiles()) {
                    String worldName = files.getName().replace(".yml", "");
                    if (worldExist(worldName)) {
                        getMessage().sendLog(Level.INFO, worldName + " already exist");
                    } else {
                        if (folderExist(worldName)) {
                            getMessage().sendLog(Level.INFO, "creating " + worldName);
                            FileConfiguration configuration = YamlConfiguration.loadConfiguration(files);
                            WorldCreator worldCreator = new WorldCreator(worldName);
                            worldCreator.environment(World.Environment.valueOf(configuration.getString("environment")));
                            worldCreator.seed(configuration.getLong("seed"));
                            worldCreator.createWorld();
                            getMessage().sendLog(Level.INFO, worldName + " has been created with " + configuration.getString("environment") + " environment");
                        } else {
                            files.delete();
                            getMessage().sendLog(Level.WARNING, worldName + " does not exist " + files.getName() + " has been deleted");
                        }
                    }
                }
            }
        } else {
            getMessage().sendLog(Level.INFO, "worlds folder undetected");
            getMessage().sendLog(Level.INFO, "tempting to create files");
            folder.mkdirs();
            for (World world : plugin.getServer().getWorlds()) {
                File file = new File(plugin.getDataFolder(), "worlds/" + world.getName() + ".yml");
                if (!file.exists()) {
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    config.addDefault("name", world.getName());
                    config.addDefault("environment", world.getEnvironment().toString());
                    config.addDefault("seed", world.getSeed());
                    config.addDefault("pvp", true);
                    config.options().copyDefaults(true);
                    try {
                        config.save(file);
                        getMessage().sendLog(Level.INFO, "created " + world.getName() + ".yml");
                    } catch (IOException e) {
                        getMessage().sendLog(Level.WARNING, e.getMessage());
                    }
                }
            }
        }
    }
    public void create(String worldName, World.Environment environment) {
        File file = new File(plugin.getDataFolder(), "worlds/" + worldName + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        WorldCreator worldCreator = new WorldCreator(worldName);
        config.addDefault("name", worldName);
        config.addDefault("environment", worldCreator.environment().toString());
        config.addDefault("seed", worldCreator.seed());
        config.addDefault("pvp", true);
        config.options().copyDefaults(true);
        try {
            config.save(file);
            worldCreator.environment(environment);
            worldCreator.createWorld();
            getMessage().sendLog(Level.INFO, "created " + worldName + ".yml");
        } catch (IOException e) {
            getMessage().sendLog(Level.WARNING, e.getMessage());
        }
    }
    public void create(String worldName, World.Environment environment, long seed) {
        File file = new File(plugin.getDataFolder(), "worlds/" + worldName + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.seed(seed);
        config.addDefault("name", worldName);
        config.addDefault("environment", worldCreator.environment().toString());
        config.addDefault("seed", seed);
        config.addDefault("pvp", true);
        config.options().copyDefaults(true);
        worldCreator.createWorld();
        try {
            config.save(file);
            worldCreator.environment(environment);
            worldCreator.createWorld();
            getMessage().sendLog(Level.INFO, "created " + worldName + ".yml");
        } catch (IOException e) {
            getMessage().sendLog(Level.WARNING, e.getMessage());
        }
    }
    public FileConfiguration getConfiguration(World world) {
        return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "worlds/" + world.getName() + ".yml"));
    }
    public boolean isPVP(World world) {
        return getConfiguration(world).getBoolean("pvp");
    }
    public void setPVP(World world, boolean value) {
        File file = new File(plugin.getDataFolder(), "worlds/" + world.getName() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("pvp", value);
        try {
            config.save(file);
            getMessage().sendLog(Level.INFO, "PVP for " + world.getName() + " has changed to " + value);
        } catch (IOException e) {
            getMessage().sendLog(Level.WARNING, e.getMessage());
        }
    }
}
