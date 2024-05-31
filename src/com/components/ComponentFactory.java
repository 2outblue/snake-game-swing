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
    private SnakeHead snakeHead;
//    private TailComponent snakeTail;

    private ComponentFactory() {
    }

    public SnakeBody createTailPart(int size, String path) {
        return new SnakeBody(size, path);

    }

    public SnakeBody createBody() {
        return new SnakeBody(ComponentConst.SNAKE_COMPONENT_SIZE, ComponentConst.BODY_13);
    }

    public SnakeHead createHead() {
        if (this.snakeHead == null) {
            return new SnakeHead(ComponentConst.SNAKE_HEAD_20);
        }
        return this.snakeHead;
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
}
