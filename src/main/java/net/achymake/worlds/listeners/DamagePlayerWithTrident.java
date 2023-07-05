package net.achymake.worlds.listeners;

import net.achymake.worlds.Worlds;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class DamagePlayerWithTrident implements Listener {
    public DamagePlayerWithTrident(Worlds plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayerWithTrident (EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.TRIDENT))return;
        Trident damager = (Trident) event.getDamager();
        if (!isPlayer(damager.getShooter()))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        if (Worlds.isPVP(event.getDamager().getWorld()))return;
        event.setCancelled(true);
        Worlds.sendActionBar((Player) damager.getShooter(), "&cError:&7 PVP is Disabled");
    }
    private boolean isPlayer(ProjectileSource projectileSource) {
        return projectileSource instanceof Player;
    }
}