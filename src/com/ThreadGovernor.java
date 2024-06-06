package com;

import com.threads.GameLoopThread;

// TODO: Is this class even needed?
public class ThreadGovernor {

    private static ThreadGovernor instance;
    private GameLoopThread gameLoopThread;

    private ThreadGovernor() {
    }

    public void closeAllThreads() {
        gameLoopThread.stopRunning();
    }

    public void startGameLoopThread() {
        gameLoopThread = new GameLoopThread();
        gameLoopThread.start();
    }

    public static ThreadGovernor getInstance() {
        if (instance == null) {
            instance = new ThreadGovernor();
        }
        return instance;
    }
}
