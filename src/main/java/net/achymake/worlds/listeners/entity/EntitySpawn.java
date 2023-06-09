package net.achymake.worlds.listeners.entity;

import net.achymake.worlds.Worlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntitySpawn implements Listener {
    public EntitySpawn(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (!Worlds.getWorldConfig().isSpawnCancelled(event.getEntity().getWorld().getName(),event.getEntityType()))return;
        event.setCancelled(true);
    }
}