package com.components.background;

import com.components.constants.ComponentConst;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BackgroundComponent extends JComponent {

    Image image;

    public BackgroundComponent() {
        try {
            this.image = ImageIO.read(new File(ComponentConst.BACKGROUND_1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }
}
