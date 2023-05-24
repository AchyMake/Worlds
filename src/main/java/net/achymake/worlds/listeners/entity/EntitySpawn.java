package net.achymake.worlds.listeners.entity;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntitySpawn implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public EntitySpawn(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (!worldConfig.isSpawnCancelled(event.getEntity().getWorld().getName(),event.getEntityType()))return;
        event.setCancelled(true);
    }
}