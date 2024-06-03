package com;

import com.threads.SnakeMovementThread;

public class ThreadGovernor {

    private static ThreadGovernor instance;
    private SnakeMovementThread movementThread;
    private Thread foodThread;
    private ThreadGovernor() {
//        movementThread = new Thread(new SnakeMovementThread());
//        movementThread = new SnakeMovementThread();
//        foodThread = new Thread(new FoodGenerationThread());
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
        System.out.println("Thread created");
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
