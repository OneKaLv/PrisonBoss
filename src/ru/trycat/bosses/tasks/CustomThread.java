package ru.trycat.bosses.tasks;

import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import java.util.*;

public class CustomThread
{
    private ExecutorService thread;
    private static List<CustomThread> threads;
    
    public void run(final Runnable run) {
        this.thread.execute(run);
    }
    
    public Future task(final Callable task) {
        if (this.thread != null) {
            return this.thread.submit((Callable<Object>)task);
        }
        return null;
    }
    
    public CustomThread(final int threads) {
        this.thread = Executors.newFixedThreadPool(threads);
        CustomThread.threads.add(this);
    }
    
    public CustomThread() {
        new CustomThread("Custom cached thread");
    }
    
    public CustomThread(final String name) {
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(name).setDaemon(true).build();
        this.thread = Executors.newCachedThreadPool(threadFactory);
        CustomThread.threads.add(this);
    }
    
    public CustomThread(final int threads, final String name) {
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(name).setDaemon(true).build();
        this.thread = Executors.newFixedThreadPool(threads, threadFactory);
        CustomThread.threads.add(this);
    }
    
    public static void shutdownAll() {
        CustomThread.threads.forEach(thread -> thread.getThread().shutdownNow().forEach(runn -> runn.run()));
    }
    
    public ExecutorService getThread() {
        return this.thread;
    }
    
    static {
        CustomThread.threads = new ArrayList<CustomThread>();
    }
}
