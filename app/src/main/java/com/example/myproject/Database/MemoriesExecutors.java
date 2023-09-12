package com.example.myproject.Database;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MemoriesExecutors {

    private static final Object LOCK = new Object();
    private static MemoriesExecutors instance;
    private final ExecutorService diskIO;
    private final Executor mainThread;
    private final Executor networkIO;

    public MemoriesExecutors(ExecutorService diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static MemoriesExecutors getInstance() {
        if(instance == null) {
            synchronized (LOCK) {
                instance = new MemoriesExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3), new MainThreadExecutor());
            }
        }
        return instance;
    }

    public ExecutorService getDiskIO() {
        return diskIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
