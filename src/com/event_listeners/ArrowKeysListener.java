package com.event_listeners;

import com.GameManager;
import com.SoundManager;
import com.game_objects.Snake;
import com.constants.Direction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Updates the direction data of the current snake instance in the GameManager and plays sounds for each direction
public class ArrowKeysListener extends KeyAdapter {

    // HeadDirectionChangeListener
    public ArrowKeysListener() {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (GameManager.getInstance().inGame()) {
            Snake snake1 = GameManager.getInstance().getSnake();
            // previousDir exists in tandem with the updatedDirection in the GameLoopThread -
            // since you can have two inputs within the time of two ticks/frames (if you press the arrow keys quickly),
            // your currentDir (which is just the last direction input) can change twice before the GameLoopThread checks
            // for updates so this still turns the snake from up to down directly - to prevent that previousDir is needed
            Direction previousDir = snake1.getPreviousDirectionInput();

            // the currentDir exists so you cant turn from UP to DOWN directly - not a valid input. This prevents you
            // from commanding direction of DOWN when the snake is going UP for example or RIGHT when the head dir is LEFT etc.
            // As explained above, this can still change within two 'ticks' of the GameLoopThread - so by itself it doesn't fully solve the issue
            Direction currentDir = snake1.getCurrentDirectionInput();

            if (e.getKeyCode() == KeyEvent.VK_LEFT && currentDir != Direction.RIGHT && previousDir != Direction.RIGHT && currentDir != Direction.LEFT) {
                snake1.setCurrentDirectionInput(Direction.LEFT);
                SoundManager.getInstance().playTurnLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentDir != Direction.LEFT && previousDir != Direction.LEFT && currentDir != Direction.RIGHT) {
                snake1.setCurrentDirectionInput(Direction.RIGHT);
                SoundManager.getInstance().playTurnRight();
            } else if (e.getKeyCode() == KeyEvent.VK_UP && currentDir != Direction.DOWN && previousDir != Direction.DOWN && currentDir != Direction.UP) {
                snake1.setCurrentDirectionInput(Direction.UP);
                SoundManager.getInstance().playTurnUp();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentDir != Direction.UP && previousDir != Direction.UP && currentDir != Direction.DOWN) {
                snake1.setCurrentDirectionInput(Direction.DOWN);
                SoundManager.getInstance().playTurnDown();
            }
        }
    }
}
