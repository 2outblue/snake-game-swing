package com.game_objects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SmallFood extends JComponent {

    private Image image;
    public SmallFood(int size, String path) {
        setPreferredSize(new Dimension(size, size));
        try {
            this.image = ImageIO.read(new File(path));
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
    }
}
