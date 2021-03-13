package ru.trycat.bosses.utils;

import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.material.*;
import org.bukkit.enchantments.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

public class ItemBuilder implements Listener
{
    private final ItemStack is;
    
    public ItemBuilder(final Material mat) {
        this.is = new ItemStack(mat);
    }
    
    public ItemBuilder(final ItemStack is) {
        this.is = is;
    }
    
    public ItemBuilder amount(final int amount) {
        this.is.setAmount(amount);
        return this;
    }
    
    public ItemBuilder name(final String name) {
        final ItemMeta meta = this.is.getItemMeta();
        meta.setDisplayName(StringUtils.color(name));
        this.is.setItemMeta(meta);
        return this;
    }
    
    public ItemBuilder lore(final String name) {
        final ItemMeta meta = this.is.getItemMeta();
        List<String> lore = (List<String>)meta.getLore();
        if (lore == null) {
            lore = new ArrayList<String>();
        }
        lore.add(StringUtils.color(name));
        meta.setLore((List)lore);
        this.is.setItemMeta(meta);
        return this;
    }
    
    public ItemBuilder lore(final List<String> lore) {
        final ItemMeta meta = this.is.getItemMeta();
        meta.setLore((List)StringUtils.color(lore));
        this.is.setItemMeta(meta);
        return this;
    }
    
    public ItemBuilder lore(final String[] lore) {
        this.lore(new ArrayList<String>(Arrays.asList(lore)));
        return this;
    }
    
    public ItemBuilder durability(final int durability) {
        this.is.setDurability((short)durability);
        return this;
    }
    
    public ItemBuilder data(final int data) {
        this.is.setData(new MaterialData(this.is.getType(), (byte)data));
        return this;
    }
    
    public ItemBuilder enchantment(final Enchantment enchantment, final int level) {
        this.is.addUnsafeEnchantment(enchantment, level);
        return this;
    }
    
    public ItemBuilder enchantment(final Enchantment enchantment) {
        this.is.addUnsafeEnchantment(enchantment, 1);
        return this;
    }
    
    public ItemBuilder type(final Material material) {
        this.is.setType(material);
        return this;
    }
    
    public ItemBuilder clearLore() {
        final ItemMeta meta = this.is.getItemMeta();
        meta.setLore((List)new ArrayList());
        this.is.setItemMeta(meta);
        return this;
    }
    
    public ItemBuilder clearEnchantments() {
        for (final Enchantment e : this.is.getEnchantments().keySet()) {
            this.is.removeEnchantment(e);
        }
        return this;
    }
    
    public ItemBuilder color(final Color color) {
        if (this.is.getType() == Material.LEATHER_BOOTS || this.is.getType() == Material.LEATHER_CHESTPLATE || this.is.getType() == Material.LEATHER_HELMET || this.is.getType() == Material.LEATHER_LEGGINGS) {
            final LeatherArmorMeta meta = (LeatherArmorMeta)this.is.getItemMeta();
            meta.setColor(color);
            this.is.setItemMeta((ItemMeta)meta);
            return this;
        }
        throw new IllegalArgumentException("color() only applicable for leather armor!");
    }
    
    public ItemStack build() {
        return this.is;
    }
}
