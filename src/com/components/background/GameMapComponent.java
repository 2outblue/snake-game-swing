package com.components.background;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameMapComponent extends JComponent {

    private Image image;

    public GameMapComponent(String path) {
        try {
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // what to do if image is null ?
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }
}
