package com.components.background;

import com.constants.ComponentConst;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BestComponent extends JComponent {

    private Image image;

    public BestComponent() {
        setPreferredSize(new Dimension(121, 30));
        try {
            this.image = ImageIO.read(new File(ComponentConst.BEST_SCORE));
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
