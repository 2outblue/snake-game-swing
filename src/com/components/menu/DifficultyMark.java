package com.components.menu;

import com.constants.Resources;
import com.game_utility.Difficulty;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

// Should it be DifficultyCheckMark ?
public class DifficultyMark extends JComponent {

    private Image image;

    public DifficultyMark() {
        setPreferredSize(new Dimension(55, 50));
        try {
            this.image = ImageIO.read(new File(Resources.DIFFICULTY_MARK));
//            System.out.println("loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0,this);
    }

    public void set(Difficulty dif) {
        switch (dif) {
            case EASY -> easy();
            case MEDIUM -> medium();
            case HARD -> hard();
        }
    }
    private void easy() {
        this.setBounds(201, 349, 55, 50);
    }

    private void medium() {
        this.setBounds(201, 464, 55, 50);
    }

    private void hard() {
        this.setBounds(201, 579, 55, 50);
    }
}
