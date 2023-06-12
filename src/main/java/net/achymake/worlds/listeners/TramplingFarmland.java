package net.achymake.worlds.listeners;

import net.achymake.worlds.Worlds;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TramplingFarmland implements Listener {
    private FileConfiguration getConfig() {
        return Worlds.getInstance().getConfig();
    }
    public TramplingFarmland(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onTramplingFarmland(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.PHYSICAL))return;
        if (event.getClickedBlock() == null)return;
        if (!event.getClickedBlock().getType().equals(Material.FARMLAND))return;
        if (!getConfig().getBoolean("prevent-trampling-farmland"))return;
        event.setCancelled(true);
    }
}