package org.achymake.worlds.data;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.achymake.worlds.Worlds;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class Message {
    private Worlds getInstance() {
        return Worlds.getInstance();
    }
    private final File file = new File(getInstance().getDataFolder(), "message.yml");
    private FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    public File getFile() {
        return file;
    }
    public FileConfiguration getConfig() {
        return config;
    }
    public String get(String path) {
        if (config.isString(path)) {
            return addColor(config.getString(path));
        } else return path + ": is missing a value";
    }
    public String get(String path, String... format) {
        if (config.isString(path)) {
            return addColor(MessageFormat.format(config.getString(path), format));
        } else return path + ": is missing a value";
    }
    private boolean setup() {
        config.options().copyDefaults(true);
        config.set("error.world.invalid", "{0}&c does not exists");
        config.set("error.world.exists", "{0}&c already exists");
        config.set("error.world.folder.exists", "{0}&c folder already exists");
        config.set("error.world.folder.invalid", "{0}&c folder does not exists");
        config.set("world-creator.post", "{0}&6 is about to be created");
        config.set("world-creator.title", "{0}&6 has been created with the following:");
        config.set("world-creator.environment", "&6Environment&f: {0}");
        config.set("world-creator.seed", "&6Seed&f: {0}");
        config.set("commands.world.difficulty.success", "{0}&6 Difficulty has changed to&f {1}");
        config.set("commands.world.difficulty.invalid", "{0}&c is not a Difficulty");
        config.set("commands.world.info.title", "{0}&6 Info:");
        config.set("commands.world.info.pvp", "&6PVP&f: {0}");
        config.set("commands.world.info.difficulty", "&6Difficulty&f: {0}");
        config.set("commands.world.info.environment", "&6Environment&f: {0}");
        config.set("commands.world.info.seed", "&6Seed&f: {0}");
        config.set("commands.world.info.gamerule.title", "&6Gamerule:");
        config.set("commands.world.info.gamerule.listed", "&6{0}&f: {1}");
        config.set("commands.world.gamerule.changed", "{0}&6 changed&f {1}&6 to&f {2}");
        config.set("commands.world.pvp.enable", "{0}&6 is now PVP mode");
        config.set("commands.world.pvp.disable", "{0}&6 is no longer PVP mode");
        config.set("commands.world.remove", "{0}&6 is Saved and Removed");
        config.set("commands.world.setspawn",  "{0}&6 changed Spawn Point");
        config.set("events.teleport.success", "&6Teleporting to&f {0}");
        try {
            config.save(file);
            return true;
        } catch (IOException e) {
            getInstance().sendWarning(e.getMessage());
            return false;
        }
    }
    public boolean reload() {
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
            return true;
        } else return setup();
    }
    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(addColor(message)));
    }
    public String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public String toTitleCase(String string) {
        if (string.contains(" ")) {
            var builder = getBuilder();
            for (var strings : string.split(" ")) {
                builder.append(strings.toUpperCase().charAt(0) + strings.substring(1).toLowerCase());
                builder.append(" ");
            }
            return builder.toString().strip();
        } else if (string.contains("_")) {
            var builder = getBuilder();
            for (var strings : string.split("_")) {
                builder.append(strings.toUpperCase().charAt(0) + strings.substring(1).toLowerCase());
                builder.append(" ");
            }
            return builder.toString().strip();
        } else return string.toUpperCase().charAt(0) + string.substring(1).toLowerCase();
    }
    public StringBuilder getBuilder() {
        return new StringBuilder();
    }
}