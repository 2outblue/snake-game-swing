package com.threads;

import com.GameManager;
import com.components.ComponentFactory;
import com.game_objects.SmallFood;

import java.util.Random;

import static java.lang.Thread.currentThread;

// Currently does nothing - not even started by the ThreadGovernor
public class FoodGenerationThread implements Runnable {
    boolean running;
//    private SnakeHead head;
    private SmallFood smallFood;

//    private Random random;

    // collision if x < 100 or x > 690 // if y > 670 or y < 80
    public FoodGenerationThread() {
//        head = Snake.getInstance().getHead();
        smallFood = ComponentFactory.getInstance().createSmallFood();
//        random = new Random();
        running = true;
    }

    @Override
    public void run() {
        System.out.println("FoodGenerationThread --> " + currentThread().getName());
        spawnFood();
        while (running) {
            if (true) { // - if (foodCollision())
                GameManager.getInstance().removeFood(smallFood);
                GameManager.getInstance().growSnake();

                spawnFood();
            }


            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void spawnFood() {
        Random random = new Random();
        smallFood = ComponentFactory.getInstance().createSmallFood();
        int randomX = random.nextInt(100, 690);
        int randomY = random.nextInt(80, 670);

        smallFood.setBounds(randomX, randomY, smallFood.getPreferredSize().width, smallFood.getPreferredSize().height);
        GameManager.getInstance().addFood(smallFood);
    }

//    private boolean foodCollision() {
//        Rectangle headBounds = Snake.getInstance().getHead().getBounds();
//        //can headBounds.x/y cause issues? (not .get, so its not going through a synch method?)
//        int snakeX = headBounds.x;
//        int snakeY = headBounds.y;
//        int foodX = smallFood.getBounds().x;
//        int foodY = smallFood.getBounds().y;
//
//        //            System.out.println("Food collision");
//        return (snakeX + ComponentConst.SNAKE_COMPONENT_SIZE >= foodX && snakeX <= foodX + ComponentConst.FOOD_SIZE) &&
//                (snakeY + ComponentConst.SNAKE_COMPONENT_SIZE >= foodY && snakeY <= foodY + ComponentConst.FOOD_SIZE);
//    }
}
