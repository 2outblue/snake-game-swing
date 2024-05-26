package com.event_listeners;

import com.game_objects.Snake;
import com.components.constants.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HeadDirectionChangeListener extends KeyAdapter {

    private Snake snake;

    public HeadDirectionChangeListener() {
        snake = Snake.getInstance();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction currentDir = snake.getCurrentInput();
        if (e.getKeyCode() == KeyEvent.VK_LEFT && currentDir != Direction.RIGHT) {
            snake.setCurrentInput(Direction.LEFT);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentDir != Direction.LEFT) {
            snake.setCurrentInput(Direction.RIGHT);
        } else if (e.getKeyCode() == KeyEvent.VK_UP && currentDir != Direction.DOWN) {
            snake.setCurrentInput(Direction.UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentDir != Direction.UP) {
            snake.setCurrentInput(Direction.DOWN);
        }
    }
}
