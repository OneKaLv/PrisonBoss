package ru.trycat.bosses.tasks;

import org.bukkit.scheduler.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import ru.trycat.bosses.Bosses;

public class AsyncTask implements Task
{
    private BukkitScheduler scheduler;
    private static Bosses instance;
    
    public AsyncTask(final Runnable run) {
        this.scheduler = Bukkit.getScheduler();
        this.run(run);
    }
    
    public AsyncTask(final Runnable run, final int ticks) {
        this.scheduler = Bukkit.getScheduler();
        this.run(run, ticks);
    }
    
    @Override
    public void run(final Runnable run) {
        this.scheduler.runTaskAsynchronously((Plugin)AsyncTask.instance, run);
    }
    
    @Override
    public void run(final Runnable run, final int delay) {
        this.scheduler.runTaskLaterAsynchronously((Plugin)AsyncTask.instance, run, (long)delay);
    }
    
    static {
        AsyncTask.instance = Bosses.instance;
    }
}
