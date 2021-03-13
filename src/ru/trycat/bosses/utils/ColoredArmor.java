package ru.trycat.bosses.utils;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class ColoredArmor
{
    public static ItemStack getArmor(final Material material, final Color color) {
        final ItemStack plate = new ItemStack(material, 1);
        final LeatherArmorMeta meta2 = (LeatherArmorMeta)plate.getItemMeta();
        meta2.setColor(color);
        plate.setItemMeta((ItemMeta)meta2);
        return plate;
    }
    
    public static ItemStack getArmor(final Material material, final Color color, final String name) {
        final ItemStack plate = new ItemStack(material, 1);
        final LeatherArmorMeta meta2 = (LeatherArmorMeta)plate.getItemMeta();
        meta2.setDisplayName(StringUtils.color(name));
        meta2.setColor(color);
        plate.setItemMeta((ItemMeta)meta2);
        return plate;
    }
}
