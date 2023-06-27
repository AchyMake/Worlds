package net.achymake.worlds;

import net.achymake.worlds.commands.MainCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import net.achymake.worlds.listeners.*;
import net.achymake.worlds.version.UpdateChecker;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class Worlds extends JavaPlugin {
    private static Worlds instance;
    public static Worlds getInstance() {
        return instance;
    }
    private static FileConfiguration configuration;
    public static FileConfiguration getConfiguration() {
        return configuration;
    }
    private static Message message;
    public static Message getMessage() {
        return message;
    }
    private static WorldConfig worldConfig;
    public static WorldConfig getWorldConfig() {
        return worldConfig;
    }
    private void start() {
        instance = this;
        configuration = getConfig();
        message = new Message(getLogger());
        worldConfig = new WorldConfig(getDataFolder());
        getWorldConfig().setup();
        reload();
        commands();
        events();
        getMessage().sendLog(Level.INFO, "Enabled " + getName() + " " + getDescription().getVersion());
        new UpdateChecker(this, 106196).getUpdate();
    }
    private void stop() {
        getMessage().sendLog(Level.INFO, "Disabled " + getName() + " " + getDescription().getVersion());
    }
    private void commands() {
        getCommand("worlds").setExecutor(new MainCommand());
    }
    private void events() {
        new NotifyUpdate(this);
        new DamagePlayer(this);
        new DamagePlayerWithArrow(this);
        new DamagePlayerWithSnowball(this);
        new DamagePlayerWithSpectralArrow(this);
        new DamagePlayerWithThrownPotion(this);
        new DamagePlayerWithTrident(this);
    }
    @Override
    public void onEnable() {
        start();
    }
    @Override
    public void onDisable() {
        stop();
    }
    public void reload() {
        File file = new File(getDataFolder(), "config.yml");
        if (file.exists()) {
            try {
                getConfig().load(file);
                getMessage().sendLog(Level.INFO, "reloaded config.yml");
            } catch (IOException | InvalidConfigurationException e) {
                getMessage().sendLog(Level.WARNING, e.getMessage());
            }
            saveConfig();
        } else {
            getMessage().sendLog(Level.INFO, "creating config file");
            getConfig().options().copyDefaults(true);
            saveConfig();
            getMessage().sendLog(Level.INFO, "created config file");
        }
        getWorldConfig().reload();
    }
}