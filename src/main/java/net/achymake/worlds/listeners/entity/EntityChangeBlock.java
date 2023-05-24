package net.achymake.worlds.listeners.entity;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlock implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public EntityChangeBlock(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (!worldConfig.isEntityCancelled(event.getEntity().getWorld().getName(),event.getEntityType()))return;
        event.setCancelled(true);
    }
}