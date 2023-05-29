package net.achymake.worlds.files;

import net.achymake.worlds.Worlds;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldConfig {
    private final Worlds worlds;
    public WorldConfig (Worlds worlds) {
        this.worlds = worlds;
    }
    private final Message message = Worlds.getMessage();
    private static final List<Player> worldEditors = new ArrayList<>();
    public boolean fileExist(String worldName) {
        return new File(worlds.getDataFolder(), "worlds/" + worldName + ".yml").exists();
    }
    public boolean folderExist(String worldName) {
        return new File(worlds.getServer().getWorldContainer(), worldName).exists();
    }
    public boolean worldExist(String worldName) {
        return worlds.getServer().getWorld(worldName) != null;
    }
    public void setup() {
        File folder = new File(worlds.getDataFolder(), "worlds");
        if (folder.exists()) {
            if (folder.list().length > 0) {
                for (File files : folder.listFiles()) {
                    String worldName = files.getName().replace(".yml", "");
                    File worldFolder = new File(worlds.getServer().getWorldContainer(), worldName);
                    if (worlds.getServer().getWorld(worldName) == null) {
                        if (worldFolder.exists()) {
                            message.sendLog("creating " + worldName);
                            FileConfiguration configuration = YamlConfiguration.loadConfiguration(files);
                            WorldCreator worldCreator = new WorldCreator(worldName);
                            worldCreator.environment(World.Environment.valueOf(configuration.getString("environment")));
                            worldCreator.seed(configuration.getLong("seed"));
                            worldCreator.createWorld();
                            message.sendLog(worldName+" has been created with " + configuration.getString("environment") + " environment");
                        } else {
                            files.delete();
                            message.sendLog(worldName + " does not exist " + files.getName() + " has been deleted");
                        }
                    }
                }
            }
        } else {
            folder.mkdirs();
            for (World world : worlds.getServer().getWorlds()) {
                File file = new File(worlds.getDataFolder(), "worlds/" + world.getName() + ".yml");
                if (!file.exists()) {
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    config.addDefault("name", world.getName());
                    config.addDefault("environment", world.getEnvironment().toString());
                    config.addDefault("seed", world.getSeed());
                    config.addDefault("pvp", true);
                    config.addDefault("disable.physicals.FARMLAND", true);
                    config.addDefault("disable.physicals.TURTLE_EGG", true);
                    config.addDefault("disable.events.SHEEP", true);
                    config.addDefault("disable.events.ENDERMAN", true);
                    config.addDefault("disable.events.ENDER_DRAGON", true);
                    config.addDefault("disable.events.WITHER", true);
                    config.addDefault("disable.events.WITHER_SKULL", true);
                    config.addDefault("disable.events.RAVAGER", true);
                    config.addDefault("disable.events.WARDEN", true);
                    config.addDefault("disable.events.CREEPER", true);
                    config.addDefault("disable.events.PRIMED_TNT", true);
                    config.addDefault("disable.events.MINECART_TNT", true);
                    config.options().copyDefaults(true);
                    try {
                        config.save(file);
                    } catch (IOException e) {
                        message.sendLog(e.getMessage());
                    }
                }
            }
        }
    }
    public FileConfiguration get(String worldName) {
        return YamlConfiguration.loadConfiguration(new File(worlds.getDataFolder(), "worlds/" + worldName + ".yml"));
    }
    public void create(String worldName, World.Environment environment) {
        File file = new File(worlds.getDataFolder(), "worlds/" + worldName + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        WorldCreator worldCreator = new WorldCreator(worldName);
        config.addDefault("name", worldName);
        config.addDefault("environment", worldCreator.environment().toString());
        config.addDefault("seed", worldCreator.seed());
        config.addDefault("pvp", true);
        config.addDefault("disable.physicals.FARMLAND", true);
        config.addDefault("disable.physicals.TURTLE_EGG", true);
        config.addDefault("disable.events.SHEEP", true);
        config.addDefault("disable.events.ENDERMAN", true);
        config.addDefault("disable.events.ENDER_DRAGON", true);
        config.addDefault("disable.events.WITHER", true);
        config.addDefault("disable.events.WITHER_SKULL", true);
        config.addDefault("disable.events.RAVAGER", true);
        config.addDefault("disable.events.WARDEN", true);
        config.addDefault("disable.events.CREEPER", true);
        config.addDefault("disable.events.PRIMED_TNT", true);
        config.addDefault("disable.events.MINECART_TNT", true);
        config.options().copyDefaults(true);
        try {
            config.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
        worldCreator.environment(environment);
        worldCreator.createWorld();
    }
    public boolean isPVP(String worldName) {
        return get(worldName).getBoolean("pvp");
    }
    public boolean isPhysicalsCancelled(String worldName, String materialType) {
        return get(worldName).getBoolean("disable.physicals." + materialType);
    }
    public boolean isEntityCancelled(String worldName, EntityType entityType) {
        return get(worldName).getBoolean("disable.events." + entityType.toString());
    }
    public boolean isSpawnCancelled(String worldName, EntityType entityType) {
        return get(worldName).getBoolean("disable.spawn." + entityType.toString());
    }
    public boolean isRedstoneCancelled(String worldName) {
        return get(worldName).getBoolean("disable.redstone");
    }
    public void setBoolean(String worldName, String type, Boolean value) {
        File file = new File(worlds.getDataFolder(), "worlds/" + worldName + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (value) {
            config.set(type, true);
            try {
                config.save(file);
            } catch (IOException e) {
                message.sendLog(e.getMessage());
            }
        } else {
            config.set(type, null);
            try {
                config.save(file);
            } catch (IOException e) {
                message.sendLog(e.getMessage());
            }
        }
    }
    public void setPVP(String worldName, boolean value) {
        File file = new File(worlds.getDataFolder(), "worlds/" + worldName + ".yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("pvp", value);
        try {
            configuration.save(file);
        } catch (IOException e) {
            message.sendLog(e.getMessage());
        }
    }
    public void reload() {
        File folder = new File(worlds.getDataFolder(), "worlds");
        for (String files : folder.list()) {
            File file = new File(worlds.getDataFolder(), "worlds/" + files);
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            try {
                config.load(file);
                config.options().copyDefaults(true);
                config.save(file);
            } catch (IOException | InvalidConfigurationException e) {
                message.sendLog(e.getMessage());
            }
        }
    }
    public List<Player> getWorldEditors() {
        return worldEditors;
    }
}