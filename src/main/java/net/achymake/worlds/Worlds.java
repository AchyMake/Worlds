package net.achymake.worlds;

import net.achymake.worlds.commands.MainCommand;
import net.achymake.worlds.listeners.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Worlds extends JavaPlugin {
    private static Worlds instance;
    public static Worlds getInstance() {
        return instance;
    }
    private static Logger logger;
    public static void sendLog(Level level, String message) {
        logger.log(level, message);
    }
    private static FileConfiguration configuration;
    public static FileConfiguration getConfiguration() {
        return configuration;
    }
    private static File folder;
    public static File getFolder() {
        return folder;
    }
    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        configuration = getConfig();
        folder = getDataFolder();
        setupWorlds();
        reload();
        commands();
        events();
        sendLog(Level.INFO, "Enabled " + getName() + " " + getDescription().getVersion());
        getUpdate();
    }
    @Override
    public void onDisable() {
        sendLog(Level.INFO, "Disabled " + getName() + " " + getDescription().getVersion());
    }
    private void commands() {
        getCommand("worlds").setExecutor(new MainCommand());
    }
    private void events() {
        new DamagePlayer(this);
        new DamagePlayerWithArrow(this);
        new DamagePlayerWithSnowball(this);
        new DamagePlayerWithSpectralArrow(this);
        new DamagePlayerWithThrownPotion(this);
        new DamagePlayerWithTrident(this);
        new NotifyUpdate(this);
    }
    public static void reload() {
        File file = new File(getFolder(), "config.yml");
        if (file.exists()) {
            try {
                getConfiguration().load(file);
                getConfiguration().save(file);
                sendLog(Level.INFO, "loaded config.yml");
            } catch (IOException | InvalidConfigurationException e) {
                sendLog(Level.WARNING, e.getMessage());
            }
        } else {
            getConfiguration().options().copyDefaults(true);
            try {
                getConfiguration().save(file);
                sendLog(Level.INFO, "created config.yml");
            } catch (IOException e) {
                sendLog(Level.WARNING, e.getMessage());
            }
        }
        File folder = new File(getFolder(), "worlds");
        if (folder.exists()) {
            for (File files : folder.listFiles()) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(files);
                try {
                    config.load(files);
                    Worlds.sendLog(Level.INFO, "loaded " + files.getName());
                } catch (IOException | InvalidConfigurationException e) {
                    Worlds.sendLog(Level.WARNING, e.getMessage());
                }
            }
        }
    }
    public static void getUpdate(Player player) {
        if (notifyUpdate()) {
            getLatest((latest) -> {
                if (!getInstance().getDescription().getVersion().equals(latest)) {
                    send(player,"&6" + getInstance().getName() + " Update:&f " + latest);
                    send(player,"&6Current Version: &f" + getInstance().getDescription().getVersion());
                }
            });
        }
    }
    public void getUpdate() {
        if (notifyUpdate()) {
            getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
                @Override
                public void run() {
                    getLatest((latest) -> {
                        sendLog(Level.INFO, "Checking latest release");
                        if (getDescription().getVersion().equals(latest)) {
                            sendLog(Level.INFO, "You are using the latest version");
                        } else {
                            sendLog(Level.INFO, "New Update: " + latest);
                            sendLog(Level.INFO, "Current Version: " + getDescription().getVersion());
                        }
                    });
                }
            });
        }
    }
    public static void getLatest(Consumer<String> consumer) {
        try {
            InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + 106196)).openStream();
            Scanner scanner = new Scanner(inputStream);
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
                scanner.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            sendLog(Level.WARNING, e.getMessage());
        }
    }
    public static boolean notifyUpdate() {
        return getConfiguration().getBoolean("notify-update.enable");
    }
    public static void send(ConsoleCommandSender sender, String message) {
        sender.sendMessage(message);
    }
    public static void send(Player player, String message) {
        player.sendMessage(addColor(message));
    }
    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(addColor(message)));
    }
    public static String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static boolean folderExist(String worldName) {
        return new File(getInstance().getServer().getWorldContainer(), worldName).exists();
    }
    public static boolean worldExist(String worldName) {
        return getInstance().getServer().getWorld(worldName) != null;
    }
    public static void setupWorlds() {
        File folder = new File(getFolder(), "worlds");
        if (folder.exists()) {
            if (folder.list().length > 0) {
                for (File files : folder.listFiles()) {
                    String worldName = files.getName().replace(".yml", "");
                    if (worldExist(worldName)) {
                        sendLog(Level.INFO, worldName + " already exist");
                    } else {
                        if (folderExist(worldName)) {
                            sendLog(Level.INFO, "creating " + worldName);
                            FileConfiguration configuration = YamlConfiguration.loadConfiguration(files);
                            WorldCreator worldCreator = new WorldCreator(worldName);
                            worldCreator.environment(World.Environment.valueOf(configuration.getString("environment")));
                            worldCreator.seed(configuration.getLong("seed"));
                            worldCreator.createWorld();
                            sendLog(Level.INFO, worldName + " has been created with " + configuration.getString("environment") + " environment");
                        } else {
                            files.delete();
                            sendLog(Level.WARNING, worldName + " does not exist " + files.getName() + " has been deleted");
                        }
                    }
                }
            }
        } else {
            folder.mkdirs();
            for (World world : getInstance().getServer().getWorlds()) {
                File file = new File(getFolder(), "worlds/" + world.getName() + ".yml");
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
                        sendLog(Level.WARNING, e.getMessage());
                    }
                }
            }
        }
    }
    public static void create(String worldName, World.Environment environment) {
        File file = new File(getFolder(), "worlds/" + worldName + ".yml");
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
        } catch (IOException e) {
            sendLog(Level.WARNING, e.getMessage());
        }
    }
    public static FileConfiguration get(World world) {
        return YamlConfiguration.loadConfiguration(new File(getFolder(), "worlds/" + world.getName() + ".yml"));
    }
    public static boolean isPVP(World world) {
        return get(world).getBoolean("pvp");
    }
    public static void setPVP(World world, boolean value) {
        File file = new File(getFolder(), "worlds/" + world.getName() + ".yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        configuration.set("pvp", value);
        try {
            configuration.save(file);
        } catch (IOException e) {
            sendLog(Level.WARNING, e.getMessage());
        }
    }
}