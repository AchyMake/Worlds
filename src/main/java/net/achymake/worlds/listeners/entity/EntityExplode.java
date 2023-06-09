package net.achymake.worlds.listeners.entity;

import net.achymake.worlds.Worlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplode implements Listener {
    public EntityExplode(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (!Worlds.getWorldConfig().isEntityCancelled(event.getEntity().getWorld().getName(),event.getEntityType()))return;
        event.setCancelled(true);
    }
}