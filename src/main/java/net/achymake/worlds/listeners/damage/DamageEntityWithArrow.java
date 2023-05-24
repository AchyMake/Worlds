package net.achymake.worlds.listeners.damage;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEntityWithArrow implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public DamageEntityWithArrow(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamageEntityWithArrow (EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.ARROW))return;
        if (event.getEntity().getType().equals(EntityType.PLAYER))return;
        Arrow damager = (Arrow) event.getDamager();
        if (damager.getShooter() instanceof Player) {
            Player player = (Player) damager.getShooter();
            if (worldConfig.getWorldEditors().contains(player))return;
            if (!worldConfig.isEntityCancelled(event.getEntity().getWorld().getName(), player.getType()))return;
            if (Worlds.getInstance().getConfig().getBoolean("is-hostile." + event.getEntity().getType()))return;
            event.setCancelled(true);
        }
    }
}