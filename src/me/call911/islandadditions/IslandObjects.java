package me.call911.islandadditions;

import net.citizensnpcs.api.npc.*;
import com.gmail.filoghost.holographicdisplays.api.*;

public class IslandObjects
{
    private final NPC npc;
    private final Hologram npcHologram;
    private final Hologram extrahologram;
    
    public IslandObjects(final NPC npc, final Hologram npcHologram, final Hologram extrahologram) {
        this.npc = npc;
        this.npcHologram = npcHologram;
        this.extrahologram = extrahologram;
    }
    
    public void destroy() {
        this.npc.destroy();
        this.npcHologram.delete();
        this.extrahologram.delete();
    }
}
