package net.achymake.worlds.listeners.entity;

import net.achymake.worlds.Worlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class FireSpread implements Listener {
    public FireSpread(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onFireSpread(BlockIgniteEvent event) {
        if (!event.getCause().equals(BlockIgniteEvent.IgniteCause.SPREAD))return;
        event.setCancelled(true);
    }
}