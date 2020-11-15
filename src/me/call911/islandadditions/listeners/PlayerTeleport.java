package me.call911.islandadditions.listeners;

import org.bukkit.plugin.*;
import org.bukkit.event.player.*;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;

import me.call911.islandadditions.IslandAdditions;

import org.bukkit.event.*;

public class PlayerTeleport implements Listener
{
	private final IslandAdditions plugin;
    
    public PlayerTeleport(final IslandAdditions plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    @EventHandler
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        final Island islandFrom = SuperiorSkyblockAPI.getIslandAt(event.getFrom());
        final Island islandTo = SuperiorSkyblockAPI.getIslandAt(event.getTo());
        if (islandFrom == null) {
            if (islandTo == null) {
                return;
            }
            this.plugin.createNpcAndHolograms(islandTo);
        }
        else if (!islandFrom.equals(islandTo)) {
            this.plugin.removeNpcFromIsland(islandFrom);
        }
    }
}
