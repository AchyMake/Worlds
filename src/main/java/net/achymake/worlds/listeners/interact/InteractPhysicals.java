package net.achymake.worlds.listeners.interact;

import net.achymake.worlds.Worlds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractPhysicals implements Listener {
    public InteractPhysicals(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteractPhysicals(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.PHYSICAL))return;
        if (event.getClickedBlock() == null)return;
        if (!Worlds.getWorldConfig().isPhysicalsCancelled(event.getClickedBlock().getWorld().getName(), event.getClickedBlock().getType().toString()))return;
        if (Worlds.getWorldConfig().getWorldEditors().contains(event.getPlayer()))return;
        event.setCancelled(true);
    }
}