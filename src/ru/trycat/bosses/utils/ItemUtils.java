package ru.trycat.bosses.utils;

import org.bukkit.inventory.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.inventory.ItemStack;

public class ItemUtils
{
    public static ItemStack addArmorPoints(final ItemStack cap, final int i, final String slot) {
        final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(cap);
        final NBTTagCompound compound = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        final NBTTagList modifiers = new NBTTagList();
        final NBTTagCompound toughness = new NBTTagCompound();
        toughness.set("AttributeName", (NBTBase)new NBTTagString("generic.maxHealth"));
        toughness.set("Name", (NBTBase)new NBTTagString("generic.maxHealth"));
        toughness.set("Amount", (NBTBase)new NBTTagInt(i));
        toughness.set("Operation", (NBTBase)new NBTTagInt(0));
        toughness.set("UUIDLeast", (NBTBase)new NBTTagInt(894654));
        toughness.set("UUIDMost", (NBTBase)new NBTTagInt(2872));
        modifiers.add((NBTBase)toughness);
        compound.set("Unbreakable", (NBTBase)new NBTTagByte((byte)1));
        compound.set("AttributeModifiers", (NBTBase)modifiers);
        nmsStack.setTag(compound);
        final ItemStack x = CraftItemStack.asBukkitCopy(nmsStack);
        return x;
    }
}
