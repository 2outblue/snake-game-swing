package com.components.border;

import javax.swing.*;
import java.awt.*;

public abstract class BorderComponent extends JComponent {

    int length;

    int width;

    public int y;

    public int x;

    public BorderComponent(int width, int length) {
        this.width = width;
        this.length = length;
        setPreferredSize(new Dimension(width, length));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, length);
    }

}
