package ru.trycat.bosses.mobs;

import ru.trycat.bosses.Spawner;

public interface Mob
{
    float getMobHealth();
    
    String getMobName();
    
    int getMobDamage();
    
    int getMobRegen();
    
    int getMobRegenDelay();
    
    Spawner getSpawner();
    
    void setSpawner(final Spawner p0);
    
    double getMobSpeed();
    
    int getMobMoney();
    
    int getMobAdditionalMoney();
    
    int getMobKills();
    
    double getMobKillsChance();
}
