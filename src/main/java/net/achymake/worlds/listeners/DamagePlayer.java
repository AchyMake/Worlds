package net.achymake.worlds.listeners;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamagePlayer implements Listener {
    private WorldConfig getWorldConfig() {
        return Worlds.getWorldConfig();
    }
    public DamagePlayer(Worlds worlds){
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayer (EntityDamageByEntityEvent event){
        if (!event.getDamager().getType().equals(EntityType.PLAYER))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        Player player = (Player) event.getDamager();
        if (getWorldConfig().isPVP(player.getWorld().getName()))return;
        event.setCancelled(true);
    }
}