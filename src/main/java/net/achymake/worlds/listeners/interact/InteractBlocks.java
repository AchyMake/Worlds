package net.achymake.worlds.listeners.interact;

import net.achymake.worlds.Worlds;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractBlocks implements Listener {
    public InteractBlocks(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteractBlocks(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        if (event.getClickedBlock() == null)return;
        if (!Worlds.getWorldConfig().isEntityCancelled(event.getPlayer().getWorld().getName(), event.getPlayer().getType()))return;
        if (Worlds.getWorldConfig().getWorldEditors().contains(event.getPlayer()))return;
        if (Tag.ANVIL.isTagged(event.getClickedBlock().getType()))return;
        if (Tag.STAIRS.isTagged(event.getClickedBlock().getType()))return;
        if (Tag.DOORS.isTagged(event.getClickedBlock().getType()))return;
        if (Tag.SLABS.isTagged(event.getClickedBlock().getType()))return;
        if (Tag.SIGNS.isTagged(event.getClickedBlock().getType()))return;
        if (event.getClickedBlock().getType().equals(Material.ENDER_CHEST))return;
        if (event.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE))return;
        if (event.getClickedBlock().getType().equals(Material.CRAFTING_TABLE))return;
        if (event.getClickedBlock().getType().equals(Material.PLAYER_HEAD))return;
        event.setCancelled(true);
    }
}