package com.components.snake;

import com.components.constants.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SnakeBody extends JComponent{

    private int size;
    private Direction direction;

    Image image;

    private float transparency = 1.0f;
    public SnakeBody(int size) {
        setPreferredSize(new Dimension(size, size));
        this.size = size;
        this.direction = Direction.DOWN;
        try {
            this.image = ImageIO.read(new File("src/resources/body13.png"));
//            System.out.println("loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        }

//        g2d.setColor(Color.BLACK);
//        g2d.fillRect(0, 0, this.size, this.size);
    }

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

    public synchronized void setSquareTransparency(float transparency) {
        this.transparency = Math.max(0, Math.min(1, transparency)); // Clamp between 0 and 1
        repaint();
    }
}
