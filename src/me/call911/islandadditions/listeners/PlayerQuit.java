package me.call911.islandadditions.listeners;

import org.bukkit.plugin.*;
import org.bukkit.event.player.*;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;

import me.call911.islandadditions.IslandAdditions;

import org.bukkit.event.*;

public class PlayerQuit implements Listener
{
    private final IslandAdditions plugin;
    
    public PlayerQuit(final IslandAdditions plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Island island = SuperiorSkyblockAPI.getIslandAt(event.getPlayer().getLocation());
        if (island == null) {
            return;
        }
        this.plugin.removeNpcFromIsland(island);
    }
}
