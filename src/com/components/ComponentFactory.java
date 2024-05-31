package com.components;

import com.components.constants.ComponentConst;
import com.components.food.SmallFood;
import com.components.snake.SnakeBody;
import com.components.snake.SnakeHead;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ComponentFactory {

    private static ComponentFactory instance;
    private JFrame frame;
    private String[] pattern;
    private int repeatCounter;
    private final int patternRepeater;
    private int patternPointer;

//    private TailComponent snakeTail;

    private ComponentFactory() {
        repeatCounter = 1;
        patternPointer = 0;
        patternRepeater = 2;
        makePattern();
    }

    public SnakeBody createTailPart(int size, String path) {
        return new SnakeBody(size, path);

    }

    public SnakeBody createBody() {
        repeatCounter++;

        if (repeatCounter > patternRepeater) {
            repeatCounter = 1;

            patternPointer++;
            if (patternPointer > 4) {patternPointer = 0;}
        }
        return new SnakeBody(ComponentConst.SNAKE_COMPONENT_SIZE, pattern[patternPointer]);
    }

    public SnakeHead createHead() {

        return new SnakeHead(ComponentConst.SNAKE_HEAD_20);

    }

    public JFrame createFrame() {
        if (this.frame == null) {
            JFrame frame = new JFrame("Snake");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(null);

            frame.pack();
            frame.setVisible(true);
            frame.setSize(800, 800);
            frame.setResizable(false);
            //sets the window location in the center of the screen
            frame.setLocationRelativeTo(null);
            this.frame = frame;
        }
        return this.frame;
    }

    public JLabel createPauseLabel() {
        JLabel label = new JLabel("GAME PAUSED", SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setBackground(Color.GREEN);
        label.setOpaque(true);
        label.setBorder(new LineBorder(Color.GREEN, 2));

        return label;
    }

    public JLabel createGameOverLabel() {
        JLabel label = new JLabel("GAME OVER", SwingConstants.CENTER);
        label.setForeground(Color.BLACK);
        label.setBackground(Color.RED);
        label.setOpaque(true);
        label.setBorder(new LineBorder(Color.GREEN, 2));

        return label;
    }

    public SmallFood createSmallFood() {
        return new SmallFood();
    }

    public static ComponentFactory getInstance() {
            if (instance == null) {
                instance = new ComponentFactory();
            }
            return instance;
    }

    private void makePattern() {
        pattern = new String[5];

        pattern[0] = ComponentConst.BODY_13_GREEN;
        pattern[1] = ComponentConst.BODY_13_RED;
        pattern[2] = ComponentConst.BODY_13_PURPLE;
        pattern[3] = ComponentConst.BODY_13_YELLOW;
        pattern[4] = ComponentConst.BODY_13_BLUE;
    }
}
