package com.threads;

import com.GameManager;
import com.game_objects.Snake;

import java.awt.*;

public class SnakeMovementThread implements Runnable{

    private int movementSpeedInverse = 33;

    private Snake snake;

    //maybe should be isRunning ?
    private boolean isMoving;

    public SnakeMovementThread() {
        snake = Snake.getInstance();
        isMoving = true;
    }

    @Override
    public void run() {

        while (isMoving) {
            Rectangle headBounds = snake.getHead().getBounds();
//            Direction headDirection = snake.getHead().getDirection();
            int step = 5;
            if (!snake.isPaused()) {
                switch (snake.getHead().getDirection()) {
                    case UP:
                        snake.getHead().setBounds(headBounds.x, headBounds.y - step, headBounds.width, headBounds.height);
                        break;
                    case DOWN:
                        snake.getHead().setBounds(headBounds.x, headBounds.y + step, headBounds.width, headBounds.height);
                        break;
                    case LEFT:
                        snake.getHead().setBounds(headBounds.x - step, headBounds.y, headBounds.width, headBounds.height);
                        break;
                    case RIGHT:
                        snake.getHead().setBounds(headBounds.x + step, headBounds.y, headBounds.width, headBounds.height);
                }
                if (borderCollision()) {
                    GameManager.getInstance().endGame();
                }
                snake.paintBody();

            }


            try {
                Thread.sleep(movementSpeedInverse);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public synchronized void moving(boolean moving) {
        isMoving = moving;
    }

    private boolean borderCollision() {
        int snakeX = snake.getHead().getBounds().x;
        int snakeY = snake.getHead().getBounds().y;
        // collision if x < 100 or x > 690 // if y > 670 or y < 80
        return snakeX < 100 || snakeX > 690 || snakeY < 80 || snakeY > 670;
    }

    private boolean selfCollision() {
        return false;
    }

}
