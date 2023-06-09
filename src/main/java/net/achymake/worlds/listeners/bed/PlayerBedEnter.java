package net.achymake.worlds.listeners.bed;

import net.achymake.worlds.Worlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerBedEnter implements Listener {
    public PlayerBedEnter(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (!Worlds.getWorldConfig().isEntityCancelled(event.getPlayer().getWorld().getName(), event.getPlayer().getType()))return;
        if (Worlds.getWorldConfig().getWorldEditors().contains(event.getPlayer()))return;
        event.setCancelled(true);
    }
}