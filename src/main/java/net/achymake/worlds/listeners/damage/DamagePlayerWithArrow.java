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

public class DamagePlayerWithArrow implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public DamagePlayerWithArrow(Worlds worlds){
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayerWithArrow (EntityDamageByEntityEvent event){
        if (!event.getDamager().getType().equals(EntityType.ARROW))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        Arrow arrow = (Arrow) event.getDamager();
        if (arrow.getShooter() instanceof Player){
            Player player = (Player) arrow.getShooter();
            if (worldConfig.isPVP(player.getWorld().getName()))return;
            event.setCancelled(true);
        }
    }
}