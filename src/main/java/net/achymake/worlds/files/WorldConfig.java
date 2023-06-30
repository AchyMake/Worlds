package net.achymake.worlds.files;

import net.achymake.worlds.Worlds;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class WorldConfig {
    private final File dataFolder;
    public WorldConfig (File dataFolder) {
        this.dataFolder = dataFolder;
    }
    private Message getMessage() {
        return Worlds.getMessage();
    }
    private Worlds getPlugin() {
        return Worlds.getInstance();
    }
    public boolean fileExist(String worldName) {
        return new File(dataFolder, "worlds/" + worldName + ".yml").exists();
    }
    public boolean folderExist(String worldName) {
        return new File(Worlds.getInstance().getServer().getWorldContainer(), worldName).exists();
    }
    public boolean worldExist(String worldName) {
        return Worlds.getInstance().getServer().getWorld(worldName) != null;
    }
    public void setup() {
        File folder = new File(dataFolder, "worlds");
        if (folder.exists()) {
            if (folder.list().length > 0) {
                for (File files : folder.listFiles()) {
                    String worldName = files.getName().replace(".yml", "");
                    File worldFolder = new File(getPlugin().getServer().getWorldContainer(), worldName);
                    if (!worldExist(worldName)) {
                        if (worldFolder.exists()) {
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
            folder.mkdirs();
            for (World world : getPlugin().getServer().getWorlds()) {
                File file = new File(dataFolder, "worlds/" + world.getName() + ".yml");
                if (!file.exists()) {
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    config.addDefault("name", world.getName());
                    config.addDefault("environment", world.getEnvironment().toString());
                    config.addDefault("seed", world.getSeed());
                    config.addDefault("pvp", true);
                    config.options().copyDefaults(true);
                    try {
                        config.save(file);
                    } catch (IOException e) {
                        getMessage().sendLog(Level.WARNING, e.getMessage());
                    }
                }
            }
        }
    }
    public FileConfiguration get(String worldName) {
        return YamlConfiguration.loadConfiguration(new File(dataFolder, "worlds/" + worldName + ".yml"));
    }
    public void create(String worldName, World.Environment environment) {
        File file = new File(dataFolder, "worlds/" + worldName + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        WorldCreator worldCreator = new WorldCreator(worldName);
        config.addDefault("name", worldName);
        config.addDefault("environment", worldCreator.environment().toString());
        config.addDefault("seed", worldCreator.seed());
        config.addDefault("pvp", true);
        config.options().copyDefaults(true);
        try {
            config.save(file);
        } catch (IOException e) {
            getMessage().sendLog(Level.WARNING, e.getMessage());
        }
        worldCreator.environment(environment);
        worldCreator.createWorld();
    }
    public boolean isPVP(String worldName) {
        return get(worldName).getBoolean("pvp");
    }
    public void setPVP(String worldName, boolean value) {
        File file = new File(dataFolder, "worlds/" + worldName + ".yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("pvp", value);
        try {
            configuration.save(file);
        } catch (IOException e) {
            getMessage().sendLog(Level.WARNING, e.getMessage());
        }
    }
    public void reload() {
        File folder = new File(dataFolder, "worlds");
        for (File files : folder.listFiles()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(files);
            try {
                config.load(files);
                getMessage().sendLog(Level.INFO, "reloaded " + files.getName());
            } catch (IOException | InvalidConfigurationException e) {
                getMessage().sendLog(Level.WARNING, e.getMessage());
            }
        }
    }
}