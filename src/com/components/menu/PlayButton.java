package com.components.menu;

import com.components.constants.ComponentConst;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlayButton extends JButton {

    private Image image;

    public PlayButton() {
        try {
            this.image = ImageIO.read(new File(ComponentConst.BUTTON_1_PLAY));
//            System.out.println("loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        this.setHorizontalTextPosition(JButton.CENTER);
//        this.setVerticalTextPosition(JButton.CENTER);
//        this.setContentAreaFilled(false);
//        this.setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }
}
