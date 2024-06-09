package com.threads;

import com.GameManager;
import com.SoundManager;
import com.components.ComponentFactory;
import com.ScoreManager;
import com.constants.ComponentBounds;
import com.constants.Direction;
import com.game_objects.SmallFood;
import com.components.snake.SnakeHead;
import com.game_objects.Snake;
import com.game_utility.MapCollisionData;
import com.game_utility.Difficulty;

import java.awt.*;
import java.util.Random;


// Rename to InGameThread, or GameTickThread ?
// Started only when startGame() is called in the GameManager, takes care of movement, the food and collision checking
public class GameLoopThread extends Thread {

    private final int TICK_RATE = 33;

    private final Snake snake;

    private SmallFood smallFood;

    //maybe should be isRunning ?
    private boolean isRunning;

    private Rectangle headBounds;

    private final Difficulty dif;

    private boolean skip = false;

    public GameLoopThread() {
        GameManager gm = GameManager.getInstance();
        snake = gm.getSnake();
        isRunning = true;
        headBounds = snake.getHead().getBounds();
        dif = gm.getDifficulty();
    }

    @Override
    public void run() {
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
                    ScoreManager.getInstance().saveScore();
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    GameManager.getInstance().endGame();
                }

                if (foodCollision()) {
                    SoundManager.getInstance().playFoodCollision();
                    GameManager.getInstance().removeFood(smallFood);
                    GameManager.getInstance().growSnake();
                    ScoreManager.getInstance().increaseScore();

                    spawnFood();
                }
                snake.paintBody(headBounds);
            }
            try {
                Thread.sleep(TICK_RATE);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean borderCollision() {
        int snakeX = snake.getHead().getBounds().x;
        int snakeY = snake.getHead().getBounds().y;
        if (dif == Difficulty.EASY || dif == Difficulty.MEDIUM) {
            return snakeX < MapCollisionData.borderMinX || snakeX > MapCollisionData.borderMaxX ||
                    snakeY < MapCollisionData.borderMinY || snakeY > MapCollisionData.borderMaxY;
        } else if (dif == Difficulty.HARD) {
            return (snakeX < MapCollisionData.borderMinX || snakeX > MapCollisionData.borderMaxX) ||
                    (snakeY < MapCollisionData.borderMinY || snakeY > MapCollisionData.borderMaxY) ||
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
                    (snakeX > 421 && snakeX < 491) && (snakeY > 280 && snakeY < 495);
        }
        return false;
    }
    private boolean selfCollision() {
        int headX = headBounds.x;
        int headY = headBounds.y;
        //doesn't check for the first two blocks (the first one always collides since there is a 5px overlap
        // with the snakeHead, doesn't check for the third and fourth for safety)
        for (int i = 3; i < snake.getBody().size(); i++) {
            Rectangle compBounds = snake.getBody().get(i).getBounds();
            int compX = compBounds.x;
            int compY = compBounds.y;
//            System.out.println(String.format("Component %d - coords x:%d--y:%d",i, compX, compY));
            if ((headX + 12 > compX && headX < compX + ComponentBounds.SNAKE_COMPONENT_SIZE) &&
                    (headY + 12> compY && headY < compY + ComponentBounds.SNAKE_COMPONENT_SIZE)) {
                return true;
            }
        }
        // checks for collision with the tail - doesn't check the last tail part because it's kinda too small to see (and avoid)
        for (int i = 1; i < snake.getTail().size() - 2; i++) {
            Rectangle compBounds = snake.getTail().get(i).getBounds();
            Dimension compSize = snake.getTail().get(i).getPreferredSize();

            int compX = compBounds.x;
            int compY = compBounds.y;
            if ((headX + 12 > compX && headX < compX + compSize.width) &&
                    (headY + 12> compY && headY < compY + compSize.height)) {
                return true;
            }
        }
        return false;
    }
    private boolean foodCollision() {
        //can headBounds.x/y cause issues? (not .get, so its not going through a synch method?)
        int snakeX = headBounds.x;
        int snakeY = headBounds.y;
        int foodX = smallFood.getBounds().x;
        int foodY = smallFood.getBounds().y;
        //            System.out.println("Food collision");
        return (snakeX + ComponentBounds.SNAKE_HEAD_20 - 10 >= foodX && snakeX <= foodX + ComponentBounds.FOOD_SIZE) &&
                (snakeY + ComponentBounds.SNAKE_HEAD_20  - 10>= foodY && snakeY <= foodY + ComponentBounds.FOOD_SIZE);
    }

    private void spawnFood() {
        Random random = new Random();
        smallFood = ComponentFactory.getInstance().createSmallFood();
        if (dif == Difficulty.EASY || dif == Difficulty.MEDIUM) {
            int randomX = random.nextInt(MapCollisionData.foodMinX, MapCollisionData.foodMaxX);
            int randomY = random.nextInt(MapCollisionData.foodMinY, MapCollisionData.foodMaxY);
            smallFood.setBounds(randomX, randomY, smallFood.getPreferredSize().width, smallFood.getPreferredSize().height);
        } else if (dif == Difficulty.HARD) {

            // generates values for the food until the values are not within the
            // inner walls of map_3 (hard difficulty) - could be done smarter but it works for now
            while (true) {
                int randomX = random.nextInt(MapCollisionData.foodMinX, MapCollisionData.foodMaxX);
                int randomY = random.nextInt(MapCollisionData.foodMinY, MapCollisionData.foodMaxY);

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
                        // center left pillar
                        (randomX > 288 && randomX < 357) && (randomY > 280 && randomY < 495) ||
                        // center right pillar
                        (randomX > 421 && randomX < 491) && (randomY > 280 && randomY < 495)) {
                    continue;
                }
                smallFood.setBounds(randomX, randomY, smallFood.getPreferredSize().width, smallFood.getPreferredSize().height);
                break;
            }
        }
        GameManager.getInstance().renderFood(smallFood);
    }

    public void stopRunning() {
//        this.interrupt();
        isRunning = false;
    }


}
