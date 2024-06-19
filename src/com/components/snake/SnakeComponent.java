package com.components.snake;

import com.constants.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// TODO: Direction for the SnakeBody is useless i think.
// Direction is used for the SnakeHead class and for the tail - Maybe make a SnakeTail class which
// extends SnakeBody and keep the direction there and in the SnakeHead, not in this class. The actual body (not tail)
// of the snake doesn't need direction as it only updates bounds for every individual components.
public abstract class SnakeComponent extends JComponent {

    protected Direction direction;

    protected Image image;

    public SnakeComponent(int size, String path) {
        setPreferredSize(new Dimension(size, size));
        this.direction = Direction.UP;
        try {
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Direction getDirection() {
        return this.direction;
    }

    public synchronized void setDirection(Direction direction) {
        this.direction = direction;
    }
}
