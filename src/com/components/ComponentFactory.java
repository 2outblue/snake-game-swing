package com.components;

import com.components.food.SmallFood;
import com.components.snake.SnakeBody;
import com.components.snake.SnakeHead;
import com.components.snake.SnakeTail;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ComponentFactory {

    public static final int COMPONENT_SIZE = 10;
    private static ComponentFactory instance;
    private JFrame frame;
    private SnakeHead snakeHead;
    private SnakeTail snakeTail;

    private ComponentFactory() {
    }

    public SnakeTail createTail() {
        if (this.snakeHead == null) {
            return new SnakeTail(COMPONENT_SIZE);
        }
        return this.snakeTail;
    }

    public SnakeBody createBody() {
        return new SnakeBody(COMPONENT_SIZE);
    }

    public SnakeHead createHead() {
        if (this.snakeHead == null) {
            return new SnakeHead(COMPONENT_SIZE);
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
