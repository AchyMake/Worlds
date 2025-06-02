package org.achymake.worlds.handlers;

import org.achymake.worlds.Worlds;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldHandler {
    private Worlds getInstance() {
        return Worlds.getInstance();
    }
    private CreatorHandler getCreator() {
        return getInstance().getCreatorHandler();
    }
    public File getFile(String worldName) {
        return new File(getInstance().getDataFolder(), "world/" + worldName + ".yml");
    }
    public boolean exists(String worldName) {
        return getFile(worldName).exists();
    }
    public FileConfiguration getConfig(String worldName) {
        return YamlConfiguration.loadConfiguration(getFile(worldName));
    }
    public World get(String worldName) {
        return getInstance().getServer().getWorld(worldName);
    }
    public File getWorldFolder(String worldName) {
        return new File(getInstance().getServer().getWorldContainer(), worldName);
    }
    public List<String> getListed() {
        var listed = new ArrayList<String>();
        getInstance().getServer().getWorlds().forEach(world -> listed.add(world.getName()));
        return listed;
    }
    public void setup() {
        var worlds = new File(getInstance().getDataFolder(), "world");
        if (worlds.exists() && worlds.isDirectory()) {
            for (var file : worlds.listFiles()) {
                var worldName = file.getName().replace(".yml", "");
                if (get(worldName) == null) {
                    if (getWorldFolder(worldName).exists()) {
                        var info = getCreator().add(worldName);
                        getInstance().sendInfo(info.getName() + " has been added with the following:");
                        getInstance().sendInfo("environment: " + info.getEnvironment().name());
                        getInstance().sendInfo("seed: " + info.getSeed());
                    } else if (file.delete()) {
                        getInstance().sendWarning(worldName + " does not exist " + file.getName() + " has been deleted");
                    }
                }
            }
        }
    }
    public void reload() {
        var folder = new File(getInstance().getDataFolder(), "world");
        if (folder.exists() && folder.isDirectory()) {
            for (var files : folder.listFiles()) {
                if (files.exists() && files.isFile()) {
                    var config = YamlConfiguration.loadConfiguration(files);
                    try {
                        config.load(files);
                    } catch (IOException | InvalidConfigurationException e) {
                        getInstance().sendWarning(e.getMessage());
                    }
                }
            }
        }
    }
    private void setup(World world) {
        var worldName = world.getName();
        var file = getFile(worldName);
        var config = YamlConfiguration.loadConfiguration(file);
        config.set("display-name", worldName);
        config.set("environment", world.getEnvironment().toString());
        config.set("seed", world.getSeed());
        try {
            config.save(file);
        } catch (IOException e) {
            getInstance().sendWarning(e.getMessage());
        }
    }
    public void reload(String worldName) {
        var world = get(worldName);
        if (exists(worldName)) {
            var file = getFile(worldName);
            try {
                getConfig(worldName).load(file);
            } catch (IOException | InvalidConfigurationException e) {
                getInstance().sendWarning(e.getMessage());
            }
        } else setup(world);
    }
    public boolean setDifficulty(String worldName, String difficultyString) {
        var world = get(worldName);
        if (world != null) {
            if (difficultyString.equalsIgnoreCase("peaceful")) {
                world.setDifficulty(Difficulty.PEACEFUL);
                return true;
            } else if (difficultyString.equalsIgnoreCase("easy")) {
                world.setDifficulty(Difficulty.EASY);
                return true;
            } else if (difficultyString.equalsIgnoreCase("normal")) {
                world.setDifficulty(Difficulty.NORMAL);
                return true;
            } else if (difficultyString.equalsIgnoreCase("hard")) {
                world.setDifficulty(Difficulty.HARD);
                return true;
            } else return false;
        } else return false;
    }
    public void remove(String worldName) {
        var world = get(worldName);
        if (world != null) {
            if (exists(worldName)) {
                getFile(worldName).delete();
            }
            getInstance().getServer().unloadWorld(world, true);
        }
    }
    public String getDisplayName(String worldName) {
        var world = get(worldName);
        if (world != null) {
            if (exists(worldName)) {
                if (getConfig(worldName).isString("display-name")) {
                    return getConfig(worldName).getString("display-name");
                } else return worldName;
            } else return worldName;
        } else return worldName;
    }
    public World.Environment getEnvironment(String worldName) {
        var world = get(worldName);
        if (world != null) {
            return world.getEnvironment();
        } else return null;
    }
    public long getSeed(String worldName) {
        var world = get(worldName);
        if (world != null) {
            return get(worldName).getSeed();
        } else return 0;
    }
}