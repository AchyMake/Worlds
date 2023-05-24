package net.achymake.worlds.listeners.leash;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;

public class PlayerLeashEntity implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public PlayerLeashEntity(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLeashEntity(PlayerLeashEntityEvent event) {
        Player player = event.getPlayer();
        if (worldConfig.getWorldEditors().contains(player))return;
        if (!worldConfig.isEntityCancelled(player.getWorld().getName(), player.getType()))return;
        event.setCancelled(true);
    }
}