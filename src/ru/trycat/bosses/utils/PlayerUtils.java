package ru.trycat.bosses.utils;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;

public class PlayerUtils
{
    public static void damagePlayer( LivingEntity damager, final Player p, final int damage, final boolean ignore) {
        if (ignore) {
            p.setHealth(p.getHealth() - damage);
        }
        else {
            Bukkit.getPluginManager().callEvent((Event)new EntityDamageByEntityEvent((Entity)damager, (Entity)p, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage));
        }
    }
    
    public static void sendActionBar(final Player p, final String boss, final float health) {
        final String message = StringUtils.color(String.format("%s: &4%s\u2764", boss, health));
        final IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        final PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)ppoc);
    }
}
