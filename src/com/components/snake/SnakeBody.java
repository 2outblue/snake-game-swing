package com.components.snake;

import com.components.constants.Direction;

import javax.swing.*;
import java.awt.*;

public class SnakeBody extends JComponent implements SnakeComponent {

    private int size;
    private Direction direction;
    public SnakeBody(int size) {
        setPreferredSize(new Dimension(size, size));
        this.size = size;
        this.direction = Direction.DOWN;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.size, this.size);
    }

    @Override
    public synchronized Direction getDirection() {
        return this.direction;
    }

    public synchronized void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public synchronized void setBounds(Rectangle r) {
        super.setBounds(r);
    }

    @Override
    public synchronized Rectangle getBounds(Rectangle rv) {
        return super.getBounds(rv);
    }

    public synchronized void display(Rectangle bounds){
        this.setBounds(bounds);
    }

}
