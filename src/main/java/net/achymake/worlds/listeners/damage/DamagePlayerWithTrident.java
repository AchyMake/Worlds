package net.achymake.worlds.listeners.damage;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamagePlayerWithTrident implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public DamagePlayerWithTrident(Worlds worlds){
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayerWithTrident (EntityDamageByEntityEvent event){
        if (!event.getDamager().getType().equals(EntityType.TRIDENT))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        Trident damager = (Trident) event.getDamager();
        if (damager.getShooter() instanceof Player){
            Player player = (Player) damager.getShooter();
            if (worldConfig.isPVP(player.getWorld().getName()))return;
            event.setCancelled(true);
        }
    }
}