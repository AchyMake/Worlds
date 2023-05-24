package net.achymake.worlds.listeners.bed;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerBedEnter implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public PlayerBedEnter(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (worldConfig.getWorldEditors().contains(event.getPlayer()))return;
        if (!worldConfig.isEntityCancelled(event.getPlayer().getWorld().getName(), event.getPlayer().getType()))return;
        event.setCancelled(true);
    }
}