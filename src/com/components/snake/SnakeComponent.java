package com.components.snake;

import com.constants.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
