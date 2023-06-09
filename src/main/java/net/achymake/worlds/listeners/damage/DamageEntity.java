package net.achymake.worlds.listeners.damage;

import net.achymake.worlds.Worlds;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntity implements Listener {
    public DamageEntity(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageEntity (EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER))return;
        if (event.getEntity().getType().equals(EntityType.PLAYER))return;
        Player player = (Player) event.getDamager();
        if (!Worlds.getWorldConfig().isEntityCancelled(event.getEntity().getWorld().getName(), player.getType()))return;
        if (Worlds.getWorldConfig().getWorldEditors().contains(player))return;
        if (Worlds.getInstance().getConfig().getBoolean("is-hostile." + event.getEntity().getType()))return;
        event.setCancelled(true);
    }
}