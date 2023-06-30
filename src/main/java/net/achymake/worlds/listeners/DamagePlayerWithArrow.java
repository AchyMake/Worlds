package net.achymake.worlds.listeners;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class DamagePlayerWithArrow implements Listener {
    private WorldConfig getWorldConfig() {
        return Worlds.getWorldConfig();
    }
    public DamagePlayerWithArrow(Worlds plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayerWithArrow (EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.ARROW))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        Arrow damager = (Arrow) event.getDamager();
        if (!isPlayer(damager.getShooter()))return;
        if (getWorldConfig().isPVP(event.getDamager().getWorld().getName()))return;
        event.setCancelled(true);
    }
    private boolean isPlayer(ProjectileSource projectileSource) {
        return projectileSource instanceof Player;
    }
}