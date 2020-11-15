package me.call911.islandadditions.listeners;

import me.call911.islandadditions.IslandAdditions;
import org.bukkit.plugin.*;
import org.bukkit.event.player.*;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;

import org.bukkit.Location;
import org.bukkit.event.*;

public class PlayerJoin implements Listener
{
    private final IslandAdditions plugin;
    
    public PlayerJoin(final IslandAdditions plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Island island = SuperiorSkyblockAPI.getIslandAt(event.getPlayer().getLocation());
        if (island == null) {
            return;
        }
        this.plugin.createNpcAndHolograms(island);
    }
}
