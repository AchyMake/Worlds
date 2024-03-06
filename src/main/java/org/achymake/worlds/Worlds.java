package org.achymake.worlds;

import org.achymake.worlds.commands.WorldsCommand;
import org.achymake.worlds.data.Message;
import org.achymake.worlds.data.WorldConfig;
import org.achymake.worlds.listeners.*;
import org.achymake.worlds.net.UpdateChecker;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class Worlds extends JavaPlugin {
    private static Worlds instance;
    public static PluginManager manager;
    public static WorldConfig worldConfig;
    public static UpdateChecker updateChecker;
    public static Message message;
    @Override
    public void onEnable() {
        instance = this;
        manager = getServer().getPluginManager();
        worldConfig = new WorldConfig(this);
        updateChecker = new UpdateChecker(this);
        message = new Message(this);
        getWorldConfig().setupWorlds();
        reload();
        commands();
        events();
        getMessage().sendLog(Level.INFO, "Enabled " + getName() + " " + getDescription().getVersion());
        getUpdateChecker().getUpdate();
    }
    @Override
    public void onDisable() {
        getMessage().sendLog(Level.INFO, "Disabled " + getName() + " " + getDescription().getVersion());
        getServer().getScheduler().cancelTasks(this);
    }
    private void commands() {
        getCommand("world").setExecutor(new WorldsCommand());
    }
    private void events() {
        getManager().registerEvents(new EntityDamageByEntity(this), this);
        getManager().registerEvents(new NotifyUpdate(this), this);
    }
    public void reload() {
        File file = new File(getDataFolder(), "config.yml");
        if (file.exists()) {
            try {
                getConfig().load(file);
            } catch (IOException | InvalidConfigurationException e) {
                getMessage().sendLog(Level.WARNING, e.getMessage());
            }
        } else {
            getConfig().options().copyDefaults(true);
            try {
                getConfig().save(file);
            } catch (IOException e) {
                getMessage().sendLog(Level.WARNING, e.getMessage());
            }
        }
        File folder = new File(getDataFolder(), "worlds");
        if (folder.exists()) {
            for (File files : folder.listFiles()) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(files);
                try {
                    config.load(files);
                } catch (IOException | InvalidConfigurationException e) {
                    getMessage().sendLog(Level.WARNING, e.getMessage());
                }
            }
        }
    }
    public Message getMessage() {
        return message;
    }
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }
    public PluginManager getManager() {
        return manager;
    }
    public WorldConfig getWorldConfig() {
        return worldConfig;
    }
    public static Worlds getInstance() {
        return instance;
    }
}