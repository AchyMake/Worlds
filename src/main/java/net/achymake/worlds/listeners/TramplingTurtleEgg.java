package net.achymake.worlds.listeners;

import net.achymake.worlds.Worlds;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TramplingTurtleEgg implements Listener {
    private FileConfiguration getConfig() {
        return Worlds.getInstance().getConfig();
    }
    public TramplingTurtleEgg(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onTramplingTurtleEgg(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.PHYSICAL))return;
        if (event.getClickedBlock() == null)return;
        if (!event.getClickedBlock().getType().equals(Material.TURTLE_EGG))return;
        if (!getConfig().getBoolean("prevent-trampling-FARMLAND"))return;
        event.setCancelled(true);
    }
}