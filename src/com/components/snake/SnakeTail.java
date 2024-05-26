package com.components.snake;

import com.components.constants.Direction;

import javax.swing.*;
import java.awt.*;

public class SnakeTail extends JComponent implements SnakeComponent {

    private Direction direction;
    private int size;

    public SnakeTail(int size) {
        setPreferredSize(new Dimension(size, size));
        this.size = size;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.size, this.size);
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public synchronized void display(Rectangle bounds){
        this.setBounds(bounds);
    }
}
