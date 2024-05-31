package com.components.snake.tail;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TailPart extends TailComponent {

    public TailPart(String path) {
        try {
            this.image = ImageIO.read(new File(path));
//            System.out.println("loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
