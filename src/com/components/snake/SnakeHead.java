package com.components.snake;

import com.components.constants.Direction;

import javax.swing.*;
import java.awt.*;

public class SnakeHead extends JComponent implements SnakeComponent{

    private int size;
    private Direction direction;

    private Rectangle previousBounds;
    private boolean directionChange;
    public SnakeHead(int size) {
        setPreferredSize(new Dimension(size, size));
        this.size = size;
        this.direction = Direction.UP;
        this.directionChange = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.size, this.size);
//        g.drawRect(270, 370, 10,10);
    }

    @Override
    public synchronized Direction getDirection() {
        return this.direction;
    }

    public synchronized Direction getReverseDirection() {
        switch (this.direction) {
            case UP -> {
                return Direction.DOWN;
            }
            case DOWN -> {
                return Direction.UP;
            }
            case LEFT -> {
                return Direction.RIGHT;
            }
            case RIGHT -> {
                return Direction.LEFT;
            }
            default -> {
                return Direction.DOWN;
            }
        }
    }

    @Override
    public synchronized void setBounds(Rectangle r) {
        super.setBounds(r);
    }

    @Override
    public synchronized Rectangle getBounds(Rectangle rv) {
        return super.getBounds(rv);
    }

    public synchronized void setDirection(Direction direction) {
        this.direction = direction;
    }

    public synchronized Rectangle getPreviousBounds() {
        return previousBounds;
    }

    public synchronized void setPreviousBounds(Rectangle previousBounds) {
        this.previousBounds = previousBounds;
    }

    public synchronized void display(Rectangle bounds){
        this.setBounds(bounds);
    }

    public synchronized boolean isDirectionChange() {
        return directionChange;
    }

    public synchronized void setDirectionChange(boolean directionChange) {
        this.directionChange = directionChange;
    }
}
