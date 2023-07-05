package net.achymake.worlds.listeners;

import net.achymake.worlds.Worlds;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamagePlayer implements Listener {
    public DamagePlayer(Worlds plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayer (EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        if (Worlds.isPVP(event.getDamager().getWorld()))return;
        event.setCancelled(true);
        Worlds.sendActionBar((Player) event.getDamager(), "&cError:&7 PVP is Disabled");
    }
}