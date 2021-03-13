package ru.trycat.bosses;

import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import ru.trycat.bosses.tasks.AsyncTask;

public class CustomHolo
{
    static final ArrayList<Entity> holo;
    
    public CustomHolo(final Location loc, final StandType st, final int value) {
        ArmorStand as = (ArmorStand)loc.getWorld().spawnEntity(loc.add(0.0, (st == StandType.MONEY) ? -0.35 : -0.1, 1.0), EntityType.ARMOR_STAND);
        as.setCustomName(ChatColor.GREEN + String.format((st == StandType.MONEY) ? "+ %s$" : "+ %s \u043e\u043f\u044b\u0442(\u0430)", value));
        as.setCustomNameVisible(true);
        as.setVisible(false);
        as.setGravity(false);
        CustomHolo.holo.add((Entity)as);
        new AsyncTask(() -> {
            as.remove();
            CustomHolo.holo.remove(as);
        }, 100);
    }
    
    public static void deleteHolograms() {
        CustomHolo.holo.forEach(Entity::remove);
        CustomHolo.holo.clear();
    }
    
    static {
        holo = new ArrayList<Entity>();
    }
    
    public enum StandType
    {
        MONEY, 
        KILLS;
    }
}
