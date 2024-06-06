package com.components.snake;

import com.constants.Direction;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class SnakeHead extends SnakeComponent{
    private double angle = 3.141592653;

    public SnakeHead(int size, String path) {
        super(size, path);
        // Head points always in the direction that it is moving (unlike the body parts - they point in the opposite direction of movement)
        this.direction = Direction.UP;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (image != null) {
//            System.out.println(getBounds().x + "<---X----Y--->" + getBounds().y);
                AffineTransform transform = new AffineTransform();
                // Translate to the center of the component
                transform.translate(getWidth() / 2.0, getHeight() / 2.0);
                // Rotate around the center
                transform.rotate(angle);
                // Translate back to the top-left corner
                transform.translate(-image.getWidth(this) / 2.0, -image.getHeight(this) / 2.0);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawImage(image, transform, this);
        }
        g2d.dispose();
    }

    // Used for the first body part when painting(rendering) the body, since all body parts point in the opposite
    // direction of the head
    public Direction getReverseDirection() {
        switch (this.direction) {
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

    public synchronized void setAngle(double angle) {
        this.angle = angle;
    }
}
