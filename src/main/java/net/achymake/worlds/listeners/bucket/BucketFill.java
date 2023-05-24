package net.achymake.worlds.listeners.bucket;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class BucketFill implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public BucketFill(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBucketFill(PlayerBucketFillEvent event) {
        if (worldConfig.getWorldEditors().contains(event.getPlayer()))return;
        if (!worldConfig.isEntityCancelled(event.getBlockClicked().getWorld().getName(),event.getPlayer().getType()))return;
        event.setCancelled(true);
    }
}