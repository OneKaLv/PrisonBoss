package ru.trycat.bosses.listeners;

import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.world.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class MobsListeners implements Listener
{
    @EventHandler
    public void onChunkUnload(final ChunkUnloadEvent e) {
        for (final Entity entity : e.getChunk().getEntities()) {
            if (!(entity instanceof Player) && entity.getCustomName() != null) {
                e.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void onDeathMob(EntityDeathEvent event){
        if (!(event.getEntity() instanceof Player)){
            event.setDroppedExp(0);
            event.getDrops().clear();
        }
    }

    @EventHandler
    public void onSLime(SlimeSplitEvent event){
        event.setCancelled(true);
    }
    
}
