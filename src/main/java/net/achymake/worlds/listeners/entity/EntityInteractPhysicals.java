package net.achymake.worlds.listeners.entity;

import net.achymake.worlds.Worlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

public class EntityInteractPhysicals implements Listener {
    public EntityInteractPhysicals(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityInteractPhysicals(EntityInteractEvent event) {
        if (!Worlds.getWorldConfig().isPhysicalsCancelled(event.getEntity().getWorld().getName(), event.getBlock().getType().toString()))return;
        event.setCancelled(true);
    }
}