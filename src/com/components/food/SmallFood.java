package com.components.food;

import com.components.constants.ComponentConst;

import javax.swing.*;
import java.awt.*;

public class SmallFood extends JComponent {

    public SmallFood() {
        setPreferredSize(new Dimension(ComponentConst.FOOD_SIZE, ComponentConst.FOOD_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.RED);

        int diameter = Math.min(getWidth(), getHeight());
        g2d.fillOval(0, 0, diameter, diameter);
    }
}
