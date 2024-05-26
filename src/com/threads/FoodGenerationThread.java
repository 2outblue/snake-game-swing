package com.threads;

import com.GameManager;
import com.components.ComponentFactory;
import com.components.food.SmallFood;
import com.components.snake.SnakeHead;
import com.game_objects.Snake;

import java.util.Random;

public class FoodGenerationThread implements Runnable {
    boolean running;
    private SnakeHead head;
    private SmallFood smallFood;

    private Random random;

    // collision if x < 100 or x > 690 // if y > 670 or y < 80
    public FoodGenerationThread() {
        head = Snake.getInstance().getHead();
        smallFood = ComponentFactory.getInstance().createSmallFood();
        random = new Random();
        running = true;
    }

    @Override
    public void run() {
        spawnFood();
        while (running) {
            if (foodCollision()) {
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
        int randomX = random.nextInt(100, 690);
        int randomY = random.nextInt(80, 670);

        smallFood.setBounds(randomX, randomY, smallFood.getPreferredSize().width, smallFood.getPreferredSize().height);
        GameManager.getInstance().addFood(smallFood);
    }

    private boolean foodCollision() {
        int snakeX = Snake.getInstance().getHead().getBounds().x;
        int snakeY = Snake.getInstance().getHead().getBounds().y;

        int foodX = smallFood.getBounds().x;
        int foodY = smallFood.getBounds().y;

        if ((snakeX > foodX && snakeX < foodX + 10) && (snakeY > foodY && snakeY < foodY + 10)) {
            System.out.println("Food collision");
            return true;
        }
        return false;
    }
}
