package ru.trycat.bosses.tasks;

import org.bukkit.scheduler.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import ru.trycat.bosses.Bosses;

public class SyncTask implements Task
{
    private BukkitScheduler scheduler;
    private static Bosses instance;
    
    public SyncTask(final Runnable run) {
        this.scheduler = Bukkit.getScheduler();
        this.run(run);
    }
    
    public SyncTask(final Runnable run, final int ticks) {
        this.scheduler = Bukkit.getScheduler();
        this.run(run, ticks);
    }
    
    @Override
    public void run(final Runnable run) {
        this.scheduler.runTask((Plugin)SyncTask.instance, run);
    }
    
    @Override
    public void run(final Runnable run, final int delay) {
        this.scheduler.runTaskLater((Plugin)SyncTask.instance, run, (long)delay);
    }
    
    static {
        SyncTask.instance = Bosses.instance;
    }
}
