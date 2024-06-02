package com.event_listeners;

import com.GameManager;
import com.SoundManager;
import com.game_objects.Snake;
import com.components.constants.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HeadDirectionChangeListener extends KeyAdapter {

    private Snake snake;

    public HeadDirectionChangeListener() {
        snake = GameManager.getInstance().getSnake();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // the currentDir exists so you cant turn from UP to DOWN directly - not a valid input
        Direction currentDir = snake.getCurrentDirectionInput();

        // last dir exists in tandem with the updatedDirection in the SnakeMovementThread - prevents
        // the snake from turning around quicker than 12 px - because you can have two inputs within the
        // time it takes to 'refresh' two times, your currentDir can change twice before the snake head updates
        // so this still turns the snake from up to down directly - to prevent that lastDir is needed
        Direction lastDir = snake.getPreviousDirectionInput();

        if (e.getKeyCode() == KeyEvent.VK_LEFT && currentDir != Direction.RIGHT && lastDir != Direction.RIGHT && currentDir != Direction.LEFT) {
            snake.setCurrentDirectionInput(Direction.LEFT);
            SoundManager.getInstance().playTurnLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentDir != Direction.LEFT && lastDir != Direction.LEFT && currentDir != Direction.RIGHT) {
            snake.setCurrentDirectionInput(Direction.RIGHT);
            SoundManager.getInstance().playTurnRight();
        } else if (e.getKeyCode() == KeyEvent.VK_UP && currentDir != Direction.DOWN && lastDir != Direction.DOWN && currentDir != Direction.UP) {
            snake.setCurrentDirectionInput(Direction.UP);
            SoundManager.getInstance().playTurnUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentDir != Direction.UP && lastDir != Direction.UP && currentDir != Direction.DOWN) {
            snake.setCurrentDirectionInput(Direction.DOWN);
            SoundManager.getInstance().playTurnDown();
        }
    }
}
