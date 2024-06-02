package com.threads;

import com.GameManager;
import com.SoundManager;
import com.ThreadGovernor;
import com.components.ComponentFactory;
import com.components.constants.ComponentConst;
import com.components.constants.Direction;
import com.components.food.SmallFood;
import com.components.snake.SnakeHead;
import com.game_objects.Snake;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Thread.currentThread;

public class SnakeMovementThread extends Thread {

    private int movementSpeedInverse = 33;

    private final Snake snake;

    private SmallFood smallFood;

    //maybe should be isRunning ?
    private boolean isRunning;

    private Rectangle headBounds;

    private boolean skip = false;

    public SnakeMovementThread() {
        snake = GameManager.getInstance().getSnake();
        isRunning = true;
        headBounds = snake.getHead().getBounds();
    }

    @Override
    public void run() {
        System.out.println("Movement thread now running");
        spawnFood();
        Direction updatedDirection = snake.getHead().getDirection();
        int step = 6;
        while (isRunning) {
            SnakeHead head = snake.getHead();
            headBounds = head.getBounds();

            // this skips checking for the actual direction once per two 'frames' (updates) -this is
            // so the snake can't turn around quicker than 12px - which would be less than its body width
            // and it will immediately collide with itself - this presents another problem, but this is solved and
            // explained in the HeadDirectionChangeListener
            if (!skip) {
                updatedDirection = head.getDirection();
                skip = true;
            } else {
                skip = false;
            }
            if (!snake.isPaused()) {
                if (updatedDirection == Direction.UP) {
                    head.setBounds(headBounds.x, headBounds.y - step, headBounds.width, headBounds.height);
                    head.setAngle(3.141592653);
                    snake.setPreviousDirectionInput(Direction.UP);
                } else if (updatedDirection == Direction.DOWN) {
                    head.setBounds(headBounds.x, headBounds.y + step, headBounds.width, headBounds.height);
                    head.setAngle(0.0);
                    snake.setPreviousDirectionInput(Direction.DOWN);
                } else if (updatedDirection == Direction.LEFT) {
                    head.setBounds(headBounds.x - step, headBounds.y, headBounds.width, headBounds.height);
                    head.setAngle(1.5708);
                    snake.setPreviousDirectionInput(Direction.LEFT);
                } else if (updatedDirection == Direction.RIGHT) {
                    head.setBounds(headBounds.x + step, headBounds.y, headBounds.width, headBounds.height);
                    head.setAngle(4.71239);
                    snake.setPreviousDirectionInput(Direction.RIGHT);
                }

                if (borderCollision() || selfCollision()) {
                    SoundManager.getInstance().playEndGame();
                    GameManager.getInstance().endGame();
                }
                // take that out to the food thread along with the relevant methods, maybe
                if (foodCollision()) {
                    SoundManager.getInstance().playFoodCollision();
                    GameManager.getInstance().removeFood(smallFood);
                    GameManager.getInstance().growSnake();

                    spawnFood();
                }
                snake.paintBody(headBounds);
//                System.out.println(headBounds);
            }

            try {
                Thread.sleep(movementSpeedInverse);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Movement Thread stopped");
    }

    private boolean borderCollision() {
        int snakeX = snake.getHead().getBounds().x;
        int snakeY = snake.getHead().getBounds().y;
        // collision if x < 100 or x > 690 // if y > 670 or y < 80 - previous values with the border components
        return snakeX < 96 || snakeX > 680 || snakeY < 78 || snakeY > 660;
    }

    private boolean selfCollision() {
        int headX = snake.getHead().getBounds().x;
        int headY = snake.getHead().getBounds().y;
        //doesn't check for the first two blocks (the first one always collides since there is a 5px overlap
        // with the snakeHead, doesn't check for the second and third for safety)
        for (int i = 3; i < snake.getBody().size(); i++) {
            Rectangle compBounds = snake.getBody().get(i).getBounds();
            int compX = compBounds.x;
            int compY = compBounds.y;
//            System.out.println(String.format("Component %d - coords x:%d--y:%d",i, compX, compY));
            if ((headX > compX && headX < compX + ComponentConst.SNAKE_COMPONENT_SIZE) &&
                    (headY > compY && headY < compY + ComponentConst.SNAKE_COMPONENT_SIZE)) {
                return true;
            }
        }
//        System.out.println(snakeX + "<-X snake Y->" + snakeY);
        return false;
    }

    private void spawnFood() {
        Random random = new Random();
        smallFood = ComponentFactory.getInstance().createSmallFood();
        int randomX = random.nextInt(100, 680);
        int randomY = random.nextInt(80, 660);

        smallFood.setBounds(randomX, randomY, smallFood.getPreferredSize().width, smallFood.getPreferredSize().height);
        GameManager.getInstance().addFood(smallFood);
    }
    private boolean foodCollision() {
        //can headBounds.x/y cause issues? (not .get, so its not going through a synch method?)
        int snakeX = headBounds.x;
        int snakeY = headBounds.y;
        int foodX = smallFood.getBounds().x;
        int foodY = smallFood.getBounds().y;

        //            System.out.println("Food collision");
        return (snakeX + ComponentConst.SNAKE_HEAD_20 - 5 >= foodX && snakeX <= foodX + ComponentConst.FOOD_SIZE) &&
                (snakeY + ComponentConst.SNAKE_HEAD_20  - 5>= foodY && snakeY <= foodY + ComponentConst.FOOD_SIZE);
    }

    public void stopRunning() {
//        this.interrupt();
        isRunning = false;
    }


}
