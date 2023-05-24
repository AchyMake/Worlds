package net.achymake.worlds.listeners.block;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;

public class BlockFertilize implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public BlockFertilize(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockFertilize(BlockFertilizeEvent event) {
        if (worldConfig.getWorldEditors().contains(event.getPlayer()))return;
        if (!worldConfig.isEntityCancelled(event.getPlayer().getWorld().getName(), event.getPlayer().getType()))return;
        event.setCancelled(true);
    }
}