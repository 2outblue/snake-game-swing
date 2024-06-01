package com.components.food;

import com.components.constants.ComponentConst;
import com.components.constants.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SmallFood extends JComponent {

    private int size;

    private Image image;
    public SmallFood(int size, String path) {
//        setPreferredSize(new Dimension(ComponentConst.FOOD_SIZE, ComponentConst.FOOD_SIZE));
        setPreferredSize(new Dimension(size, size));
        this.size = size;
        try {
            this.image = ImageIO.read(new File(path));
//            System.out.println("loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // TODO: add check in case the image is null

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(image, 0, 0,this);
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setColor(Color.RED);
//
//        int diameter = Math.min(getWidth(), getHeight());
//        g2d.fillOval(0, 0, diameter, diameter);
    }
}
