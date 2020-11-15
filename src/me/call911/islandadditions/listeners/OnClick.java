package me.call911.islandadditions.listeners;

import org.bukkit.plugin.*;
import org.bukkit.event.player.*;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;

import me.call911.islandadditions.IslandAdditions;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.*;

public class OnClick implements Listener
{
    @SuppressWarnings("unused")
	private final IslandAdditions plugin;
    
    public OnClick(final IslandAdditions plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    
	  @EventHandler
	   public void NPCInventory(PlayerInteractEntityEvent e)
	   {
	       Player npc = (Player) e.getRightClicked();
	       Player p = e.getPlayer();
	     
	       if(npc.getName().contains("§7"))
	       {
	         e.setCancelled(true);
	         Bukkit.dispatchCommand(p, plugin.getConfig().getString("island-npc.entityCommand"));
	       }
	     }
	   
}
