package net.achymake.worlds.listeners.mount;

import net.achymake.worlds.Worlds;
import net.achymake.worlds.files.WorldConfig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class EntityMount implements Listener {
    private final WorldConfig worldConfig = Worlds.getWorldConfig();
    public EntityMount(Worlds worlds) {
        worlds.getServer().getPluginManager().registerEvents(this, worlds);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityMount(org.spigotmc.event.entity.EntityMountEvent event) {
        if (!event.getEntity().getType().equals(EntityType.PLAYER))return;
        if (event.getMount().getType().equals(EntityType.ARMOR_STAND))return;
        Player player = (Player) event.getEntity();
        if (worldConfig.getWorldEditors().contains(player))return;
        if (!worldConfig.isEntityCancelled(player.getWorld().getName(), player.getType()))return;
        event.setCancelled(true);
    }
}