package ru.trycat.bosses.utils;

import java.util.HashMap;

public class Utils {

    public static HashMap<String, Double> calculatePercents(final HashMap<String, Integer> attackers, final int totalDamage) {
        final HashMap<String, Double> damagePercents = new HashMap<String, Double>();
        final double totalDamagePercents = totalDamage / 100.0;
        for (final String key : attackers.keySet()) {
            final double percent = attackers.get(key) / totalDamagePercents;
            damagePercents.put(key, percent);
        }
        return damagePercents;
    }
}
