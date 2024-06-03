package com.threads;

import com.GameManager;
import com.SoundManager;
import com.components.ComponentFactory;
import com.components.constants.ComponentConst;
import com.components.constants.Direction;
import com.game_objects.SmallFood;
import com.components.snake.SnakeHead;
import com.game_objects.Snake;
import com.game_utility.CoordinateStore;
import com.game_utility.Difficulty;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SnakeMovementThread extends Thread {

    private final int movementSpeedInverse = 33;

    private final Snake snake;

    private SmallFood smallFood;

    //maybe should be isRunning ?
    private boolean isRunning;

    private Rectangle headBounds;

    private Difficulty dif;

    private boolean skip = false;

    public SnakeMovementThread() {
        snake = GameManager.getInstance().getSnake();
        isRunning = true;
        headBounds = snake.getHead().getBounds();
        dif = CoordinateStore.getDifficulty();
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
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
                    try {
                        sleep(600);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
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
        if (dif == Difficulty.EASY || dif == Difficulty.MEDIUM) {
            // collision if x < 100 or x > 690 // if y > 670 or y < 80 - previous values with the border components
            return snakeX < CoordinateStore.borderMinX || snakeX > CoordinateStore.borderMaxX ||
                    snakeY < CoordinateStore.borderMinY || snakeY > CoordinateStore.borderMaxY;
        } else if (dif == Difficulty.HARD) {
            if ((snakeX < CoordinateStore.borderMinX || snakeX > CoordinateStore.borderMaxX) ||
                    (snakeY < CoordinateStore.borderMinY || snakeY > CoordinateStore.borderMaxY) ||
                    // middle wall upper left corner
                    (snakeX > 170 && snakeX < 376) && (snakeY > 170 && snakeY < 240) ||
                    (snakeX > 170 && snakeX < 238) && (snakeY > 220 && snakeY < 370) ||
                    // middle wall upper right corner
                    (snakeX > 402 && snakeX < 610) && (snakeY > 170 && snakeY < 240) ||
                    (snakeX > 540 && snakeX < 610) && (snakeY > 170 && snakeY < 370) ||
                    // middle wall bottom left corner
                    (snakeX > 170 && snakeX < 376) && (snakeY > 540 && snakeY < 610) ||
                    (snakeX > 170 && snakeX < 240) && (snakeY > 410 && snakeY < 560) ||
                    // middle wall bottom right corner
                    (snakeX > 402 && snakeX < 610) && (snakeY > 540 && snakeY < 610) ||
                    (snakeX > 540 && snakeX < 610) && (snakeY > 410 && snakeY < 560) ||
                    // middle left pillar
                    (snakeX > 288 && snakeX < 357) && (snakeY > 280 && snakeY < 495) ||
                    // middle right pillar
                    (snakeX > 421 && snakeX < 491) && (snakeY > 280 && snakeY < 495)) {
                return true;
            }
        }

        return false;
    }

    private boolean selfCollision() {
        int headX = headBounds.x;
        int headY = headBounds.y;
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
        if (dif == Difficulty.EASY || dif == Difficulty.MEDIUM) {
            int randomX = random.nextInt(CoordinateStore.foodMinX, CoordinateStore.foodMaxX);
            int randomY = random.nextInt(CoordinateStore.foodMinY, CoordinateStore.foodMaxY);
            smallFood.setBounds(randomX, randomY, smallFood.getPreferredSize().width, smallFood.getPreferredSize().height);
        } else if (dif == Difficulty.HARD) {
            boolean outOfBounds = true;

            while (outOfBounds) {
                int randomX = random.nextInt(CoordinateStore.foodMinX, CoordinateStore.foodMaxX);
                int randomY = random.nextInt(CoordinateStore.foodMinY, CoordinateStore.foodMaxY);

                if ((randomX > 170 && randomX < 376) && (randomY > 170 && randomY < 240) ||
                        (randomX > 170 && randomX < 238) && (randomY > 220 && randomY < 370) ||
                        // middle wall upper right corner
                        (randomX > 402 && randomX < 610) && (randomY > 170 && randomY < 240) ||
                        (randomX > 540 && randomX < 610) && (randomY > 170 && randomY < 370) ||
                        // middle wall bottom left corner
                        (randomX > 170 && randomX < 376) && (randomY > 540 && randomY < 610) ||
                        (randomX > 170 && randomX < 240) && (randomY > 410 && randomY < 560) ||
                        // middle wall bottom right corner
                        (randomX > 402 && randomX < 610) && (randomY > 540 && randomY < 610) ||
                        (randomX > 540 && randomX < 610) && (randomY > 410 && randomY < 560) ||
                        // middle left pillar
                        (randomX > 288 && randomX < 357) && (randomY > 280 && randomY < 495) ||
                        // middle right pillar
                        (randomX > 421 && randomX < 491) && (randomY > 280 && randomY < 495)) {
                } else {
                    smallFood.setBounds(randomX, randomY, smallFood.getPreferredSize().width, smallFood.getPreferredSize().height);
                    break;
                }
            }
        }
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
