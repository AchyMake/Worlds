package net.achymake.worlds;

import net.achymake.worlds.commands.WorldCommand;
import net.achymake.worlds.files.Message;
import net.achymake.worlds.files.WorldConfig;
import net.achymake.worlds.listeners.bed.PlayerBedEnter;
import net.achymake.worlds.listeners.block.*;
import net.achymake.worlds.listeners.bucket.BucketEmpty;
import net.achymake.worlds.listeners.bucket.BucketEntity;
import net.achymake.worlds.listeners.bucket.BucketFill;
import net.achymake.worlds.listeners.connection.NotifyUpdate;
import net.achymake.worlds.listeners.connection.QuitWhileWorldEdit;
import net.achymake.worlds.listeners.damage.*;
import net.achymake.worlds.listeners.entity.*;
import net.achymake.worlds.listeners.interact.InteractBlocks;
import net.achymake.worlds.listeners.interact.InteractEntity;
import net.achymake.worlds.listeners.interact.InteractPhysicals;
import net.achymake.worlds.listeners.leash.PlayerLeashEntity;
import net.achymake.worlds.listeners.mount.EntityMount;
import net.achymake.worlds.listeners.shear.ShearEntity;
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
        new PlayerBedEnter(this);
        new BlockBreak(this);
        new BlockFertilize(this);
        new BlockPlace(this);
        new BlockRedstone(this);
        new HarvestBlock(this);
        new BucketEmpty(this);
        new BucketEntity(this);
        new BucketFill(this);
        new NotifyUpdate(this);
        new QuitWhileWorldEdit(this);
        new DamageEntity(this);
        new DamagePlayer(this);
        new DamagePlayerWithArrow(this);
        new DamagePlayerWithSnowball(this);
        new DamagePlayerWithSpectralArrow(this);
        new DamagePlayerWithThrownPotion(this);
        new DamagePlayerWithTrident(this);
        new EntityBlockForm(this);
        new EntityChangeBlock(this);
        new EntityExplode(this);
        new EntityInteractPhysicals(this);
        new EntitySpawn(this);
        new FireSpread(this);
        new InteractBlocks(this);
        new InteractEntity(this);
        new InteractPhysicals(this);
        new PlayerLeashEntity(this);
        new EntityMount(this);
        new ShearEntity(this);
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