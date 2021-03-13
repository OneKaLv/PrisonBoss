package ru.trycat.bosses.utils;

import org.bukkit.*;

import java.text.DecimalFormat;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;

public class StringUtils
{
    public static String color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> color (List<String> list){
        List<String> stringList = new ArrayList<>();
        for (String str : list){
            stringList.add(color(str));
        }
        return stringList;
    }
    
    public static String[] color(final String[] strings) {
        return color(Arrays.asList(strings)).toArray(new String[strings.length]);
    }

    public static double fixDouble(double amount, int digits) {
        if (digits == 0) return (int) amount;
        StringBuilder format = new StringBuilder("##");
        for (int i = 0; i < digits; i++) {
            if (i == 0) format.append(".");
            format.append("#");
        }
        return Double.valueOf(new DecimalFormat(format.toString()).format(amount).replace(",", "."));
    }

    public static double fixDouble(double amount) {
        return fixDouble(amount, 2);
    }
}
