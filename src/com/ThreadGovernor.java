package com;

import com.threads.SnakeMovementThread;

// TODO: Is this class even needed?
public class ThreadGovernor {

    private static ThreadGovernor instance;
    private SnakeMovementThread movementThread;
    private Thread foodThread;
    private ThreadGovernor() {
    }

    public void startAllThreads() {

    }

    public void closeAllThreads() {
        movementThread.stopRunning();

//        movementThread = new Thread(new SnakeMovementThread());
    }

    public void createMovementThread() {
        movementThread = new SnakeMovementThread();
        movementThread.start();
    }
    public void createFoodGenerationThread(){
        foodThread.start();
    }





    public static ThreadGovernor getInstance() {
        if (instance == null) {
            instance = new ThreadGovernor();
        }
        return instance;
    }
}
