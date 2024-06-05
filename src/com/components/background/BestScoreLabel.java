package com.components.background;

import com.constants.ComponentBounds;
import com.constants.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BestScoreLabel extends JComponent {

    private Image image;

    // this is the BEST + the apple component - not the actual dynamic best score value
    public BestScoreLabel() {
        setPreferredSize(new Dimension(ComponentBounds.BEST_SCORE_WIDTH, ComponentBounds.BEST_SCORE_HEIGHT));
        try {
            this.image = ImageIO.read(new File(Resources.BEST_SCORE));
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
