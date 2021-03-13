package ru.trycat.bosses;

import java.util.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class RewardItem
{
    Random random;
    
    public RewardItem(final Location loc, final double chance, final ItemStack itemStack) {
        this.random = new Random();
        if (this.random.nextFloat() <= chance) {
            loc.getWorld().dropItemNaturally(loc, itemStack);
        }
    }
}
