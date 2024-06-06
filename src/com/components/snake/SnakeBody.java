package com.components.snake;

import com.constants.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;


public class SnakeBody extends SnakeComponent{
    private float transparency = 1.0f;
    public SnakeBody(int size, String path) {
        super(size, path);
        // All body parts point in the opposite direction of movement (unlike the head - the head points in the direction that it is moving)
        this.direction = Direction.DOWN;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));

        if (image != null) {
            int x = (getWidth() - image.getWidth(this)) / 2;
            int y = (getHeight() - image.getHeight(this)) / 2;
            g2d.drawImage(image, x, y, this);
        } else {
            Image image2 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            int x = (getWidth() - image2.getWidth(this)) / 2;
            int y = (getHeight() - image2.getHeight(this)) / 2;
            g2d.drawImage(image2, x, y, this);
        }
    }

    public synchronized void setSquareTransparency(float transparency) {
        this.transparency = Math.max(0, Math.min(1, transparency)); // Clamp between 0 and 1
        repaint();
    }
}
