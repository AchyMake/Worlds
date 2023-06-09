package net.achymake.worlds.listeners;

import net.achymake.worlds.Worlds;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamagePlayerWithThrownPotion implements Listener {
    public DamagePlayerWithThrownPotion(Worlds worlds){
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayerWithThrownPotion (EntityDamageByEntityEvent event){
        if (!event.getDamager().getType().equals(EntityType.SPLASH_POTION))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        ThrownPotion damager = (ThrownPotion) event.getDamager();
        if (damager.getShooter() instanceof Player){
            Player player = (Player) damager.getShooter();
            if (Worlds.getWorldConfig().isPVP(player.getWorld().getName()))return;
            event.setCancelled(true);
        }
    }
}