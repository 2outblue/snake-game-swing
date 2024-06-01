package com.components.snake;

import com.components.constants.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

public class SnakeHead extends JComponent{

    private int size;

    private Image image;

    private double angle = 3.141592653;
    private Direction direction;

    public SnakeHead(int size) {
        setPreferredSize(new Dimension(size, size));
        this.size = size;
        this.direction = Direction.UP;
        try {
            this.image = ImageIO.read(new File("src/resources/snake-head-20.png"));
//            System.out.println("loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
//        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, this.size, this.size);
//        g.drawRect(270, 370, 10,10);

        if (image != null) {
            int x = (getWidth() - image.getWidth(this)) / 2;
            int y = (getHeight() - image.getHeight(this)) / 2;
//            g.drawImage(image, x, y, this);
//            System.out.println(getBounds().x + "<---X----Y--->" + getBounds().y);
                AffineTransform transform = new AffineTransform();
                // Translate to the center of the component
                transform.translate(getWidth() / 2, getHeight() / 2);
                // Rotate around the center
                transform.rotate(angle);
                // Translate back to the top-left corner
                transform.translate(-image.getWidth(this) / 2, -image.getHeight(this) / 2);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawImage(image, transform, this);
        }
        g2d.dispose();
    }

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

    public synchronized void setAngle(double angle) {
        this.angle = angle;
    }
}
