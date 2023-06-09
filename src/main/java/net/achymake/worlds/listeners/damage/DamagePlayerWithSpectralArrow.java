package net.achymake.worlds.listeners.damage;

import net.achymake.worlds.Worlds;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamagePlayerWithSpectralArrow implements Listener {
    public DamagePlayerWithSpectralArrow(Worlds worlds){
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayerWithSpectralArrow (EntityDamageByEntityEvent event){
        if (!event.getDamager().getType().equals(EntityType.SPECTRAL_ARROW))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        SpectralArrow damager = (SpectralArrow) event.getDamager();
        if (damager.getShooter() instanceof Player){
            Player player = (Player) damager.getShooter();
            if (Worlds.getWorldConfig().isPVP(player.getWorld().getName()))return;
            event.setCancelled(true);
        }
    }
}