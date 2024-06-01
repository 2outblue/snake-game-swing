package com.components.menu;

import com.components.constants.ComponentConst;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuComponent extends JComponent {

    private Image image;

    private final int size = ComponentConst.FRAME_WIDTH;

    public MenuComponent() {
        setPreferredSize(new Dimension(size, size));
        try {
            this.image = ImageIO.read(new File(ComponentConst.START_SCREEN_BACKGROUND));
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
