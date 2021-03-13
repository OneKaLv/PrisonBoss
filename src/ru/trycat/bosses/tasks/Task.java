package ru.trycat.bosses.tasks;

public interface Task
{
    void run(final Runnable p0);
    
    void run(final Runnable p0, final int p1);
}
