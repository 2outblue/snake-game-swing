package com.threads;

import com.GameManager;
import com.components.ComponentFactory;
import com.components.constants.ComponentConst;
import com.components.food.SmallFood;
import com.game_objects.Snake;

import java.awt.*;
import java.util.Random;

public class SnakeMovementThread implements Runnable{

    private int movementSpeedInverse = 33;

    private Snake snake;

    private SmallFood smallFood;

    //maybe should be isRunning ?
    private boolean isMoving;

    private Rectangle headBounds;

    public SnakeMovementThread() {
        snake = Snake.getInstance();
        isMoving = true;
        headBounds = snake.getHead().getBounds();
    }

    @Override
    public void run() {
        spawnFood();
        while (isMoving) {
            headBounds = snake.getHead().getBounds();
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
                if (borderCollision() || selfCollision()) {
                    GameManager.getInstance().endGame();
                }
                // take that out to the food thread along with the relevant methods
                if (foodCollision()) {
                    GameManager.getInstance().removeFood(smallFood);
                    GameManager.getInstance().growSnake();

                    spawnFood();
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

    private boolean borderCollision() {
        int snakeX = snake.getHead().getBounds().x;
        int snakeY = snake.getHead().getBounds().y;
        // collision if x < 100 or x > 690 // if y > 670 or y < 80
        return snakeX < 100 || snakeX > 690 || snakeY < 80 || snakeY > 670;
    }

    private boolean selfCollision() {
        int snakeX = snake.getHead().getBounds().x;
        int snakeY = snake.getHead().getBounds().y;
        //doesn't check for the first two blocks (the first one always collides since there is a 5px overlap
        // with the snakeHead, doesn't check for the second and third for safety)
        for (int i = 3; i < snake.getBody().size(); i++) {
            Rectangle compBounds = snake.getBody().get(i).getBounds();
            int compX = compBounds.x;
            int compY = compBounds.y;
//            System.out.println(String.format("Component %d - coords x:%d--y:%d",i, compX, compY));
            if ((snakeX > compX && snakeX < compX + 10) && (snakeY > compY && snakeY < compY + 10)) {
                return true;
            }
        }
//        System.out.println(snakeX + "<-X snake Y->" + snakeY);
        return false;
    }

    private void spawnFood() {
        Random random = new Random();
        smallFood = ComponentFactory.getInstance().createSmallFood();
        int randomX = random.nextInt(100, 690);
        int randomY = random.nextInt(80, 670);

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
        return (snakeX + ComponentConst.SNAKE_COMPONENT_SIZE >= foodX && snakeX <= foodX + ComponentConst.FOOD_SIZE) &&
                (snakeY + ComponentConst.SNAKE_COMPONENT_SIZE >= foodY && snakeY <= foodY + ComponentConst.FOOD_SIZE);
    }

}
