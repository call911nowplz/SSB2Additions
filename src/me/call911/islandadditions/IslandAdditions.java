package me.call911.islandadditions;

import org.bukkit.plugin.java.*;
import org.bukkit.entity.*;
import org.bukkit.util.*;
import org.bukkit.util.Vector;

import java.util.*;
import me.call911.islandadditions.commands.*;
import me.call911.islandadditions.listeners.OnClick;
import me.call911.islandadditions.listeners.PlayerJoin;
import me.call911.islandadditions.listeners.PlayerQuit;
import me.call911.islandadditions.listeners.PlayerTeleport;
import net.citizensnpcs.api.*;
import net.citizensnpcs.trait.*;
import net.citizensnpcs.api.trait.*;
import org.bukkit.plugin.*;
import java.util.function.*;
import net.citizensnpcs.api.npc.*;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.*;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.gmail.filoghost.holographicdisplays.api.*;

public final class IslandAdditions extends JavaPlugin
{
    private final Map<UUID,IslandObjects> islandObjects;
    private EntityType entityType;
    private final List<String> extraLines;
    private final List<String> npcLines;
    private final Vector extraOffset;
    private final Vector npcOffset;
    
    public IslandAdditions() {
        this.islandObjects = new HashMap<UUID, IslandObjects>();
        this.extraLines = new ArrayList<String>();
        this.npcLines = new ArrayList<String>();
        this.extraOffset = new Vector();
        this.npcOffset = new Vector();
    }
    
    public void onEnable() {
        if (!this.getDataFolder().exists() && !this.getDataFolder().mkdirs()) {
            return;
        }
        this.saveDefaultConfig();
        new OnClick(this);
        new PlayerJoin(this);
        new PlayerQuit(this);
        new PlayerTeleport(this);
        new IslandAdditionsCommand(this);
        this.reload();
    }
    
    public void reload() {
        this.extraLines.clear();
        this.npcLines.clear();
        this.reloadConfig();
        try {
            this.entityType = EntityType.valueOf(this.getConfig().getString("island-npc.entityType"));
        }
        catch (IllegalArgumentException exception) {
            this.entityType = EntityType.VILLAGER;
        }
        this.getConfig().getStringList("extra-hologram.lines").forEach(line -> this.extraLines.add(ChatColor.translateAlternateColorCodes('&', line)));
        this.getConfig().getStringList("island-npc.lines").forEach(line -> this.npcLines.add(ChatColor.translateAlternateColorCodes('&', line)));
        this.extraOffset.setX(this.getConfig().getDouble("extra-hologram.offset-x"));
        this.extraOffset.setY(this.getConfig().getDouble("extra-hologram.offset-y"));
        this.extraOffset.setZ(this.getConfig().getDouble("extra-hologram.offset-z"));
        this.npcOffset.setX(this.getConfig().getDouble("island-npc.offset-x"));
        this.npcOffset.setY(this.getConfig().getDouble("island-npc.offset-y"));
        this.npcOffset.setZ(this.getConfig().getDouble("island-npc.offset-z"));
    }
    
    public void onDisable() {
        this.islandObjects.values().forEach(IslandObjects::destroy);
        this.islandObjects.clear();
    }
    
    public void createNpcAndHolograms(final Island island) {
        if (this.islandObjects.containsKey(island.getOwner().getUniqueId())) {
            return;
        }
        final NPC npc = CitizensAPI.getNPCRegistry().createNPC(this.entityType, "§7");
        String skinName = this.getConfig().getString("island-npc.skinName");
        npc.data().set(NPC.PLAYER_SKIN_UUID_METADATA, skinName);
        npc.data().set(NPC.PLAYER_SKIN_USE_LATEST, false);
        final Location npcLocation = island.getCenter().clone().add(this.npcOffset);
        npcLocation.setYaw((float)this.getConfig().getDouble("island-npc.offset-yaw"));
        npc.spawn(npcLocation);
        npc.data().setPersistent("nameplate-visible", (Object)false);
        final LookClose lookClose = new LookClose();
        lookClose.lookClose(true);
        lookClose.setRange(2);
        npc.addTrait((Trait)lookClose);
        final Hologram npcHologram = HologramsAPI.createHologram((Plugin)this, npcLocation.add(0.0, 2.0, 0.0));
        this.npcLines.forEach(npcHologram::appendTextLine);
        final Hologram extraHologram = HologramsAPI.createHologram((Plugin)this, island.getCenter().clone().add(this.extraOffset));
        this.extraLines.forEach(extraHologram::appendTextLine);
        this.islandObjects.put(island.getUniqueId(), new IslandObjects(npc, npcHologram, extraHologram));
    }
    
    public void removeNpcFromIsland(final Island island) {
        if (island.getAllPlayersInside().size() > 1 || !this.islandObjects.containsKey(island.getUniqueId())) {
            return;
        }
        if (this.islandObjects.get(island.getUniqueId()) == null) {
            return;
        }
        final IslandObjects islandObject = this.islandObjects.get(island.getUniqueId());
        islandObject.destroy();
        this.islandObjects.remove(island.getUniqueId());
    }
}
