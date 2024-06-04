package com.components.menu;

import com.constants.ComponentConst;
import com.constants.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuBackgroundComponent extends JComponent {

    private Image image;

    public MenuBackgroundComponent() {
        setPreferredSize(new Dimension(ComponentConst.FRAME_WIDTH, ComponentConst.FRAME_HEIGHT));
        try {
            this.image = ImageIO.read(new File(Resources.START_SCREEN_BACKGROUND));
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
