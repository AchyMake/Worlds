package net.achymake.worlds.listeners.bucket;

import net.achymake.worlds.Worlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class BucketEmpty implements Listener {
    public BucketEmpty(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (!Worlds.getWorldConfig().isEntityCancelled(event.getBlockClicked().getWorld().getName(), event.getPlayer().getType()))return;
        if (Worlds.getWorldConfig().getWorldEditors().contains(event.getPlayer()))return;
        event.setCancelled(true);
    }
}