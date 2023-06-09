package net.achymake.worlds.listeners.entity;

import net.achymake.worlds.Worlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;

public class EntityBlockForm implements Listener {
    public EntityBlockForm(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        if (!Worlds.getWorldConfig().isEntityCancelled(event.getEntity().getWorld().getName(), event.getEntity().getType()))return;
        event.setCancelled(true);
    }
}