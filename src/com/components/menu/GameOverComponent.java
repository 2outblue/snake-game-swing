package com.components.menu;

import com.constants.ComponentBounds;
import com.constants.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameOverComponent extends JComponent {
    private Image image;

    private final int width = ComponentBounds.GAME_OVER_WIDTH;
    private final int height = ComponentBounds.GAME_OVER_HEIGHT;


    public GameOverComponent() {
        setPreferredSize(new Dimension(width, height));
        try {
            this.image = ImageIO.read(new File(Resources.GAME_OVER_1));
//            System.out.println("loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        // TODO: add check in case the image is null
        g2d.drawImage(image, 0, 0,this);
    }
}
