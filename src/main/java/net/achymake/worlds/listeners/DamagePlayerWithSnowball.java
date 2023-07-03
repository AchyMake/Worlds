package net.achymake.worlds.listeners;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class DamagePlayerWithSnowball implements Listener {
    private WorldConfig getWorldConfig() {
        return Worlds.getWorldConfig();
    }
    public DamagePlayerWithSnowball(Worlds plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDamagePlayerWithSnowball (EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.SNOWBALL))return;
        Snowball damager = (Snowball) event.getDamager();
        if (!isPlayer(damager.getShooter()))return;
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        if (isPVP(event.getDamager().getWorld()))return;
        event.setCancelled(true);
        Player player = (Player) damager.getShooter();
        Worlds.sendActionBar(player, "&cError:&7 PVP is Disabled");
    }
    private boolean isPlayer(ProjectileSource projectileSource) {
        return projectileSource instanceof Player;
    }
    private boolean isPVP(World world) {
        return getWorldConfig().isPVP(world.getName());
    }
}