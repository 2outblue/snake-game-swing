package com.components.snake.tail;

import javax.swing.*;
import java.awt.*;

public abstract class TailComponent extends JComponent {

    Image image;

    public TailComponent() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (image != null) {
            int x = (getWidth() - image.getWidth(this)) / 2;
            int y = (getHeight() - image.getHeight(this)) / 2;
            g2d.drawImage(image, x, y, this);
        }
    }
}
