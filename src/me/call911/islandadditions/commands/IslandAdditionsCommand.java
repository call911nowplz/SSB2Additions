package me.call911.islandadditions.commands;

import me.call911.islandadditions.IslandAdditions;
import org.bukkit.command.*;
import org.bukkit.*;

public class IslandAdditionsCommand implements CommandExecutor
{
    private final IslandAdditions plugin;
    
    public IslandAdditionsCommand(final IslandAdditions plugin) {
        this.plugin = plugin;
        plugin.getCommand("additions").setExecutor((CommandExecutor)this);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            return false;
        }
        this.plugin.reload();
        sender.sendMessage(ChatColor.GREEN + this.plugin.getDescription().getName() + ">> reloaded plugin successfully.");
        return true;
    }
}
