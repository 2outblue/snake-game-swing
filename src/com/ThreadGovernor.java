package com;

import com.threads.CollisionCheckerThread;
import com.threads.FoodGenerationThread;
import com.threads.SnakeMovementThread;

public class ThreadGovernor {

    private static ThreadGovernor instance;
    private Thread movementThread;
    private CollisionCheckerThread ct;
    private Thread foodThread;
    private ThreadGovernor() {
        movementThread = new Thread(new SnakeMovementThread());
//        foodThread = new Thread(new FoodGenerationThread());
    }

    public void startAllThreads() {

    }

    public void createMovementThread() {

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
