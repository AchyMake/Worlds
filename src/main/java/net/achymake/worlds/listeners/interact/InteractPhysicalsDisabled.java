package net.achymake.worlds.listeners.interact;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractPhysicalsDisabled implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public InteractPhysicalsDisabled(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteractPhysicalsDisabled(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null)return;
        if (worldConfig.getWorldEditors().contains(event.getPlayer()))return;
        if (!event.getAction().equals(Action.PHYSICAL))return;
        if (!worldConfig.isPhysicalsCancelled(event.getClickedBlock().getWorld().getName(), event.getClickedBlock().getType().toString()))return;
        event.setCancelled(true);
    }
}