package net.achymake.worlds.listeners.damage;

import net.achymake.worlds.Worlds;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamagePlayerWithSnowball implements Listener {
    public DamagePlayerWithSnowball(Worlds worlds){
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayerWithSnowball (EntityDamageByEntityEvent event){
        if (!event.getDamager().getType().equals(EntityType.SNOWBALL))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        Snowball snowball = (Snowball) event.getDamager();
        if (snowball.getShooter() instanceof Player){
            Player player = (Player) snowball.getShooter();
            if (Worlds.getWorldConfig().isPVP(player.getWorld().getName()))return;
            event.setCancelled(true);
        }
    }
}