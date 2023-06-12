package net.achymake.worlds.listeners;

import net.achymake.worlds.Worlds;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockRedstone implements Listener {
    private FileConfiguration getConfig() {
        return Worlds.getInstance().getConfig();
    }
    public BlockRedstone(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockRedstone(BlockRedstoneEvent event) {
        if (!getConfig().getBoolean("prevent-redstone"))return;
        event.setNewCurrent(0);
    }
}