package com;

import com.threads.CollisionCheckerThread;
import com.threads.SnakeMovementThread;

public class ThreadGovernor {

    private static ThreadGovernor instance;
    private Thread movementThread;
    private CollisionCheckerThread ct;
    private ThreadGovernor() {
        movementThread = new Thread(new SnakeMovementThread());
    }

    public void startAllThreads() {

    }

    public void createMovementThread() {

        movementThread.start();
    }





    public static ThreadGovernor getInstance() {
        if (instance == null) {
            instance = new ThreadGovernor();
        }
        return instance;
    }
}
