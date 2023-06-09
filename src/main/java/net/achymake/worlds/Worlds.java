package net.achymake.worlds;

import net.achymake.worlds.commands.WorldCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import net.achymake.worlds.listeners.*;
import net.achymake.worlds.version.UpdateChecker;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class Worlds extends JavaPlugin {
    private static Worlds instance;
    private static WorldConfig worldConfig;
    private static Message message;
    @Override
    public void onEnable() {
        instance = this;
        message = new Message(this);
        worldConfig = new WorldConfig(getDataFolder());
        worldConfig.setup();
        reload();
        getCommand("world").setExecutor(new WorldCommand());
        new NotifyUpdate(this);
        new DamagePlayer(this);
        new DamagePlayerWithArrow(this);
        new DamagePlayerWithSnowball(this);
        new DamagePlayerWithSpectralArrow(this);
        new DamagePlayerWithThrownPotion(this);
        new DamagePlayerWithTrident(this);
        message.sendLog(Level.INFO, "Enabled " + getName() + " " + getDescription().getVersion());
        new UpdateChecker(this, 106196).getUpdate();
    }
    @Override
    public void onDisable() {
        if (worldConfig.getWorldEditors().isEmpty()) {
            worldConfig.getWorldEditors().clear();
        }
        message.sendLog(Level.INFO, "Disabled " + getName() + " " + getDescription().getVersion());
    }
    public static Message getMessage() {
        return message;
    }
    public static WorldConfig getWorldConfig() {
        return worldConfig;
    }
    public void reload() {
        File file = new File(getDataFolder(), "config.yml");
        if (file.exists()) {
            try {
                getConfig().load(file);
                saveConfig();
            } catch (IOException | InvalidConfigurationException e) {
                message.sendLog(Level.WARNING, e.getMessage());
            }
        } else {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
    public static Worlds getInstance() {
        return instance;
    }
}